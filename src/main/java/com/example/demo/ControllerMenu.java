package com.example.demo;

import com.example.demo.Containers.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class ControllerMenu {

    private DataSource dataSource;

    public static Employee loggedEmployee;
    private Stage stage;
    private Scene scene;
    private Parent root;


    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Logowanie
    @FXML
    private Label logowanieLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label hasloLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button anulujLogowanie;
    @FXML
    private Button zaloguj;

    //Menu Glowne
    @FXML
    private Button administracjaButton;
    @FXML
    private Label menuGlowneLabel;
    @FXML
    private Button wrocDoLogowania;
    @FXML
    private Button wyjdz;
    @FXML
    private Button wypozyczeniaButton;
    @FXML
    private Button zwrotyButton;


    public void turnOff(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void doMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }


    public void zaloguj(ActionEvent event) throws IOException {
        String enteredUsername = loginTextField.getText();
        String enteredPassword = passwordField.getText();
        if(DataSource.authenticateUser(enteredUsername,enteredPassword)) {
            root = FXMLLoader.load(getClass().getResource("/com/example/demo/menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(cssMain);
            stage.setScene(scene);
            stage.show();
            loggedEmployee = DataSource.getEmployee();
            System.out.println("TEST1");
            System.out.println(loggedEmployee);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occured");
            alert.setContentText("Wpisz prawidlowe dane");
            alert.show(); // Show the alert

        }
    }


//            System.out.println("WIELKE ELO");

    public void doLogowania(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/com/example/demo/logowanie.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doEdycjiKatSprzetu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Sprzet/sprzetMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doEdycjiEgzSprzetu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Egzemplarz/egzemplarzeMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doAdministracji(ActionEvent event) throws IOException{
        if(loggedEmployee.checkAuth()){
            root = FXMLLoader.load(getClass().getResource("Administracja/administracjaMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(cssMain);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occured");
            alert.setContentText("Nie posiadasz odpowiednich uprawnień");
            alert.show(); // Show the alert
        }
    }

    public void doEdycjiKlientow(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Klient/klientMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doEdycjiWypozyczen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Wypozyczenie/wypozyczenieMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ControllerWypozyczenie controllerWypozyczenie = loader.getController();
        controllerWypozyczenie.setLoggedEmployee(loggedEmployee);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doEdycjiZwrotow(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Zwrot/zwrotMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
       scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void anulujDaneLogowania(ActionEvent event) throws IOException{
        loginTextField.clear();
        passwordField.clear();
    }

    public void setLoggedEmployee() {
        loggedEmployee = DataSource.getEmployee();
    }
}