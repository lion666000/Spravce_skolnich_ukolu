package com.example.ukoly;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
    private Button upravitButton;

    @FXML
    private Button smazatButton;

    @FXML
    private  Button infoButton;



    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tutorial Dialog");
        alert.setHeaderText("Nápověda k použití aplikace"); // Set to null for no header
        alert.setContentText("Nejdříve zadáme název ůkolu a poté jaký je to předmět a jakou má úkol prioritu a jestli je splněn. \n" +
                " Poté můžeme vybrat položku ze seznamu po čemž můžeme zobrazit podrobné info, nebo ho smazat, a nebo ho upravit (čímž ho přepíšeme)");

        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        ukolyListView.setItems(observableList);
        upravitButton.setDisable(true);
        smazatButton.setDisable(true);
        infoButton.setDisable(true);
    }


    @FXML
    protected void handlePridatUkol() {
        if (nazevTextField.getText().length() > 0 && subject.getSelectedToggle() != null && priority.getSelectedToggle() != null) {
            boolean temp = true;
            for (Ukol ukoly : observableList) {
                if (ukoly.getName().equals(nazevTextField.getText())){
                    temp = false;
                }
            }
            if (temp) {
                RadioButton selectedSubject = (RadioButton) subject.getSelectedToggle();

                RadioButton selectedPriority = (RadioButton) priority.getSelectedToggle();

                observableList.add(new Ukol(nazevTextField.getText(), selectedSubject, selectedPriority, finishedCheckBox.isSelected() ));

                ukolyListView.setItems(observableList);
                ukolyListView.refresh();
            }


        }
        else {
            detailLabel.setText("Formulář není zcela vyplněný!");
        }


    }

    @FXML
    protected void handleShowDetail() {
        if (ukolyListView.getSelectionModel().getSelectedItem() != null) {
            Ukol selectedUkol = (Ukol) ukolyListView.getSelectionModel().getSelectedItem();

            detailLabel.setText("Název: " + selectedUkol.getName() + " , Předmět: " + selectedUkol.getSubjectString() + " , Priorita: " + selectedUkol.getPriorityString() + " , Dokončeno: " + selectedUkol.isFinished());
        }
        else {
            detailLabel.setText("Nebyl vybrán úkol");
        }
    }

    @FXML
    protected void handleEnd() {
        Platform.exit();
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

    @FXML
    protected void handleSelected() {
        if (ukolyListView.getSelectionModel().getSelectedItem() != null) {
            Ukol selectedUkol = (Ukol) ukolyListView.getSelectionModel().getSelectedItem();

            nazevTextField.setText(selectedUkol.getName());

            subject.selectToggle(selectedUkol.getSubject());

            priority.selectToggle(selectedUkol.getPriority());

            finishedCheckBox.setSelected(selectedUkol.isFinished());

            handleShowDetail();

            upravitButton.setDisable(false);
            smazatButton.setDisable(false);
            infoButton.setDisable(false);
        }
        else {
            detailLabel.setText("Nebyl vybrán úkol");
        }
    }

    @FXML
    protected void handleChange() {
        if (ukolyListView.getSelectionModel().getSelectedItem() != null) {
            Ukol selectedUkol = (Ukol) ukolyListView.getSelectionModel().getSelectedItem();

            RadioButton selectedSubject = (RadioButton) subject.getSelectedToggle();

            RadioButton selectedPriority = (RadioButton) priority.getSelectedToggle();

            selectedUkol.setName(nazevTextField.getText());
            selectedUkol.setSubject(selectedSubject);
            selectedUkol.setPriority(selectedPriority);
            selectedUkol.setFinished(finishedCheckBox.isSelected());




            detailLabel.setText("Detail");
        }
        else {
            detailLabel.setText("Nebyl vybrán úkol");
        }
    }

    @FXML
    protected void handleDelete() {
        if (ukolyListView.getSelectionModel().getSelectedItem() != null) {

            observableList.remove(ukolyListView.getSelectionModel().getSelectedItem());

            detailLabel.setText("Detail");

            if (observableList.isEmpty()) {
                upravitButton.setDisable(true);
                smazatButton.setDisable(true);
                infoButton.setDisable(true);
            }

        }
        else {
            detailLabel.setText("Nebyl vybrán úkol");
        }
    }

    @FXML
    protected void handleSave() {
        if (!observableList.isEmpty()) {
            ArrayList<String> temp = new ArrayList<>();
            Path soubor = Path.of("data", "log.txt");

            try {

                // Pokud složka neexistuje, vytvoříme ji
                if (!Files.exists(soubor.getParent())) {
                    Files.createDirectories(soubor.getParent());
                }


                for  (Ukol ukol : observableList) {
                    temp.add(ukol.toString2());
                }


                // Zápis do souboru (vytvoří nebo přepíše soubor)
                Files.write(soubor, temp);

                System.out.println("Zápis proběhl úspěšně.");
                System.out.println("Absolutní cesta: " + soubor.toAbsolutePath());

            } catch (IOException e) {
                System.out.println("Chyba při zápisu do souboru:");
                e.printStackTrace();
            }
        }
        else {
            detailLabel.setText("Není co uložit");
            System.err.println("Není co uložit");
        }
    }

    @FXML
    protected void handleLoad() {
        try {
            ArrayList<String> temp = new ArrayList<>();
            Path soubor = Path.of("data", "log.txt");


            String name;
            ToggleButton selectedSubject = null;
            String selectedSubjectString;
            ToggleButton selectedPriority = null;
            String selectedPriorityString;
            boolean finishedCheckBox;

            if (soubor.toFile().exists()) {
                observableList.removeAll(observableList);

                try {
                    Files.readAllLines(soubor).forEach(temp::add);
                } catch (IOException e) {
                    System.out.println("Chyba při zápisu do souboru:");
                    e.printStackTrace();
                }

                for  (String s : temp) {
                    System.out.println(s);
                    StringBuilder stringBuilder = new StringBuilder(s);

                    name =  stringBuilder.substring(0,stringBuilder.indexOf(";"));

                    System.out.println("Name: " + name);

                    stringBuilder.delete(0, stringBuilder.indexOf(";")+1);

                    selectedSubjectString = stringBuilder.substring(0,stringBuilder.indexOf(";"));

                    System.out.println("Subject: " + selectedSubjectString);

                    stringBuilder.delete(0, stringBuilder.indexOf(";")+1);

                    selectedPriorityString = stringBuilder.substring(0,stringBuilder.indexOf(";"));

                    System.out.println("Priority: " + selectedPriorityString);

                    stringBuilder.delete(0, stringBuilder.indexOf(";")+1);

                    if (stringBuilder.toString().equals("true")) {
                        finishedCheckBox = true;
                    }
                    else {
                        finishedCheckBox = false;
                    }

                    System.out.println("Finished: " + finishedCheckBox);

                    stringBuilder.delete(0, stringBuilder.length());


                    for (Toggle toggle : subject.getToggles()) {
                        RadioButton rb = (RadioButton) toggle;
                        if (rb.getText().equals(selectedSubjectString)) {
                            selectedSubject = rb;
                            System.out.println("Selected set: " + rb.getText());
                            break; // Exit once we find the match
                        }
                    }

                    for (Toggle toggle : priority.getToggles()) {
                        RadioButton rb = (RadioButton) toggle;
                        if (rb.getText().equals(selectedPriorityString)) {
                            selectedPriority = rb;
                            System.out.println("Priority set: " + rb.getText());
                            break; // Exit once we find the match
                        }
                    }

                    observableList.add(new Ukol(name, selectedSubject, selectedPriority, finishedCheckBox));
                }
            }
        }
        catch (Exception e) {
            System.err.println("Něco je špatně s formátem dat v souboru. |" + e.getMessage());
        }
    }
}
