package co.planez.padawan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.Syllabus;

public class SyllabusDAO extends SuperDAO {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(SyllabusDAO.class);
	
	public SyllabusDAO() {
		super(Syllabus.class);
	}
}
