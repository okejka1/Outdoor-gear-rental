package com.example.demo;

import com.example.demo.Containers.DisplayedEquipment;
import com.example.demo.Containers.DisplayedEquipmentEntity;
import com.example.demo.Containers.DisplayedRental;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Label;
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

public class ControllerWyszkiwanieSprzetu implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedEquipment, Integer> ID;
    @FXML
    private TableColumn<DisplayedEquipment, String> cena_wypozyczenia;
    @FXML
    private TableColumn<DisplayedEquipment, String> kategoria;
    @FXML
    private TableColumn<DisplayedEquipment, String> nazwa;
    @FXML
    private TableColumn<DisplayedEquipment, String> promocja;
    @FXML
    private Label wyszukiwanieLabel;
    @FXML
    private TextField kategoriaTextField;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TableView<DisplayedEquipment> sprzetTableView;

    ObservableList<DisplayedEquipment> displayedEquipmentList = FXCollections.observableArrayList();

    public void showEquipment() {
        ResultSet resultSet = DataSource.performSprzet_view();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    displayedEquipmentList.add(new DisplayedEquipment(resultSet.getString("kategoria"), resultSet.getString("nazwa"), resultSet.getString("cena_wypozyczenia"),
                            resultSet.getString("promocja"), resultSet.getInt("ID") ));
                }
                for(DisplayedEquipment equipment : displayedEquipmentList) {
                    System.out.println(equipment.toString());
                }
                sprzetTableView.setItems(displayedEquipmentList);

                FilteredList<DisplayedEquipment> filteredData = new FilteredList<>(displayedEquipmentList, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(()->
                                equipment -> equipment.getKategoria().toLowerCase().startsWith(kategoriaTextField.getText().toLowerCase())
                                        && equipment.getNazwa().toLowerCase().startsWith(nazwaTextField.getText().toLowerCase()),

                        kategoriaTextField.textProperty(),
                        nazwaTextField.textProperty()
                ));

                SortedList<DisplayedEquipment> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(sprzetTableView.comparatorProperty());

                sprzetTableView.setItems(sortedData);

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
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        cena_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("cena_wypozyczenia"));
        promocja.setCellValueFactory(new PropertyValueFactory<>("promocja"));
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        showEquipment();
    }
    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

}
