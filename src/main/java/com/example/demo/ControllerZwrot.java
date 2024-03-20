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

public class ControllerZwrot {

    private DataSource dataSource;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String cssMain = this.getClass().getResource("/com/example/demo/MainStyle.css").toExternalForm();


    //Zwrot Menu
    @FXML
    private Button dodawanieZwrotowButton;
    @FXML
    private Label edycjaZwrotowLabel;
    @FXML
    private Button edycjaZwrotowMenuButton;
    @FXML
    private Button wyszukiwanieZwrotowButton;

    public void wyszukajZwroty(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Zwrot/wyszukiwanieZwrotow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(cssMain);
        stage.setScene(scene);
        stage.show();
    }

    public void dodajZwroty(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Zwrot/dodawanieZwrotow.fxml"));
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
