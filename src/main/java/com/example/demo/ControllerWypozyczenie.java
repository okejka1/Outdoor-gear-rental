package com.example.demo;

import com.example.demo.Containers.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;


public class ControllerWypozyczenie{

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Employee loggedEmployee;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Wypozyczenia Menu
    @FXML
    private Button dodawanieWypozyczenButton;
    @FXML
    private Button edycjaWypozyczenMenuButton;
    @FXML
    private Label edycjaWypozyczenLabel;
    @FXML
    private Button wyszukiwanieWypozyczenButton;


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

    public void doWyszukiwaniaWypozyczen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Wypozyczenie/wyszukiwanieWypozyczen.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void doDodawaniaWypozyczen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Wypozyczenie/dodawanieWypozyczen.fxml"));
        root = loader.load();
        ControllerDodawanieWypozyczen controllerDodawanieWypozyczen = loader.getController();
        controllerDodawanieWypozyczen.setLoggedEmployee(loggedEmployee);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = ControllerMenu.loggedEmployee;
        System.out.println("Logged Employee in ControllerWypozyczenie: " + loggedEmployee);
    }
}
