package com.example.demo;

import com.example.demo.Containers.EquipmentCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

public class ControllerDodawanieSprzetu implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private Label cenaLabel;
    @FXML
    private Button dodajKategorie;
    @FXML
    private Label kategoriaLabel;
    @FXML
    private ListView<String> kategoriaListView;
    @FXML
    private Label nazwaLabel;
    @FXML
    private Button wrocDoMenu;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TextField cenaTextField;

    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void showCategories() {
        ObservableList<String> equipmentCategoriesList = FXCollections.observableArrayList();
        ResultSet resultSet = DataSource.performSelectEquipCategory();

        if (resultSet != null) {
            try {
                while ((resultSet.next())) {
                    equipmentCategoriesList.add(new EquipmentCategory(resultSet.getString("nazwa")).getNazwa());
                }

                kategoriaListView.setItems(equipmentCategoriesList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCategories();
    }


    public void dodajSprzet(ActionEvent event) throws IOException {
        String equipmentName = nazwaTextField.getText();
        String rentalPriceText = cenaTextField.getText();
        String categoryName = kategoriaListView.getSelectionModel().getSelectedItem();

        if (equipmentName.isEmpty() || rentalPriceText.isEmpty() || categoryName == null) {
            // Handle empty fields
            return;
        }

        try {
            float rentalPrice = Float.parseFloat(rentalPriceText);

            // Assuming you have a method to call the stored procedure and pass the parameters
            int equipmentID = DataSource.manageEquipment(equipmentName, rentalPrice, "", categoryName, "Insert");

            if (equipmentID != -1) {
                System.out.println("Equipment added successfully with ID: " + equipmentID);
                // You can add additional logic or UI updates here
            } else {
                System.err.println("Failed to add equipment.");
            }
        } catch (NumberFormatException e) {
            // Handle invalid rental price format
            System.err.println("Invalid rental price format.");
        }
    }


}
