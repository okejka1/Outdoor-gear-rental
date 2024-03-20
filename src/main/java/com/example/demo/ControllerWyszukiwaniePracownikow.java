package com.example.demo;

import com.example.demo.Containers.DisplayedEmployee;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerWyszukiwaniePracownikow implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedEmployee, Integer> ID;
    @FXML
    private TableColumn<DisplayedEmployee, Date> data_zatrudnienia;
    @FXML
    private TableColumn<DisplayedEmployee, String> email;
    @FXML
    private TableColumn<DisplayedEmployee, String> imie;
    @FXML
    private TableColumn<DisplayedEmployee, String> nazwisko;
    @FXML
    private TableColumn<DisplayedEmployee, String> nr_dowodu;
    @FXML
    private TableColumn<DisplayedEmployee, String> nr_telefonu;
    @FXML
    private TableView<DisplayedEmployee> pracownicyTableView;
    @FXML
    private TableColumn<DisplayedEmployee, String> stanowisko;
    @FXML
    private Button wrocDoMenu;


    ObservableList<DisplayedEmployee> displayedEmployeesList = FXCollections.observableArrayList();
    public void showEmployees() {
        ResultSet resultSet = DataSource.performPracownik_view();

        if (resultSet != null) {
            try {
                while ((resultSet.next())) {
                    displayedEmployeesList.add(new DisplayedEmployee(resultSet.getString("stanowisko"), resultSet.getInt("ID"), resultSet.getString("imie"),
                            resultSet.getString("nazwisko"), resultSet.getString("nr_telefonu"), resultSet.getString("email"),
                            resultSet.getString("nr_dowodu"), resultSet.getDate("data_zatrudnienia")));
                }

                pracownicyTableView.setItems(displayedEmployeesList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        stanowisko.setCellValueFactory(new PropertyValueFactory<>("stanowisko"));
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nr_telefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        nr_dowodu.setCellValueFactory(new PropertyValueFactory<>("nr_dowodu"));
        data_zatrudnienia.setCellValueFactory(new PropertyValueFactory<>("data_zatrudnienia"));
        showEmployees();
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
