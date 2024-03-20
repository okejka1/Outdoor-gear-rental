package com.example.demo;

import com.example.demo.Containers.DisplayedRental;
import com.example.demo.Containers.DisplayedReturn;
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

public class ControllerWyszukiwanieZwrotow implements Initializable {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private TableColumn<DisplayedReturn, Integer> ID;
    @FXML
    private TableColumn<DisplayedReturn, Integer> wypID;
    @FXML
    private TableColumn<DisplayedReturn, Date> data_zwrotu;
    @FXML
    private TableColumn<DisplayedReturn, Integer> empID;
    @FXML
    private TableColumn<DisplayedReturn, String> imie;
    @FXML
    private TableColumn<DisplayedReturn, String> nazwisko;
    @FXML
    private Button wrocDoMenu;
    @FXML
    private TableView<DisplayedReturn> zwrotyTableView;
    @FXML
    private Label wyszukiwanieLabel;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    ObservableList<DisplayedReturn> displayedReturnsList = FXCollections.observableArrayList();

    public void showReturns() throws SQLException {
        ResultSet resultSet = DataSource.performZwrot_view();
        System.out.println("Show returns 1: resultSet -" + resultSet.isClosed());

        if(resultSet != null) {
            try{
                while((resultSet.next())) {
                    displayedReturnsList.add(new DisplayedReturn(resultSet.getInt("ID"), resultSet.getInt("wypID"), resultSet.getDate("data_zwrotu"),
                            resultSet.getInt("empID"), resultSet.getString("imie"), resultSet.getString("nazwisko")));
                }
                for( DisplayedReturn disReturn : displayedReturnsList ){
                    System.out.println(disReturn.toString());
                }

                zwrotyTableView.setItems(displayedReturnsList);

                FilteredList<DisplayedReturn> filteredData = new FilteredList<>(displayedReturnsList, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(()->
                                returned -> returned.getImie().toLowerCase().startsWith(imieTextField.getText().toLowerCase())
                                        && returned.getNazwisko().toLowerCase().startsWith(nazwiskoTextField.getText().toLowerCase())
                                        && String.valueOf(returned.getWypID()).contains(idTextField.getText()) ,

                        imieTextField.textProperty(),
                        nazwiskoTextField.textProperty(),
                        idTextField.textProperty()
                ));

                SortedList<DisplayedReturn> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(zwrotyTableView.comparatorProperty());

                zwrotyTableView.setItems(sortedData);

            }catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Show returns 2: resultSet -" + resultSet.isClosed());
                DataSource.closeResultSet(resultSet);
                System.out.println("Show returns 3: resultSet -" + resultSet.isClosed());
            }
        }

    }

    public void initialize(URL url, ResourceBundle rb){
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        wypID.setCellValueFactory(new PropertyValueFactory<>("wypID"));
        data_zwrotu.setCellValueFactory(new PropertyValueFactory<>("data_zwrotu"));
        empID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        try {
            showReturns();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


