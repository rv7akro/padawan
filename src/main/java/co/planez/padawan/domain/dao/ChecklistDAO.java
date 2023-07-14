package co.planez.padawan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.Checklist;

public class ChecklistDAO extends SuperDAO {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
	
	public ChecklistDAO() {
		super(Checklist.class);
	}
}
