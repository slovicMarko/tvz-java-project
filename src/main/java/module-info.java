module hr.production.slovic_projektni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hr.production.slovic_projektni to javafx.fxml;
    exports hr.production.slovic_projektni.controllers;
    opens hr.production.slovic_projektni.controllers to javafx.fxml;
    exports hr.production.slovic_projektni.main;
    opens hr.production.slovic_projektni.main to javafx.fxml;
    exports hr.production.slovic_projektni;
    exports hr.production.slovic_projektni.model.oldModel;
    opens hr.production.slovic_projektni.model.oldModel to javafx.fxml;
}