package co.planez.padawan.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.planez.padawan.domain.persistence.Persistence;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import co.planez.padawan.domain.dao.ChecklistDAO;

@Entity("Users")
public class Checklist {
	private static ChecklistDAO dao = new ChecklistDAO();
	
	@Id
	@JsonIgnore
	private ObjectId id;

	private long studentId;

	private User user;
	private User instructor;
	private Syllabus syllabus;
	private List<String> elements;
	private List<String> goals;
	
	public static String ID_KEY = "students";

	public Checklist() {}
	
	public Checklist(User user) {
		this.user = user;
		this.studentId = Persistence.instance().getID(ID_KEY, 1000);
	}
	
	public long getId() {
		return studentId;
	}
	
	@JsonView(SummaryView.Normal.class)
	public String getName() {
		return user.getName();
	}
	
	public String getFullName() {
		return user.getFullName();
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
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
		return "User [name=" + user.getName() + ", fullName=" + user.getFullName() + "]";
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
	public static List<Checklist> getAll() {
		return (List<Checklist>) dao.getAll();
	}

	public static Checklist getByID(long id) {
		return (Checklist) dao.getByID(id);
	}
	
	public void save() {
		dao.save(this);
	}
	
	public void delete() {
		dao.delete(this);
	}
}
