package com.example.ukoly;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HlavniController {
    ObservableList<Ukol> observableList = FXCollections.observableArrayList();

    @FXML
    private TextField nazevTextField;

    @FXML
    private ListView ukolyListView;

    @FXML
    private Label detailLabel;

    @FXML
    private ToggleGroup subject;

    @FXML
    private ToggleGroup priority;

    @FXML
    private CheckBox finishedCheckBox;



    @FXML
    public void initialize() {
        ukolyListView.setItems(observableList);
    }

    @FXML
    protected void handlePridatUkol() {
        RadioButton selectedSubject = (RadioButton) subject.getSelectedToggle();

        RadioButton selectedPriority = (RadioButton) priority.getSelectedToggle();

        observableList.add(new Ukol(nazevTextField.getText(), selectedSubject.getText(), selectedPriority.getText(), finishedCheckBox.isSelected() ));


    }

    @FXML
    protected void handleShowDetail() {
        if (ukolyListView.getSelectionModel().getSelectedItem() != null) {
            Ukol selectedUkol = (Ukol) ukolyListView.getSelectionModel().getSelectedItem();

            detailLabel.setText("Název: " + selectedUkol.getName() + " , Předmět: " + selectedUkol.getSubject() + " , Priorita: " + selectedUkol.getPriority() + " , Dokončeno: " + selectedUkol.isFinished());
        }
        else {
            detailLabel.setText("Nebyl vybrán úkol");
        }
    }

    @FXML
    protected void handleEnd() {
        System.exit(0);
    }

    @FXML
    protected void handleShowAll() {
        ukolyListView.setItems(observableList);
    }

    @FXML
    protected void handleShowCompleted() {
        ObservableList<Ukol> tempList = FXCollections.observableArrayList();

        for (Ukol ukol : observableList) {
            if  (ukol.isFinished()) {
                tempList.add(ukol);
            }
        }

        ukolyListView.setItems(tempList);
    }

    @FXML
    protected void handleShowUnfinished() {
        ObservableList<Ukol> tempList = FXCollections.observableArrayList();

        for (Ukol ukol : observableList) {
            if  (!ukol.isFinished()) {
                tempList.add(ukol);
            }
        }

        ukolyListView.setItems(tempList);
    }
}
