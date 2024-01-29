package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.ExistingUserException;
import hr.production.slovic_projektni.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileUtilUsers {

    public static final Logger logger = LoggerFactory.getLogger(FileUtilUsers.class);

    private static final String USERS_FILE_NAME = "data/users.txt";

    public static void changeUserPassword(String newPassword) {
        File usersFile = new File(USERS_FILE_NAME);

        List<User> existingUsers = getExistingUsers();
        try (PrintWriter pw = new PrintWriter(usersFile)) {
            for (User userNext : existingUsers) {

                if (userNext.getUsername().equals(MainApplication.getActiveUser().getUsername())) {
                    pw.println(userNext.getUsername() + ":" + User.hashPassword(newPassword));
                } else {
                    pw.println(userNext.getUsername() + ":" + userNext.getPasswordHash());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "Password isn't changed because: " + e.getMessage();
            logger.error(message);
            throw new RuntimeException(e);
        }


    }


    public static void createUserInFile(String username, String password) throws ExistingUserException {
        File usersFile = new File(USERS_FILE_NAME);
        List<User> existingUsers = getExistingUsers();

        Boolean usernameExist = existingUsers.stream()
                .anyMatch(u -> u.getUsername().equals(username));

        if (usernameExist) {
            String message = "Username already taken: " + username;
            logger.error(message);
            throw new ExistingUserException("User already exist.");
        } else {
            existingUsers.add(new User(Long.parseLong("1"), username, password));
        }

        try (PrintWriter pw = new PrintWriter(usersFile)) {
            for (User userNext : existingUsers) {

                pw.println(userNext.getUsername() + ":" + userNext.getPasswordHash());
            }
        } catch (FileNotFoundException e) {
            String message = "Cannot store users in file: " + e.getMessage();
            logger.error(message);
            throw new RuntimeException(e);
        }
    }

    public static List<User> getExistingUsers() {
        File usersFile = new File(USERS_FILE_NAME);
        List<User> existingUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            List<String> userData;
            while (Optional.of(line = reader.readLine()).isPresent()) {
                userData = Arrays.asList(line.split(":"));
                existingUsers.add(new User(Long.parseLong("1"),
                        userData.get(0),
                        userData.get(1)));
            }
        } catch (IOException e) {
            String message = "Problem with reading data from file: " + e.getMessage();
            logger.error(message);
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {
        }
        return existingUsers;
    }

    public static String getLoggedInUser(String username, String hashPassword) {
        List<User> users = getExistingUsers();

        for (User user : users) {
            if (user.getUsername().equals(username))
                if (user.getPasswordHash().equals(hashPassword)) {
                    MainApplication.setActiveUser(DatabaseUtilUsers.activeUser(user));
                    return "Successful login in application!";
                } else {
                    return "Incorrect password!";
                }
        }
        return "User doesn't exist.";
    }

}
