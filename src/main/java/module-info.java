module towerdef {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens towerdef.controller to javafx.fxml;

    exports towerdef;
    exports towerdef.controller;
    exports towerdef.model;
    exports towerdef.model.map;
    exports towerdef.model.entity;
}