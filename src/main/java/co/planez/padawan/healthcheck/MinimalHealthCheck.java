package co.planez.padawan.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class MinimalHealthCheck extends HealthCheck {

    public MinimalHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
