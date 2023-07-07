package co.planez.padawan.domain.dao;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.User;
import co.planez.padawan.domain.persistence.Persistence;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.*;

public class UserDAO extends SuperDAO {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
	
	public UserDAO() {
		super(User.class);
	}

	public User getByUsername(String name) {
		Datastore ds = Persistence.instance().datastore();
		Query<User> query = ds.find(User.class);
		List<User> users = query.filter(Filters.eq("name", name)).iterator().toList();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
}
