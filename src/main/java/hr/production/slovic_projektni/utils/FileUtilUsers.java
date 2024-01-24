package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileUtilUsers {

    private static final String USERS_FILE_NAME = "data/users.txt";

    public static void saveUserToFile(User user){
        File usersFile = new File(USERS_FILE_NAME);

        String formatString;

        List<User> existingUsers = getExistingUsers();
        existingUsers.add(new User(user.getId(), user.getUsername(), user.getPasswordHash(), user.getUserRole()));

        try(PrintWriter pw = new PrintWriter(usersFile)){
            for (User userNext : existingUsers){
                formatString = userNext.getUsername() + ":"
                        + userNext.getPasswordHash() + ":"
                        + userNext.getId() + ":"
                        + userNext.getUserRole().getName().toUpperCase();

                pw.println(formatString);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<User> getExistingUsers(){
        File usersFile = new File(USERS_FILE_NAME);
        List<User> existingUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            List<String> userData;
            while(Optional.of(line = reader.readLine()).isPresent()){
                userData = Arrays.asList(line.split(":"));
                existingUsers.add(new User(Long.parseLong(userData.get(2)),
                        userData.get(0),
                        userData.get(1),
                        UserRole.valueOf(userData.get(3))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex){
            System.out.println("Uspješan unos,");
        }
        return existingUsers;
    }

    public static User getLoggedInUser(String username, String hashPassword){
        User activeUser = null;
        List<User> users = getExistingUsers();

        for (User user : users) {
            if (user.getUsername().equals(username))
                if (user.getPasswordHash().equals(hashPassword)) {
                    return user;
                }
        }
        return activeUser;

    }

}
