/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Rayed
 */
public class FXMLDocumentController implements Initializable {
    
    private Users user;
    
    @FXML private TextField nameField;
    
    @FXML private TextField sidField;
    
    @FXML private TextField emailField;
    
    @FXML private TextField passwordField;
    
    @FXML private Label loginLabel;
    
    @FXML
    protected void handleRegisterAction(ActionEvent event) throws IOException, SQLException{
        createNewUser();
        if (DatabaseConnection.insertData(user)){
            Parent root = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } 
    }

    private void createNewUser() throws NumberFormatException {
        user = new Users();
        user.setName(nameField.getText());
        user.setSid(Integer.parseInt(sidField.getText()));
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    Parent loginPage = FXMLLoader.load(getClass().getResource("FXMLLoginPage.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loginPage));
                    stage.show();
                    
                    Node source = (Node) t.getSource();
                    Stage current = (Stage) source.getScene().getWindow();
                    current.close();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  );
    }    
    
}
