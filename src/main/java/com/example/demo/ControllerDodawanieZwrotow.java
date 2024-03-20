package com.example.demo;

import com.example.demo.Containers.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerDodawanieZwrotow implements Initializable {
    private DataSource dataSource;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedRental2, Integer> ID;
    @FXML
    private TableColumn<DisplayedRental2, Date> data_oddania;
    @FXML
    private TableColumn<DisplayedRental2, Date> data_wypozyczenia;
    @FXML
    private TableColumn<DisplayedRental2, Integer> empId;
    @FXML
    private TableColumn<DisplayedRental2, String> imie;
    @FXML
    private TableColumn<DisplayedRental2, String> nazwisko;
    @FXML
    private TableColumn<DisplayedRental2, String> nr_telefonu;
    @FXML
    private Button wrocDoMenuButton;
    @FXML
    private TableView<DisplayedRental2> wypozyczeniaTableView;
    @FXML
    private Button zwrocWypozyczenieButton;

    ObservableList<DisplayedRental2> displayedRentalsList = FXCollections.observableArrayList();
    ObservableList<DisplayedReturn> returnsDone = FXCollections.observableArrayList();



    public void fillReturnList() {

        ResultSet resultSetReturns = DataSource.performZwrot_view();

        if (resultSetReturns != null) {
            try {
                while ((resultSetReturns.next())) {
                    returnsDone.add(new DisplayedReturn(resultSetReturns.getInt("ID"), resultSetReturns.getInt("wypID"), resultSetReturns.getDate("data_zwrotu"),
                            resultSetReturns.getInt("empID"), resultSetReturns.getString("imie"), resultSetReturns.getString("nazwisko")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
            System.out.println("CLOSIN 1");
            }
        }
    }

    public void showRentals() {

        ResultSet resultSet = DataSource.performWypozyczenie_view2();

        if (resultSet != null) {

            try {

                while (resultSet.next()) {
                    DisplayedRental2 displayedRental2 = new DisplayedRental2(resultSet.getInt("ID"), resultSet.getDate("data_wypozyczenia"),
                            resultSet.getDate("data_oddania"), resultSet.getString("imie"), resultSet.getString("nazwisko"),
                            resultSet.getString("nr_telefonu"), resultSet.getInt("empId"));
                    boolean alreadyReturned = false;
                    for (DisplayedReturn returned: returnsDone) {
                        if (returned.getWypID() == displayedRental2.getID()){
                            alreadyReturned = true;
                            break;
                        }
                    }
                    if(!alreadyReturned){
                        displayedRentalsList.add(displayedRental2);
                    }
                }

                wypozyczeniaTableView.setItems(displayedRentalsList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
                System.out.println("CLOSING 2");
            }
        } else {
            System.out.println("ResultSet equals to null");
        }
    }

    public void zwrocWypozyczenie(ActionEvent event) throws IOException {
        DisplayedRental2 rentalToReturn = wypozyczeniaTableView.getSelectionModel().getSelectedItem();
        ObservableList<EquipmentEntityRental> equipmentEntitiesReturn = FXCollections.observableArrayList();
        ResultSet resultSet2 = DataSource.performSelectEquipmentEntities(rentalToReturn.getID());

        if(resultSet2 != null){
            System.out.println("GIT ale jest closed");
        }
        try {
            System.out.println(resultSet2.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultSet2 != null) {
            try {
                while (resultSet2.next()) {
                    System.out.println("WHAT");
                    equipmentEntitiesReturn.add(new EquipmentEntityRental(resultSet2.getInt("WYPOZYCZENIEID"), resultSet2.getInt("EGZEMPLARZ_SPRZETUID")));
                    System.out.println("NAAAH");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
               DataSource.closeResultSet(resultSet2);
                System.out.println("CLOSING 3");
            }
            for (EquipmentEntityRental entityRental:equipmentEntitiesReturn) {
                DataSource.performSetOnEquipmentEntityStatus(entityRental.getEquipmentEntityID(),"dostepny");
            }
            int returnId = DataSource.manageReturns(rentalToReturn.getID(),"Insert");
            if (returnId != -1) {
                System.out.println("Return added successfully with ID: " + returnId);
                // You can add additional logic or UI updates here

                displayedRentalsList.remove(wypozyczeniaTableView.getSelectionModel().getSelectedItem());
                wypozyczeniaTableView.setItems(displayedRentalsList);
            } else {
                System.err.println("Failed to add equipment return.");
            }
        } else {
            System.out.println("ResultSet equals to null");
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillReturnList();
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        data_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("data_wypozyczenia"));
        data_oddania.setCellValueFactory(new PropertyValueFactory<>("data_oddania"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nr_telefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        empId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        showRentals();
    }
}
