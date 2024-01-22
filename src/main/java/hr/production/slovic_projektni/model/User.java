package hr.production.slovic_projektni.model;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

public class User extends NamedEntity {

    //private static final Logger logger = LoggerFactory.getLogger(User.class);

    private static final Pattern VALID_USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9\\-_ ]{4,32}$");

    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private UserRole userRole;

    public User(Long id, String firstName, String lastName, String username, String passwordHash, UserRole userRole) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
    }

    public User(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public static String hashPassword(String password){
        try {
            var digest = MessageDigest.getInstance("SHA-256")
                    .digest(password.getBytes());

            return new String(Base64.getEncoder()
                    .encode(digest));
        } catch (NoSuchAlgorithmException e) {
            String m = "System doesn't support SHA-256";
            //logger.error(m);

            //throw new UnsupportedAlgorithmException(m, e);
        }
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    public static class UserBuilder{

    }
}
