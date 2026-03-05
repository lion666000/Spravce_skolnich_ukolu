package com.example.ukoly;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class HlavniController {
    ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    private TextField nazevTextField;

    @FXML
    private ListView ukolyListView;

    @FXML
    private Label detailLabel;

    @FXML
    private RadioButton ajRadio;

    @FXML
    private RadioButton cjRadio;

    @FXML
    private RadioButton ckRadio;


    @FXML
    protected void handlePridatUkol() {
        observableList.add(nazevTextField.getText());

        ukolyListView.setItems(observableList);
    }
}
