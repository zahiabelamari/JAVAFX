module com.example.javafxgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;

    requires json.simple;
    requires com.google.gson;

    opens com.example.javafxCat.entities to gson;

    opens com.example.javafxCat to javafx.fxml,javafx.base;
    exports com.example.javafxCat;
    exports com.example.javafxCat.entities;
    exports com.example.javafxCat.service;
    exports com.example.javafxCat.dao;
    exports com.example.javafxCat.dao.impl;
}