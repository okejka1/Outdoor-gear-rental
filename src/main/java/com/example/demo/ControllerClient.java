package com.example.demo;

import com.example.demo.DataSource;
import com.example.demo.Containers.Client;
import com.example.demo.Containers.Adress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerClient {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Klient Menu
    @FXML
    private Button dodawanieKlientaButton;
    @FXML
    private Button edycjaKlientMenuButton;
    @FXML
    private Label edycjaKlientowLabel;
    @FXML
    private Button wyszukiwanieKlientowButton;

    //Dodawanie Klienta
    @FXML
    private Label adresKlienta;
    @FXML
    private TextField adresKlientaTextField;
    @FXML
    private Button dodajKlienta;
    @FXML
    private Label dodawanieKlientowLabel;
    @FXML
    private Button dodawanieKlientowMenuButton;
    @FXML
    private TextField domKlientaTextField;
    @FXML
    private Label imieKlienta;
    @FXML
    private TextField imieKlientaTextField;
    @FXML
    private Label kodPocztowyKlienta;
    @FXML
    private TextField kodPocztowyKlientaTextField;
    @FXML
    private Label mailKlienta;
    @FXML
    private TextField mailKlientaTextField;
    @FXML
    private Label miastoKlienta;
    @FXML
    private TextField miastoKlientaTextField;
    @FXML
    private TextField mieszkanieKlientaTextField;
    @FXML
    private Label nazwiskoKlienta;
    @FXML
    private TextField nazwiskoKlientaTextField;
    @FXML
    private Label nrDomuKlienta;
    @FXML
    private Label nrDowoduKlienta;
    @FXML
    private Label nrMieszkaniaKlienta;
    @FXML
    private TextField numerDowoduKlientaTextField;
    @FXML
    private Label telKlienta;
    @FXML
    private TextField telefonKlientaTextField;



    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doDodawaniaKlienta(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Klient/dodawanieNowychKlientow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void wyszukajKlientow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Klient/wyszukiwanieKlientow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void dodajKlienta(ActionEvent event) throws IOException {
        // Get input from the user
        String name = imieKlientaTextField.getText();
        String surname = nazwiskoKlientaTextField.getText();
        String phoneNumber = telefonKlientaTextField.getText();
        String email = mailKlientaTextField.getText();
        String idNumber = numerDowoduKlientaTextField.getText();
        String city = miastoKlientaTextField.getText();
        String postalCode = kodPocztowyKlientaTextField.getText();
        String street = adresKlientaTextField.getText();
        String flatNr = mieszkanieKlientaTextField.getText();
        String houseNr = domKlientaTextField.getText();

        Adress adress = new Adress(0,city,street,houseNr,flatNr,postalCode);

        Client newClient = new Client(0, name, surname, phoneNumber, email, idNumber, adress);

        DataSource.addClient(newClient);

        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
   }

}
