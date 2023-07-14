package co.planez.padawan.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.planez.padawan.domain.persistence.Persistence;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import co.planez.padawan.domain.dao.SyllabusDAO;

@Entity("Syllabus")
public class Syllabus {
	private static SyllabusDAO dao = new SyllabusDAO();
	
	@Id
	@JsonIgnore
	private ObjectId id;

	private long syllabusId;

	private String name;
	private List<String> elements;
	private List<String> goals;
	
	public static String ID_KEY = "syllabus";

	public Syllabus() {}
	
	public Syllabus(String name) {
		this.name = name;
		this.syllabusId = Persistence.instance().getID(ID_KEY, 1000);
	}
	
	public long getId() {
		return syllabusId;
	}
	
	@JsonView(SummaryView.Normal.class)
	public String getName() {
		return name;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	public List<String> getGoals() {
		return goals;
	}

	public void setGoals(List<String> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}

	/*
	 * Database Management Functionality
	 */
	public static long count() {
		return dao.count();
	}
	
	public static void drop() {
		dao.drop();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Syllabus> getAll() {
		return (List<Syllabus>) dao.getAll();
	}

	public static Syllabus getByID(long id) {
		return (Syllabus) dao.getByID(id);
	}
	
	public void save() {
		dao.save(this);
	}
	
	public void delete() {
		dao.delete(this);
	}
}
