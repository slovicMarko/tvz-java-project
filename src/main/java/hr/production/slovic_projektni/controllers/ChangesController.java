package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.exception.ClickedOnInvalidContentException;
import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.FindLastSerializableChangeThread;
import hr.production.slovic_projektni.threads.GetSerializableDataThread;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChangesController<T> {

     private static final Logger logger = LoggerFactory.getLogger(ChangesController.class);

     @FXML TableView<SerializableObject<T>> serializedObjectTableView;
     @FXML TableColumn<SerializableObject<T>, String> classTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> attributeTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> userTableColumn;
     @FXML TableColumn<SerializableObject<T>, String> dateTimeTableColumn;
     @FXML TextArea originalSerializedTextArea;
     @FXML TextArea changedSerializedTextArea;

     private GetSerializableDataThread<T> getSerializableDataThread;
     private static ScheduledExecutorService executorService;
     private List<SerializableObject<T>> serializableObjectList = new ArrayList<>();


     public void initialize(){

          FindLastSerializableChangeThread<T> findLastSerializableChangeThread = new FindLastSerializableChangeThread<T>();
          findLastSerializableChangeThread.run();

          Platform.runLater(() -> {
               SerializableObject<T> selectedObject = findLastSerializableChangeThread.getLastData();
               displayLastChangeInTextArea(selectedObject);
          });


          executorService = Executors.newSingleThreadScheduledExecutor();
          executorService.scheduleAtFixedRate(() -> {
               Platform.runLater(() -> {
                    if (!MainApplication.getFxmlName().equals("changes.fxml")){
                         executorService.shutdownNow();
                    } else{
                         getSerializableDataThread = new GetSerializableDataThread<>();
                         getSerializableDataThread.run();

                         List<SerializableObject<T>> updatedObjects = getSerializableDataThread.getSerializedList();

                         if (!serializableObjectList.equals(updatedObjects)){
                              serializableObjectList = updatedObjects;
                              serializedObjectTableView.setItems(FXCollections.observableArrayList(updatedObjects));
                         }
                    }
               });
          }, 0, 10, TimeUnit.SECONDS);

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

     private void displayLastChangeInTextArea(SerializableObject<T> selectedObject) {
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
     }

     private void selectingClickedChange() throws ClickedOnInvalidContentException{

          SerializableObject<T> selectedObject = serializedObjectTableView.getSelectionModel().getSelectedItem();
          if (selectedObject != null){
               displayLastChangeInTextArea(selectedObject);
          } else {
               String message = "Clicked outside of the projects table.";
               logger.warn(message);
               throw new ClickedOnInvalidContentException(message);
          }

     }

     private String selectedUser(User user){
          if (user.getFirstName() == null){
               return "Name: " +
                       "\n\nUsername: " +
                       "\n\nRole: " +
                       "\n\nHashed password: ";
          }
          return "Name: " + user.getFirstName() + " " + user.getLastName() +
                  "\n\nUsername: " + user.getUsername().username() +
                  "\n\nRole: " + user.getUserRole().getName() +
                  "\n\nHashed password: " + user.getPasswordHash();
     }

     private String selectedComment(Comment comment){
          if (comment.getContent() == null){
               return "Author: " +
                       "\n\nContent: " +
                       "\n\nNumber of likes: \n";
          }
          String formatedString = "Author: " + comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName() +
                  "\n\nContent: " + comment.getContent() +
                  "\n\nNumber of likes: " + comment.getLikes().size();

          return formatedString;
     }

     private String selectedProject(Project project){
          if (project.getName().isEmpty()){
               return "Author: " +
                       "\n\nName: " +
                       "\n\nDescription: " +
                       "\n\nSubject: " +
                       "\n\nNumber of Comments: ";
          } return "Author: " + project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName() +
                  "\n\nName: " + project.getName() +
                  "\n\nDescription: " + project.getDescription() +
                  "\n\nSubject: " + project.getSubject().getName() + ", " + project.getSubject().getProfessorName() +
                  "\n\nNumber of Comments: " + project.getComments().size();
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

                    return new ReadOnlyStringWrapper("Changed Attribute");
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
          if (users.getOriginal().getFirstName() == null){
               return "New User";
          }
          if (users.getChanged().getFirstName() == null){
               return "User deleted";
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
          if (comments.getOriginal().getContent() == null){
               return "New Comment";
          }
          if (comments.getChanged().getContent() == null){
               return "Comment deleted";
          }
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
          if (projects.getOriginal().getName().isEmpty()){
               return "New Project";
          }
          if (projects.getChanged().getName().isEmpty()){
               return "Project deleted";
          }
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
