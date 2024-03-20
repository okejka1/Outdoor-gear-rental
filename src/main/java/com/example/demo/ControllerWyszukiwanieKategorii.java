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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerWyszukiwanieKategorii implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private Button dodajKategorie;
    @FXML
    private TableView<EquipmentCategory> kategoriaTableView;
    @FXML
    private TableColumn<EquipmentCategory, String> nazwa;
    @FXML
    private TextField nowaKategoriaTextField;
    @FXML
    private Button wrocDoMenu;




    public void showCategories() {
        ObservableList<EquipmentCategory> equipmentCategoriesList = FXCollections.observableArrayList();
        ResultSet resultSet = DataSource.performSelectEquipCategory();

        if (resultSet != null) {
            try {
                while ((resultSet.next())) {
                    equipmentCategoriesList.add(new EquipmentCategory(resultSet.getString("nazwa")));
                }

                kategoriaTableView.setItems(equipmentCategoriesList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        showCategories();
    }

    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void updateTable(ActionEvent event) throws IOException {
        String newCategoryName = nowaKategoriaTextField.getText().trim();
        //System.out.println("New Category Name: " + newCategoryName);
        if (!newCategoryName.isEmpty()) {
            // Insert the new category into the database
            boolean success = DataSource.performInsertNewCategory(newCategoryName);
            //System.out.println("Insertion success: " + success);

            // Refresh the table to show the new category
            showCategories();

            // Clear the text field
            nowaKategoriaTextField.clear();

        }
    }
}
