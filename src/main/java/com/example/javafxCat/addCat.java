package com.example.javafxCat;

import com.example.javafxCat.entities.Cat;
import com.example.javafxCat.service.CatService;


import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class addCat {

    @FXML
    private Button button_save;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_race;
    @FXML
    private TextField tf_sexe;
    @FXML
    private TextField tf_annee;
    @FXML
    private TextField tf_poids;
    @FXML
    private TableView<Cat> table;

    private  boolean update;
    int catID=0;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {
        String nom = this.tf_name.getText();
        String race = this.tf_race.getText();
        String sexe = this.tf_sexe.getText();
        String anneN = this.tf_annee.getText();
        String poids = this.tf_poids.getText();


        CatService catService = new CatService();
        Cat cat = new Cat(catID,nom, race,sexe, parseInt(anneN), parseDouble(poids));
        if(update==false){
            CatService.save(cat);
        }
        else{
            catService.update(cat,catID);
        }
        onClearButtonClick();

        this.tf_name.clear();
        this.tf_race.clear();
        this.tf_sexe.clear();
        this.tf_annee.clear();
        this.tf_poids.clear();

        // Reload the gameTableView
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameTableView.fxml"));
        Parent root = fxmlLoader.load();
        TableViewController controller = fxmlLoader.getController();
        controller.setCatService(catService);
        controller.loadData();

        Scene newScene = new Scene(root);

        // Reapply the CSS styles to the new scene
        newScene.getStylesheets().add("styles.css");

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set the new scene on the current stage
        currentStage.setScene(newScene);
        currentStage.show();
    }



    private Stage stage;
    private Scene scene;
    private Parent root;
    public void onBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameTableView.fxml"));
        Parent root = fxmlLoader.load();
        TableViewController controller = fxmlLoader.getController();
        CatService catService = new CatService();
        controller.setCatService(catService);
        controller.loadData();

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(primaryStage.getScene().getStylesheets()); // Add the current stylesheets to the new scene

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void onClearButtonClick() {
        this.tf_name.clear();
        this.tf_race.clear();
        this.tf_sexe.clear();
        this.tf_annee.clear();
        this.tf_poids.clear();
    }

    public void setUpdate(boolean b) {
        this.update=b;
    }

    public void setTextField(int id, String nom, String race,String sexe , int anneeN, double poids) {
        this.catID=id;
        this.tf_name.setText(nom);
        this.tf_race.setText(race);
        this.tf_sexe.setText(sexe);
        this.tf_annee.setText(String.valueOf(anneeN));
        this.tf_poids.setText(String.valueOf(poids));

    }
}