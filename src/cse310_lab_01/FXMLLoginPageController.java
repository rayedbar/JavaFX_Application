/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rayed
 */
public class FXMLLoginPageController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label prompt;

    @FXML
    protected void handleLogInAction(ActionEvent event) throws IOException, SQLException {
        String email = emailField.getText();
        String password = passwordField.getText();

        ResultSet result = DatabaseConnection.getUserCredentials();
        while (result.next()) {
            if (result.getString("email").equals(email) && result.getString("password").equals(password)) {
                DatabaseConnection.user_id = result.getInt("id");
                Parent root = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();

                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }
        prompt.setText("Credentials Do Not Match!!!");
    }

    @FXML
    protected void handleAdminLoginAction(ActionEvent event) throws IOException {

        if (emailField.getText().equals("admin@hotmail.com") && passwordField.getText().equals("admin")) {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAdminPage.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            prompt.setText("No Are No Admin!!");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
