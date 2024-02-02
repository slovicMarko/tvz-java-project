package hr.production.slovic_projektni.model;

import hr.production.slovic_projektni.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

public class User extends NamedEntity implements Serializable, Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(User.class);
    private String firstName;
    private String lastName;
    private Username username;
    private String passwordHash;
    private UserRole userRole;

    public User(Long id, String firstName, String lastName, Username username, String passwordHash, UserRole userRole) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
    }

    public User(Long id, Username username, String passwordHash) {
        super(id);
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User() {
        super(Long.parseLong("1"));
    }


    public static String hashPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256")
                    .digest(password.getBytes());

            return new String(Base64.getEncoder()
                    .encode(digest));
        } catch (NoSuchAlgorithmException e) {
            String m = "System doesn't support SHA-256";
            logger.error(m);

        }
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Username getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, username, passwordHash, userRole);
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            String message = "Error while cloning user for serialization";
            logger.error(message);
            Constants.errorAlert("Cloning error", message);
            throw new AssertionError();
        }
    }
}
