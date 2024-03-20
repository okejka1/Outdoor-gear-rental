package com.example.demo;

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

public class ControllerDodawaniePracownikow {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    @FXML
    private Label adresLabel;
    @FXML
    private TextField adresTextField;
    @FXML
    private Button doMenuButton;
    @FXML
    private Button dodajPracownikaButton;
    @FXML
    private Label dodawanieKlientowLabel;
    @FXML
    private TextField domTextField;
    @FXML
    private Label hasloLabel;
    @FXML
    private TextField hasloTextField;
    @FXML
    private Label imieLabel;
    @FXML
    private TextField imieTextField;
    @FXML
    private Label kodPocztowyLabel;
    @FXML
    private TextField kodPocztowyTextField;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private Label mailLabel;
    @FXML
    private TextField mailTextField;
    @FXML
    private Label miastoLabel;
    @FXML
    private TextField miastoTextField;
    @FXML
    private TextField mieszkanieTextField;
    @FXML
    private Label nazwiskoLabel;
    @FXML
    private TextField nazwiskoTextField;
    @FXML
    private Label nrDomuLabel;
    @FXML
    private Label nrDowoduLabel;
    @FXML
    private Label nrMieszkaniaLabel;
    @FXML
    private TextField numerDowoduTextField;
    @FXML
    private Label telLabel;
    @FXML
    private TextField telefonTextField;

    public void dodajPracownika(ActionEvent event) throws  IOException {
        String firstName = imieTextField.getText();
        String lastName = nazwiskoTextField.getText();
        String phoneNumber = telefonTextField.getText();
        String email = mailTextField.getText();
        String idNumber = numerDowoduTextField.getText();
        String login = imieTextField.getText();
        String password = hasloTextField.getText();
        String city = miastoTextField.getText();
        String street = adresTextField.getText();
        String houseNumber = domTextField.getText();
        String flatNumber = mieszkanieTextField.getText();
        String postalCode = kodPocztowyTextField.getText();

        Integer adressID = DataSource.manageAddress(new Adress(0,city,street,houseNumber,flatNumber,postalCode),"Insert");

        DataSource.manageEmployees(firstName,lastName,phoneNumber,email,idNumber,login,password,adressID,"Insert");

    }

    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }
}
