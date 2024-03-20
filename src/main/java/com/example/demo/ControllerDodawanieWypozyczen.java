package com.example.demo;

import com.example.demo.Containers.DisplayedClient;
import com.example.demo.Containers.DisplayedEquipmentEntity;
import com.example.demo.Containers.Employee;
import com.example.demo.Containers.EquipmentCategory;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerDodawanieWypozyczen implements Initializable {
    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Employee loggedEmployee;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private DatePicker datePicker;
    @FXML
    private Label returnDateLabel;
    @FXML
    private Button usun;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, Integer> ID;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, Double> cena;
    @FXML
    private Label dodajEgzemplarzeLabel;
    @FXML
    private Label dodajKlientaLabel;
    @FXML
    private TableView<DisplayedEquipmentEntity> dostepneEgzemplarzeTableView;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, Double> egzCena;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzID;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzKategoria;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzKolor;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzNazwa_sprzetu;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzPromocja;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzRozmiar;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> egzStatus;
    @FXML
    private TableView<DisplayedEquipmentEntity> egzemplarzeDodaneTableView;
    @FXML
    private Label egzemplarzeLabel;
    @FXML
    private TableColumn<DisplayedClient, String> imie;
    @FXML
    private TextField imieTextField;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> kategoria;
    @FXML
    private ComboBox<String> kategoriaComboBox;
    @FXML
    private TableColumn<DisplayedClient, Integer> klientID;
    @FXML
    private TableView<DisplayedClient> klientTableView;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> kolor;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> nazwa_sprzetu;
    @FXML
    private TableColumn<DisplayedClient, String> nazwisko;
    @FXML
    private TextField nazwiskoTextField;
    @FXML
    private TableColumn<DisplayedClient, String> nr_telefonu;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> promocja;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> rozmiar;
    @FXML
    private TableColumn<DisplayedEquipmentEntity, String> status;
    @FXML
    private Button wrocDoMenuButton;
    @FXML
    private Button zatwierdzButton;

    ObservableList<DisplayedClient> displayedClients = FXCollections.observableArrayList();
    ObservableList<DisplayedEquipmentEntity> addedEquipmentEntities = FXCollections.observableArrayList();
    ObservableList<DisplayedEquipmentEntity> displayedEquipmentEntities = FXCollections.observableArrayList();

    public void showEquipmentEntity() {
        ResultSet resultSet = DataSource.performEgzemplarz_view();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    DisplayedEquipmentEntity displayed = new DisplayedEquipmentEntity(resultSet.getInt("ID"), resultSet.getString("nazwa_sprzetu"), resultSet.getString("kategoria"), resultSet.getDouble("cena"), resultSet.getString("promocja")
                            , resultSet.getString("kolor"), resultSet.getString("rozmiar"), resultSet.getString("status"));
                    if(displayed.getStatus().equals("dostepny")){
                        displayedEquipmentEntities.add(displayed);
                    }
                }
                dostepneEgzemplarzeTableView.setItems(displayedEquipmentEntities);


                FilteredList<DisplayedEquipmentEntity> filteredData = new FilteredList<>(displayedEquipmentEntities, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(() ->
                                entity -> {
                                    String selectedCategory = kategoriaComboBox.getSelectionModel().getSelectedItem();
                                    String searchText = nazwaTextField.getText();

                                    // Check if either category or text field is not empty
                                    boolean isCategoryNotEmpty = selectedCategory != null && !selectedCategory.isEmpty();
                                    boolean isSearchTextNotEmpty = searchText != null && !searchText.isEmpty();

                                    return (!isCategoryNotEmpty || entity.getKategoria().equals(selectedCategory)) &&
                                            (!isSearchTextNotEmpty || entity.getNazwa_sprzetu().contains(searchText));
                                },
                        kategoriaComboBox.valueProperty(),
                        nazwaTextField.textProperty()
                ));

                SortedList<DisplayedEquipmentEntity> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(dostepneEgzemplarzeTableView.comparatorProperty());

                dostepneEgzemplarzeTableView.setItems(sortedData);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        } else {
            System.out.println("ResultSet equals to null");
        }
    }

    public void showClients(){
        ResultSet resultSet = DataSource.performKlient_view();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    displayedClients.add(new DisplayedClient(resultSet.getInt("ID"), resultSet.getString("imie"), resultSet.getString("nazwisko"), resultSet.getString("nr_telefonu")));
                }
                klientTableView.setItems(displayedClients);

                FilteredList<DisplayedClient> filteredData = new FilteredList<>(displayedClients, b -> true);
                filteredData.predicateProperty().bind(Bindings.createObjectBinding(()->
                    person -> person.getImie().contains(imieTextField.getText())
                    && person.getNazwisko().contains(nazwiskoTextField.getText()),

                        imieTextField.textProperty(),
                        nazwiskoTextField.textProperty()
                ));

                SortedList<DisplayedClient> sortedData = new SortedList<>(filteredData);

                sortedData.comparatorProperty().bind(klientTableView.comparatorProperty());

                klientTableView.setItems(sortedData);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DataSource.closeResultSet(resultSet);
            }
        } else {
            System.out.println("ResultSet equals to null");
        }
    }

    public void showCategories() {
        ObservableList<String> equipmentCategoriesList = FXCollections.observableArrayList();
        ResultSet resultSet = DataSource.performSelectEquipCategory();
        if (resultSet != null) {
            try {
                while ((resultSet.next())) {
                    EquipmentCategory category = new EquipmentCategory(resultSet.getString("nazwa"));
                    equipmentCategoriesList.add(category.getNazwa());
                }

                kategoriaComboBox.setItems(equipmentCategoriesList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DataSource.closeResultSet(resultSet);
            }
        }

    }

    public void doMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/menu.fxml"));
        root = loader.load();
        ControllerMenu controllerMenu = loader.getController();
        controllerMenu.setLoggedEmployee();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }



    public void dodajEgzemplarzDoWypozyczenia(ActionEvent event) throws IOException {
        addedEquipmentEntities.add(dostepneEgzemplarzeTableView.getSelectionModel().getSelectedItem());
        egzemplarzeDodaneTableView.setItems(addedEquipmentEntities);
        displayedEquipmentEntities.remove(dostepneEgzemplarzeTableView.getSelectionModel().getSelectedItem());
        dostepneEgzemplarzeTableView.setItems(displayedEquipmentEntities);
    }

    public void usunDodanyEgzemplarz(ActionEvent event) throws  IOException {
        displayedEquipmentEntities.add(egzemplarzeDodaneTableView.getSelectionModel().getSelectedItem());
        dostepneEgzemplarzeTableView.setItems(displayedEquipmentEntities);
        addedEquipmentEntities.remove(egzemplarzeDodaneTableView.getSelectionModel().getSelectedItem());
        egzemplarzeDodaneTableView.setItems(addedEquipmentEntities);
    }

    public void zatwierdzNoweWypozyczenie(ActionEvent event) throws IOException {
        DisplayedClient client = klientTableView.getSelectionModel().getSelectedItem();
        LocalDate returnDate = datePicker.getValue();
        LocalDate rentalDate = LocalDate.now();
        Double totalPrice = 0d;
        if(client == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occured");
            alert.setContentText("Nie wybrano klienta");
            alert.show(); // Show the ale
            return;
        }
        if (returnDate == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occured");
            alert.setContentText("Nie wybrano daty zwrotu");
            alert.show(); // Show the ale
            return;
        }
        for (DisplayedEquipmentEntity entity : addedEquipmentEntities) {
            totalPrice+=entity.getCena();
        }
        int rentalID = DataSource.manageRentals(returnDate,totalPrice,rentalDate,loggedEmployee.getId(),client.getID(),"Insert");
        if (rentalID != -1) {
            System.out.println("Rental added successfully with ID: " + rentalID);
            // You can add additional logic or UI updates here
        } else {
            System.err.println("Failed to add rental.");
        }
        for (DisplayedEquipmentEntity entity : addedEquipmentEntities) {
            DataSource.performSetOnEquipmentEntityStatus(entity.getID(),"wypozyczony");
            int entityRentalID = DataSource.manageEntityRentals(rentalID,entity.getID(),"Insert");
            if (entityRentalID != -1) {
                System.out.println("EquipmentRental added successfully with ID: " + entityRentalID);
                // You can add additional logic or UI updates here
            } else {
                System.err.println("Failed to add equipment rental.");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCategories();
        klientID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nr_telefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        showClients();
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nazwa_sprzetu.setCellValueFactory(new PropertyValueFactory<>("nazwa_sprzetu"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        promocja.setCellValueFactory(new PropertyValueFactory<>("promocja"));
        kolor.setCellValueFactory(new PropertyValueFactory<>("kolor"));
        rozmiar.setCellValueFactory(new PropertyValueFactory<>("rozmiar"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        showEquipmentEntity();
        egzID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        egzNazwa_sprzetu.setCellValueFactory(new PropertyValueFactory<>("nazwa_sprzetu"));
        egzKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        egzCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        egzPromocja.setCellValueFactory(new PropertyValueFactory<>("promocja"));
        egzKolor.setCellValueFactory(new PropertyValueFactory<>("kolor"));
        egzRozmiar.setCellValueFactory(new PropertyValueFactory<>("rozmiar"));
        egzStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = ControllerMenu.loggedEmployee;
        System.out.println("Logged Employee in ControllerWypozyczenie: " + loggedEmployee);
    }
}
