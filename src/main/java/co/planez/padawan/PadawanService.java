package co.planez.padawan;

import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.TimeZone;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.auth.ApplicationAuthenticator;
import co.planez.padawan.auth.ApplicationAuthorizer;
import co.planez.padawan.common.RuntimeExceptionMapper;
import co.planez.padawan.domain.User;
import co.planez.padawan.domain.persistence.Persistence;
import co.planez.padawan.healthcheck.MinimalHealthCheck;
import co.planez.padawan.resources.PadawanResource;
import co.planez.padawan.resources.UserResource;
//import co.planez.padawan.resources.SlackClient;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.forms.MultiPartBundle;

public class PadawanService extends Application<PadawanConfiguration> {
	private static final Logger LOG = LoggerFactory.getLogger(PadawanService.class);

	public static void main(String[] args) throws Exception {
		LOG.info("Starting : Planez Padawan Service");
        if (args.length < 2) {
            new PadawanService().run(new String[]{"server", "configuration.ftl"});
        } else {
            new PadawanService().run(args);
        }
	}

	@Override
	public void initialize(Bootstrap<PadawanConfiguration> bootstrap) {
		// bootstrap.addBundle(new AssetsBundle("/doc", "/doc", "index.html","html"));
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), 
				new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
		bootstrap.addBundle(new MultiPartBundle());
	}

	@Override
	public String getName() {
		return "padawan";
	}

	@Override
	public void run(PadawanConfiguration config, Environment env) throws Exception {
		env.jersey().setUrlPattern("/api/*");
		
        env.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(new ApplicationAuthenticator())
                    .setAuthorizer(new ApplicationAuthorizer())
                    .setRealm("Planez Padawan")
                    .setPrefix("Bearer")
                    .buildAuthFilter()));
        
		// Get the startup date/time in GMT
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Set up Slack communications
		// SlackClient slack = new SlackClient(config);
		// slack.sendMessage("Planez Padawan Prep System restarted at " + dateFormatGmt.format(new Date()));
		// slack.sendMessage("Planez Padawan Prep System running in '" + config.getMode() + "' mode.");

		// Configure to allow CORS
		configureCors(env);
		
		// Set up the Persistence singleton
		new Persistence().initialize(config.getMongodb());

		// Set exception mappers
		if (config.getMode().contentEquals("PROD")) {
			env.jersey().register(new RuntimeExceptionMapper());
		}
		
		// Now set up the API
		env.jersey().register(new PadawanResource(config));
		env.jersey().register(new UserResource(config));
		env.healthChecks().register("check", new MinimalHealthCheck());
	}
	
	private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, "true");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "/");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}
