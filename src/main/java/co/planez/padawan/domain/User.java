package co.planez.padawan.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.planez.padawan.domain.persistence.Persistence;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import io.dropwizard.auth.PrincipalImpl;
import co.planez.padawan.domain.dao.UserDAO;

@Entity("Users")
public class User extends PrincipalImpl {
	private static UserDAO dao = new UserDAO();
	
	@Id
	@JsonIgnore
	private ObjectId id;
	
	@JsonView(SummaryView.Login.class)
	private long userId;
	@JsonView(SummaryView.Normal.class)
	private String fullName;
	@JsonIgnore
	private String password;
	@Transient
	@JsonView(SummaryView.Login.class)
	private String token;
	@JsonView(SummaryView.Normal.class)
	private List<Role> roles;
	@JsonView(DetailView.Normal.class)
	private List<Checklist> students;
	
	public static String ID_KEY = "users";

	public User() {
		super("");
	}
	
	public User(String name, String fullName, List<Role> roles) {
		super(name);
		this.fullName = fullName;
		this.roles = roles;
		this.userId = Persistence.instance().getID(ID_KEY, 1000);
	}
	
	public long getId() {
		return userId;
	}
	
	@JsonView(SummaryView.Normal.class)
	public String getName() {
		return super.getName();
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isAdmin() {
		if (roles != null) {
			return roles.contains(Role.ADMIN);
		} else {
			return false;
		}
	}

	public boolean isInstructor() {
		if (roles != null) {
			return roles.contains(Role.INSTRUCTOR);
		} else {
			return false;
		}
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public List<Role> addRole(Role role) {
		roles.add(role);
		return roles;
	}

	@Override
	public String toString() {
		return "User [name=" + super.getName() + ", fullName=" + fullName + ", isAdmin=" + isAdmin() + "]";
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
	public static List<User> getAll() {
		return (List<User>) dao.getAll();
	}

	public static User getByID(long id) {
		return (User) dao.getByID(id);
	}
	
	public static User getByUsername(String username) {
		return dao.getByUsername(username);
	}
	
	public void save() {
		dao.save(this);
	}
	
	public void delete() {
		dao.delete(this);
	}

}
