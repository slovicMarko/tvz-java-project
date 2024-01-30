package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.exception.ClickedOnInvalidContentException;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ChangesController<T> {

     private static final Logger logger = LoggerFactory.getLogger(ChangesController.class);

     @FXML TableView<SerializableObject<T>> serializedObjectTableView;
     @FXML TableColumn<SerializableObject<T>, String> classTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> attributeTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> userTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> dateTimeTableColumn;
     @FXML TextArea originalSerializedTextArea;
     @FXML TextArea changedSerializedTextArea;


     public void initialize(){
          List<SerializableObject<T>> serializableObjects = SerializableMethods.deserializeFromFile();

          serializedObjectTableView.setItems(FXCollections.observableArrayList((Collection<? extends SerializableObject<T>>) serializableObjects));

          initializeTableColumns();

          serializedObjectTableView.setOnMouseClicked((event)-> {
               try{
                    if (event.getClickCount() == 2){
                         selectingClickedChange();
                    }
               } catch (ClickedOnInvalidContentException ex){
                    logger.error(ex.getMessage());
                    System.out.println(ex.getMessage());
               }

          });

     }

     private void selectingClickedChange() {

          SerializableObject<T> selectedObject = serializedObjectTableView.getSelectionModel().getSelectedItem();
          if (selectedObject != null){
               if (selectedObject.getOriginal() instanceof User){
                    originalSerializedTextArea.setText(selectedUser((User) selectedObject.getOriginal()));
                    changedSerializedTextArea.setText(selectedUser((User) selectedObject.getChanged()));

               } else if (selectedObject.getOriginal() instanceof Comment){
                    originalSerializedTextArea.setText(selectedComment((Comment) selectedObject.getOriginal()));
                    changedSerializedTextArea.setText(selectedComment((Comment) selectedObject.getChanged()));

               } else if (selectedObject.getOriginal() instanceof Project){
                    originalSerializedTextArea.setText(selectedProject((Project) selectedObject.getOriginal()));
                    changedSerializedTextArea.setText(selectedProject((Project) selectedObject.getChanged()));
               }

          } else {
               throw new ClickedOnInvalidContentException("Clicked outside of the projects table.");
          }

     }

     private String selectedUser(User user){
          return "Class: User " +
                  "\n\nName: " + user.getFirstName() + " " + user.getLastName() +
                  "\n\nUsername: " + user.getUsername() +
                  "\n\nRole: " + user.getUserRole().getName() +
                  "\n\nHashed password: " + user.getPasswordHash();
     }
     private String selectedComment(Comment comment){
          String formatedString = "Class: Comment " +
                  "\n\nAuthor: " + comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName() +
                  "\n\nContent: " + comment.getContent() +
                  "\n\nUsers like this: \n";
          Map<Long, User> userMap = DatabaseUtilUsers.getUsersMap();
          for (Long id : comment.getLikes()){
               formatedString += "\t " + userMap.get(id).getFirstName() + " " + userMap.get(id).getLastName() + "\n";
          }
          return formatedString;
     }

     private String selectedProject(Project project){
          String formatedString = "Class: Project " +
                  "\n\nAuthor: " + project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName() +
                  "\n\nName: " + project.getName() +
                  "\n\nDescription: " + project.getDescription() +
                  "\n\nSubject: " + project.getSubject().getName() + ", " + project.getSubject().getProfessorName() +
                  "\n\nNumber of Comments: " + project.getComments().size();

          return formatedString;
     }



     private void initializeTableColumns() {
          classTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SerializableObject<T>, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TableColumn.CellDataFeatures<SerializableObject<T>, String> param) {
                    if (param.getValue().getOriginal() instanceof User){
                         return new ReadOnlyStringWrapper("User");

                    } else if (param.getValue().getOriginal() instanceof Comment){
                         return new ReadOnlyStringWrapper("Comment");

                    } else if (param.getValue().getOriginal() instanceof Project){
                         return new ReadOnlyStringWrapper("Project");
                    }
                    return new ReadOnlyStringWrapper("Class");
               }
          });
          attributeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SerializableObject<T>, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TableColumn.CellDataFeatures<SerializableObject<T>, String> param) {
                    if (param.getValue().getOriginal() instanceof User){
                         return new ReadOnlyStringWrapper(findAttributesUser((SerializableObject<User>) param.getValue()));

                    } else if (param.getValue().getOriginal() instanceof Comment){
                         return new ReadOnlyStringWrapper(findAttributesComment((SerializableObject<Comment>) param.getValue()));

                    } else if (param.getValue().getOriginal() instanceof Project){
                         return new ReadOnlyStringWrapper(findAttributesProject((SerializableObject<Project>) param.getValue()));
                    }

                    return new ReadOnlyStringWrapper("Attribute");
               }
          });
          userTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SerializableObject<T>, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TableColumn.CellDataFeatures<SerializableObject<T>, String> param) {
                    return new ReadOnlyStringWrapper(param.getValue().getMadeChange().getFirstName() + " " +
                            param.getValue().getMadeChange().getLastName() + " (" +
                            param.getValue().getMadeChange().getUserRole().getName() + ")");
               }
          });
          dateTimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SerializableObject<T>, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TableColumn.CellDataFeatures<SerializableObject<T>, String> param) {
                    return new ReadOnlyStringWrapper(param.getValue().getChangeTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")));
               }
          });
     }

     public String findAttributesUser(SerializableObject<User> users){
          if (!(users.getOriginal().getFirstName()+users.getOriginal().getLastName())
                  .equals((users.getChanged().getFirstName()+users.getChanged().getLastName()))){
               return  "Name";
          }
          if (!(users.getOriginal().getUserRole())
                  .equals((users.getChanged().getUserRole()))){
               return "Role";
          }
          if (!(users.getOriginal().getPasswordHash())
                  .equals((users.getChanged().getPasswordHash()))){
               return "Password";
          }
          return "Unknown";
     }

     public String findAttributesComment(SerializableObject<Comment> comments){
          if (!comments.getOriginal().getContent()
                  .equals(comments.getChanged().getContent())){
               return "Content";
          }
          if (!comments.getOriginal().getNumberOfLikes()
                  .equals(comments.getChanged().getNumberOfLikes())){
               return "Likes";
          }
          return "Unknown";
     }

     public String findAttributesProject(SerializableObject<Project> projects){
          String string = "";
          if (!projects.getOriginal().getSubject()
                  .equals(projects.getChanged().getSubject())){
               string = "Subject";
          }
          if (!projects.getOriginal().getName()
                  .equals(projects.getChanged().getName())){
               string += string!=""? ", Name":"Name";
          }
          if (!projects.getOriginal().getDescription()
                  .equals(projects.getChanged().getDescription())){
               string += string!=""? ", Description":"Description";
          }
          if (!projects.getOriginal().getComments()
                  .equals(projects.getChanged().getComments())){
               string += string!=""? ", Comments":"Comments";
          }
          return string;
     }

}
