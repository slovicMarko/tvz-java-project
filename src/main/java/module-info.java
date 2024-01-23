module hr.production.slovic_projektni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires org.slf4j;


    opens hr.production.slovic_projektni to javafx.fxml;
    exports hr.production.slovic_projektni.controllers;
    opens hr.production.slovic_projektni.controllers to javafx.fxml;
    exports hr.production.slovic_projektni.main;
    opens hr.production.slovic_projektni.main to javafx.fxml;
    exports hr.production.slovic_projektni;
}