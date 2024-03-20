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

public class ControllerEgzemplarz {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();

    //Egzemplarze Menu
    @FXML
    private Button dodawanieEgzemplarzyButton;
    @FXML
    private Label edycjaEgzemplarzyLabel;
    @FXML
    private Button edycjaEgzemplarzyMenuButton;
    @FXML
    private Button wyszukiwanieEgzemplarzyButton;

    public void wyszukajEgzemplarz(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Egzemplarz/wyszukiwanieEgzemplarzy.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void dodajEgzemplarz(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Egzemplarz/dodawanieEgzemplarzy.fxml"));
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
