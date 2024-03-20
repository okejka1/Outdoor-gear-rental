package com.example.demo;

import com.example.demo.Containers.DisplayedClient2;
import com.example.demo.Containers.DisplayedRental;
import com.example.demo.Containers.Employee;
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
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerWyszukiwanieWypozyczenie implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Employee loggedEmployee;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Wyszukiwanie Wypozyczen
    @FXML
    private TableColumn<DisplayedRental, Integer> ID;
    @FXML
    private TableColumn<DisplayedRental, Integer> PRACOWNIKID;
    @FXML
    private TableColumn<DisplayedRental, Double> cena_wypozyczenia;
    @FXML
    private TableColumn<DisplayedRental, Date> data_oddania;
    @FXML
    private TableColumn<DisplayedRental, Date> data_wypozyczenia;
    @FXML
    private TableColumn<DisplayedRental, Integer> egzID;
    @FXML
    private TableColumn<DisplayedRental, String> imie;
    @FXML
    private TableColumn<DisplayedRental, String> kategoria;
    @FXML
    private TableColumn<DisplayedRental, String> kolor;
    @FXML
    private TableColumn<DisplayedRental, String> nazwa;
    @FXML
    private TableColumn<DisplayedRental, String> nazwisko;
    @FXML
    private TableColumn<DisplayedRental, String> rozmiar;
    @FXML
    private TableView<DisplayedRental> wypozyczeniaTableView;
    @FXML
    private Button wyszukiwanieWypozyczenDoMenu;
    @FXML
    private Label wyszukiwanieLabel;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;


    ObservableList<DisplayedRental> displayedRentalsList = FXCollections.observableArrayList();
    public void showRentals() {
        ResultSet resultSet = DataSource.performWypozyczenie_view();
        if (resultSet != null) {

            try {

                while (resultSet.next()) {
                    displayedRentalsList.add(new DisplayedRental(resultSet.getInt("ID"), resultSet.getInt("PRACOWNIKID"), resultSet.getString("imie"), resultSet.getString("nazwisko"),
                    resultSet.getString("kategoria"), resultSet.getString("nazwa"), resultSet.getDouble("cena_wypozyczenia"), resultSet.getInt("egzID"), resultSet.getString("kolor"),
                            resultSet.getString("rozmiar"), resultSet.getDate("data_wypozyczenia"), resultSet.getDate("data_oddania")));
                }
                wypozyczeniaTableView.setItems(displayedRentalsList);

                FilteredList<DisplayedRental> filteredData = new FilteredList<>(displayedRentalsList, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(()->
                                person -> person.getImie().toLowerCase().startsWith(imieTextField.getText().toLowerCase())
                                        && person.getNazwisko().toLowerCase().startsWith(nazwiskoTextField.getText().toLowerCase())
                                        && String.valueOf(person.getID()).contains(idTextField.getText()) ,

                        imieTextField.textProperty(),
                        nazwiskoTextField.textProperty(),
                        idTextField.textProperty()
                ));

                SortedList<DisplayedRental> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(wypozyczeniaTableView.comparatorProperty());

                wypozyczeniaTableView.setItems(sortedData);


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
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SIEMA LECIMY, UDALO SIE");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        PRACOWNIKID.setCellValueFactory(new PropertyValueFactory<>("PRACOWNIKID"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        cena_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("cena_wypozyczenia"));
        egzID.setCellValueFactory(new PropertyValueFactory<>("egzID"));
        kolor.setCellValueFactory(new PropertyValueFactory<>("kolor"));
        rozmiar.setCellValueFactory(new PropertyValueFactory<>("rozmiar"));
        data_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("data_wypozyczenia"));
        data_oddania.setCellValueFactory(new PropertyValueFactory<>("data_oddania"));
        showRentals();
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

    public void setLoggedEmployee() {
        loggedEmployee = DataSource.getEmployee();
    }
}
