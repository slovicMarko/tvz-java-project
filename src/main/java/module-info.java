module hr.production.slovic_projektni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    exports hr.production.slovic_projektni.controllers;
    opens hr.production.slovic_projektni.controllers to javafx.fxml;
    exports hr.production.slovic_projektni;
    opens hr.production.slovic_projektni to javafx.fxml;
}