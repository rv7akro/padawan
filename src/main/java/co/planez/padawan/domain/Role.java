package co.planez.padawan.domain;

public enum Role { 
	USER(Names.USER), 
	ADMIN(Names.ADMIN),
	INSTRUCTOR(Names.INSTRUCTOR);

	public class Names {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String INSTRUCTOR = "INSTRUCTOR";
	}

    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
