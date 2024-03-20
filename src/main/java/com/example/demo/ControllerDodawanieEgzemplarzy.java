package com.example.demo;

import com.example.demo.Containers.DisplayedEquipment;
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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerDodawanieEgzemplarzy implements Initializable {
    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();
    @FXML
    private TableColumn<DisplayedEquipment, Integer> ID;
    @FXML
    private TableColumn<DisplayedEquipment, Double> cena_wypozyczenia;
    @FXML
    private Button dodajEgzemplarzButton;
    @FXML
    private TableColumn<DisplayedEquipment, String> kategoria;
    @FXML
    private Label kolorLabel;
    @FXML
    private TextField kolorTextField;
    @FXML
    private TableColumn<DisplayedEquipment, String> nazwa;
    @FXML
    private TableColumn<DisplayedEquipment, String> promocja;
    @FXML
    private Label rozmiarLabel;
    @FXML
    private TextField rozmiarTextField;
    @FXML
    private Label sprzetLabel;
    @FXML
    private TableView<DisplayedEquipment> sprzetTableView;
    @FXML
    private Button wrocDoMenu;

    public void showEquipment() {
        ObservableList<DisplayedEquipment> displayedEquipmentList = FXCollections.observableArrayList();
        ResultSet resultSet = DataSource.performSprzet_view();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    displayedEquipmentList.add(new DisplayedEquipment(resultSet.getString("kategoria"), resultSet.getString("nazwa"), resultSet.getString("cena_wypozyczenia"),
                            resultSet.getString("promocja"), resultSet.getInt("ID")));
                }
                for (DisplayedEquipment equipment : displayedEquipmentList) {
                    System.out.println(equipment.toString());
                }
                sprzetTableView.setItems(displayedEquipmentList);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        } else {
            System.out.println("ResultSet equals to null");
        }
    }

    public void dodajEgzemplarz(ActionEvent event) throws IOException {
        String equipmentEntitySize = rozmiarTextField.getText();
        String equipmentEntityColor = kolorTextField.getText();
        String equipmentEntityStatus = "dostepny";

        Integer equipmentID = sprzetTableView.getSelectionModel().getSelectedItem().getID();


        if (equipmentEntitySize.isEmpty() || equipmentEntityColor.isEmpty() || equipmentID == null) {
            // Handle empty fields
            System.out.println("One of the parameters blank or null");
            return;
        }
        // Assuming you have a method to call the stored procedure and pass the parameters
        int equipmentEntityID = DataSource.manageEquipmentEntity(equipmentEntityColor, equipmentEntitySize, equipmentEntityStatus, equipmentID, "Insert");
        System.out.println();
        if (equipmentEntityID != -1) {
            System.out.println("Equipment added successfully with ID: " + equipmentEntityID);
            // You can add additional logic or UI updates here
        } else {
            System.err.println("Failed to add equipment.");
        }

    }

    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        cena_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("cena_wypozyczenia"));
        promocja.setCellValueFactory(new PropertyValueFactory<>("promocja"));
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        showEquipment();
    }
}
