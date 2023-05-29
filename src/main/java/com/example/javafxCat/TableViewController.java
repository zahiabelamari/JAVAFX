package com.example.javafxCat;

import com.example.javafxCat.entities.Cat;
import com.example.javafxCat.service.CatService;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViewController implements Initializable {
    @FXML
    private TableColumn<Cat, Integer> id;
    @FXML
    private TableColumn<Cat, String> col_nom;
    @FXML
    private TableColumn<Cat, String> col_race;
    @FXML
    private TableColumn<Cat, String> col_sexe;
    @FXML
    private TableColumn<Cat, Integer> col_annee;
    @FXML
    private TableColumn<Cat, Double> col_poids;
    @FXML
    private TableView<Cat> catTable;

    @FXML
    private TextField rowCountLabel;
    @FXML
    private Button ajouter;
    @FXML
    private Label gameCountText;
    private CatService catService;

    private IntegerProperty rowCount;

    ObservableList<Cat> catList = FXCollections.observableArrayList();

    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameTableView.fxml"));
        Parent root = fxmlLoader.load();
        TableViewController controller = fxmlLoader.getController();
        CatService catService = new CatService();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        addDeleteAndUpdateButtons();
        catService = new CatService();
    }



    private void configureTableColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_race.setCellValueFactory(new PropertyValueFactory<>("race"));
        col_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        col_annee.setCellValueFactory(new PropertyValueFactory<>("anneeNaissance"));
        col_poids.setCellValueFactory(new PropertyValueFactory<>("poids"));

    }

    void setCatService(CatService catService) {
        this.catService = catService;
    }

    void loadData() {
        catList.addAll(catService.findAll());
        catTable.setItems(catList);
    }

    @FXML
    private void handleAjouterButtonAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addGameForms.fxml"));
            Parent afficherPage = loader.load();

            // Create the scene to display the loaded FXML file
            Scene afficherScene = new Scene(afficherPage);

            // Load the styles.css file
            afficherScene.getStylesheets().add("styles.css");

            // Get the primary stage and update its scene
            Stage primaryStage = (Stage) ajouter.getScene().getWindow();
            primaryStage.setScene(afficherScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public  void exporter()
    {
        catService.exporterVersExcel("src/main/resources/fileExcel.xlsx");
    }
    private Stage stage;
    private Scene scene;
    @FXML
    public  void importer(ActionEvent event) throws IOException {
        catService.importerDepuisExcel("src/main/resources/fileExcel.xlsx");
        reload(event);


    }
    private void addDeleteAndUpdateButtons(){
        // Create the "Supprimer" column
        TableColumn<Cat, Void> deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(new Callback<TableColumn<Cat, Void>, TableCell<Cat, Void>>() {
            @Override
            public TableCell<Cat, Void> call(TableColumn<Cat, Void> param) {
                return new TableCell<Cat, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                        deleteButton.setOnAction(event -> {
                            Cat cat = getTableView().getItems().get(getIndex());
                            catService.delete(cat);
                            try {
                                reload(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }


                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        });

        // Create the "Modifier" column
        TableColumn<Cat, Void> updateColumn = new TableColumn<>("Modifier");
        updateColumn.setCellFactory(new Callback<TableColumn<Cat, Void>, TableCell<Cat, Void>>() {
            @Override
            public TableCell<Cat, Void> call(TableColumn<Cat, Void> param) {
                return new TableCell<Cat, Void>() {
                    private final Button updateButton = new Button("Update");

                    {
                        updateButton.setOnAction(event -> {
                            Cat cat = getTableView().getItems().get(getIndex());

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateGameForm.fxml"));
                            try {
                                Parent root = loader.load();
                                addCat addCatController = loader.getController();
                                addCatController.setUpdate(true);
                                addCatController.setTextField(cat.getId(), cat.getNom(), cat.getRace(), cat.getSexe(), cat.getAnneeNaissance(), cat.getPoids());

                                Scene scene = new Scene(root);
                                scene.getStylesheets().add("styles.css");

                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } catch (IOException e) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        });

                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(updateButton);
                        }
                    }
                };
            }
        });

        // Add the delete and update columns to the TableView
        catTable.getColumns().add(deleteColumn);
        catTable.getColumns().add(updateColumn);
    }


}
