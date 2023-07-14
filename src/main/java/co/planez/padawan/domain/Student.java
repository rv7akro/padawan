package co.planez.padawan.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.planez.padawan.domain.persistence.Persistence;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import co.planez.padawan.domain.dao.StudentDAO;

@Entity("Users")
public class Student {
	private static StudentDAO dao = new StudentDAO();
	
	@Id
	@JsonIgnore
	private ObjectId id;

	private long studentId;

	private User user;
	private User instructor;
	private Syllabus syllabus;
	
	public static String ID_KEY = "students";

	public Student() {}
	
	public Student(User user) {
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
	public static List<Student> getAll() {
		return (List<Student>) dao.getAll();
	}

	public static Student getByID(long id) {
		return (Student) dao.getByID(id);
	}
	
	public void save() {
		dao.save(this);
	}
	
	public void delete() {
		dao.delete(this);
	}
}
