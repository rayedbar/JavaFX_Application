/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rayed
 */
public class FXMLAdminPageController implements Initializable {

    @FXML
    private ComboBox<String> sections;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, Integer> sidColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    private ObservableList<Student> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sections.getItems().addAll("Section-01", "Section-02", "Section-03");
        sections.setPromptText("See List of Enrolled Students");

        sections.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            @SuppressWarnings("unchecked")
            public void handle(ActionEvent t) {
                int slot_id = 0;
                String section = sections.getValue();
                switch (section) {
                    case "Section-01":
                        slot_id = 1;
                        break;
                    case "Section-02":
                        slot_id = 2;
                        break;
                    case "Section-03":
                        slot_id = 3;
                }

                data = DatabaseConnection.getEnrolledStudents(slot_id);

                nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
                sidColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("sid"));
                emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

                studentTable.setItems(data);
            }
        });
    }

}
