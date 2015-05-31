/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Rayed
 */
public class FXMLHomePageController implements Initializable {

    @FXML private ToggleGroup slots;
    
    @FXML private Label label;
    
    @FXML private RadioButton slot01RadioBtn;
    
    @FXML private RadioButton slot02RadioBtn;
    
    @FXML private RadioButton slot03RadioBtn;
    
    @FXML private Button registerButton;
    
    private int slotID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slot01RadioBtn.setUserData(1);
        slot02RadioBtn.setUserData(2);
        slot03RadioBtn.setUserData(3);
        
        slots.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton rb = (RadioButton) t1.getToggleGroup().getSelectedToggle();
                slotID = (int) rb.getUserData();
                try {
                    ResultSet result = DatabaseConnection.getSlotSeats(slotID);
                    int seatNo = 0;
                    while (result.next()){
                        seatNo = result.getInt("seats");
                    }
                    label.setText(seatNo + " seats remaining");
                    if (seatNo <= 0){
                        registerButton.setDisable(true);
                    } else {
                        registerButton.setDisable(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if (DatabaseConnection.registerSlot()){
                        label.setText("Well Done! You are now registered");
                    } else {
                        label.setText("Registration Failed");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
