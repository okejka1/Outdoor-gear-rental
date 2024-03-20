package com.example.demo;

import com.example.demo.Containers.DisplayedClient;
import com.example.demo.Containers.DisplayedClient2;
import com.example.demo.Containers.DisplayedEquipmentEntity;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerWyszukiwanieKlientow implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedClient2, Integer> ID;
    @FXML
    private TableColumn<DisplayedClient2, String> email;
    @FXML
    private TableColumn<DisplayedClient2, String> imie;
    @FXML
    private TableColumn<DisplayedClient2, String> kod_pocztowy;
    @FXML
    private TableColumn<DisplayedClient2, String> miasto;
    @FXML
    private TableColumn<DisplayedClient2, String> nazwisko;
    @FXML
    private TableColumn<DisplayedClient2, String> nr_dowodu;
    @FXML
    private TableColumn<DisplayedClient2, String> nr_telefonu;
    @FXML
    private TableColumn<DisplayedClient2, String> numer_domu;
    @FXML
    private TableColumn<DisplayedClient2, String> numer_mieszkania;
    @FXML
    private TableColumn<DisplayedClient2, String> ulica;
    @FXML
    private Button wrocDoMenu;
    @FXML
    private TableView<DisplayedClient2> klientTableView;
    @FXML
    private Label wyszukwianieLabel;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    ObservableList<DisplayedClient2> displayedClientsList = FXCollections.observableArrayList();

    public void showClients() {
        ResultSet resultSet = DataSource.performKlient_view2();

        if(resultSet != null) {
            try{
                while((resultSet.next())) {
                    displayedClientsList.add(new DisplayedClient2(resultSet.getInt("ID"), resultSet.getString("imie"), resultSet.getString("nazwisko"), resultSet.getString("nr_telefonu"), resultSet.getString("email"),
                            resultSet.getString("nr_dowodu"), resultSet.getString("miasto"), resultSet.getString("ulica"), resultSet.getString("numer_domu"), resultSet.getString("numer_mieszkania"),
                            resultSet.getString("kod_pocztowy")));
                }

                klientTableView.setItems(displayedClientsList);

                FilteredList<DisplayedClient2> filteredData = new FilteredList<>(displayedClientsList, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(()->
                                person -> person.getImie().toLowerCase().startsWith(imieTextField.getText().toLowerCase())
                                        && person.getNazwisko().toLowerCase().startsWith(nazwiskoTextField.getText().toLowerCase())
                                        && String.valueOf(person.getID()).contains(idTextField.getText()) ,

                        imieTextField.textProperty(),
                        nazwiskoTextField.textProperty(),
                        idTextField.textProperty()
                ));

                SortedList<DisplayedClient2> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(klientTableView.comparatorProperty());

                klientTableView.setItems(sortedData);

            }catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("tutaj?");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nr_telefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        nr_dowodu.setCellValueFactory(new PropertyValueFactory<>("nr_dowodu"));
        miasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
        ulica.setCellValueFactory(new PropertyValueFactory<>("ulica"));
        numer_domu.setCellValueFactory(new PropertyValueFactory<>("numer_domu"));
        numer_mieszkania.setCellValueFactory(new PropertyValueFactory<>("numer_mieszkania"));
        kod_pocztowy.setCellValueFactory(new PropertyValueFactory<>("kod_pocztowy"));
        showClients();

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
