package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.User;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtilComment {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtilComment.class);


    public static void updateCommentContent(Comment comment) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updateSqlQuery = "UPDATE COMMENT " +
                    "SET CONTENT = ? " +
                    "WHERE AUTHOR_ID = ? AND ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(updateSqlQuery);
            pstmt.setString(1, comment.getContent());
            pstmt.setLong(2, comment.getAuthor().getId());
            pstmt.setLong(3, comment.getId());
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unsuccessful save!");
            alert.setContentText("Your change isn't saved.");
            alert.showAndWait();

            String message = "Comment is not changed.";
            logger.error(message);

        }
    }

    public static void updateCommentLikes(Comment comment) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updateSqlQuery = "UPDATE COMMENT " +
                    "SET LIKES = ? " +
                    "WHERE AUTHOR_ID = ? AND ID = ?";

            String likes = comment.getLikes().isEmpty() ? "," : "";
            for (Long id : comment.getLikes()) {
                likes += (id + ",");
            }

            PreparedStatement pstmt = connection.prepareStatement(updateSqlQuery);
            pstmt.setString(1, likes);
            pstmt.setLong(2, comment.getAuthor().getId());
            pstmt.setLong(3, comment.getId());
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unsuccessful save!");
            alert.setContentText("Your impression isn't saved.");
            alert.showAndWait();

            String message = "Like is not applied to comment.";
            logger.error(message);

        }
    }

    public static Long saveComment(Long authorId, String content, Long projectId) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updateSqlQuery = "INSERT INTO COMMENT (AUTHOR_ID, CONTENT, PROJECT_ID) " +
                    "VALUES(?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(updateSqlQuery, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, authorId);
            pstmt.setString(2, content);
            pstmt.setLong(3, projectId);
            pstmt.execute();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unsuccessful save!");
            alert.setContentText("Your comment isn't saved.");
            alert.showAndWait();

            String message = "Comment is not saved.";
            logger.error(message);

        }
        return null;
    }


    public static List<Comment> getComments(Long projectId, Map<Long, User> users) {
        List<Comment> comments = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT COMMENT.*\n" +
                    "FROM COMMENT\n" +
                    "JOIN PROJECT ON COMMENT.PROJECT_ID = PROJECT.ID\n" +
                    "WHERE COMMENT.PROJECT_ID  = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, projectId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                User author = users.get(resultSet.getLong("AUTHOR_ID"));
                String content = resultSet.getString("CONTENT");
                String likes = resultSet.getString("LIKES");

                List<Long> likesId = new ArrayList<>();
                for (String stringId : likes.split(",")) {
                    likesId.add(Long.parseLong(stringId));
                }

                comments.add(new Comment(id, author, content, likesId));
            }

        } catch (SQLException | IOException ex) {
            String message = "Problem while catching Project comments.";
            logger.error(message);
        }
        return comments;
    }
}
