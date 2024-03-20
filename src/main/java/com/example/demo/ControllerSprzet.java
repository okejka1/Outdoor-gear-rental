package com.example.demo;

import com.example.demo.DataSource;
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

public class ControllerSprzet {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Sprzet Menu
    @FXML
    private Button dodawanieKategoriiButton;
    @FXML
    private Button dodawanieSprzetuButton;
    @FXML
    private Label edycjaSprzetuLabel;
    @FXML
    private Button edycjaSprzetuMenuButton;
    @FXML
    private Button wyszukiwanieKategoriiButton;
    @FXML
    private Button wyszukiwanieSprzetuButton;

    public void wyszukajSprzet(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Sprzet/wyszukiwanieSprzetu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void wyszukajKategorie(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Sprzet/wyszukiwanieKategorii.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void dodajSprzet(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Sprzet/dodawanieSprzetu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
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
