package com.example.javafxCat;

import com.example.javafxCat.dao.impl.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class login {
    @FXML
    private TextField id_user;

    @FXML
    private PasswordField password;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;
    private Runnable onLoginSuccess;


    // Méthode exécutée lorsque le bouton "OK" est cliqué
    @FXML
    private void okClicked() {
        String username = id_user.getText();
        String pswd = password.getText();

        // Votre code pour vérifier l'authentification avec la base de données MySQL
        try {
            // Récupérer une connexion à la base de données
            Connection connection = DB.getConnection();

            // Préparer la requête SQL pour vérifier les informations d'authentification
            String sql = "SELECT password FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
           // statement.setString(2, pswd);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Vérifier si l'utilisateur est authentifié avec succès
            if (resultSet.next()) {
                System.out.println("Authentification réussie !");
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            } else {
                System.out.println("Échec de l'authentification !");

            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode exécutée lorsque le bouton "Cancel" est cliqué
    @FXML
    private void cancelClicked() {
        // Votre code pour gérer l'annulation
        System.out.println("Opération annulée !");
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    public  void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }






}
