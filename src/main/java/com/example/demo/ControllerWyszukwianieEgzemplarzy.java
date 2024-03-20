package com.example.demo;

import com.example.demo.Containers.DisplayedEquipmentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class ControllerWyszukwianieEgzemplarzy implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedEquipmentEntity, Integer> ID;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, Double> cena;
    @FXML
    private TableView<DisplayedEquipmentEntity> egzemplarzTableView;
    @FXML
    private TextField filterTextBox;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> kategoria;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> kolor;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> nazwa_sprzetu;
    @FXML
    private Button powrotDoMenu;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> promocja;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> rozmiar;
    @FXML
    private Label searchLabel;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> status;

    ObservableList<DisplayedEquipmentEntity> displayedEquipmentEntityList = FXCollections.observableArrayList();

    public void showEquipmentEntity() {
        ResultSet resultSet = DataSource.performEgzemplarz_view();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    displayedEquipmentEntityList.add(new DisplayedEquipmentEntity(resultSet.getInt("ID"), resultSet.getString("nazwa_sprzetu"), resultSet.getString("kategoria"), resultSet.getDouble("cena"), resultSet.getString("promocja")
                    , resultSet.getString("kolor"), resultSet.getString("rozmiar"), resultSet.getString("status")));
                }
                egzemplarzTableView.setItems(displayedEquipmentEntityList);

                FilteredList<DisplayedEquipmentEntity> filteredData = new FilteredList<>(displayedEquipmentEntityList, b -> true);
                filterTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(productSearchModel -> {
                        if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                            return true;
                        }

                        String searchKeyword = newValue.toLowerCase();

                        if(productSearchModel.getKategoria().toLowerCase().startsWith(searchKeyword)){
                            return true;
                        }
                        else if(productSearchModel.getNazwa_sprzetu().toLowerCase().startsWith(searchKeyword)){
                            return true;
                        }
                        else{
                            return false;
                        }
                    });
                });

                SortedList<DisplayedEquipmentEntity> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(egzemplarzTableView.comparatorProperty());

                egzemplarzTableView.setItems(sortedData);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        } else {
            System.out.println("ResultSet equals to null");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nazwa_sprzetu.setCellValueFactory(new PropertyValueFactory<>("nazwa_sprzetu"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        promocja.setCellValueFactory(new PropertyValueFactory<>("promocja"));
        kolor.setCellValueFactory(new PropertyValueFactory<>("kolor"));
        rozmiar.setCellValueFactory(new PropertyValueFactory<>("rozmiar"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        showEquipmentEntity();
    }

    public void doMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/menu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ControllerMenu controllerMenu = loader.getController();
        controllerMenu.setLoggedEmployee();
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

}




