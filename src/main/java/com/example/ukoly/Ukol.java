package com.example.ukoly;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class Ukol {
    private String name;
    private ToggleButton subject;
    private String subjectString;
    private ToggleButton priority;
    private String priorityString;
    private boolean finished;

    public Ukol(String name, ToggleButton subject, ToggleButton priority,  boolean finished) {
        this.name = name;
        this.subject = subject;
        this.priority = priority;
        this.finished = finished;

        subjectString = subject.getText();
        priorityString = priority.getText();
    }


    public String getName() {
        return name;
    }

    public String getSubjectString() {
        return subjectString;
    }

    public ToggleButton getSubject() {return subject;}

    public String getPriorityString() {
        return priorityString;
    }

    public ToggleButton getPriority() {return priority;}

    public boolean isFinished() {
        return finished;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(ToggleButton subject) {
        this.subject = subject;
    }

    public void setPriority(ToggleButton priority) {
        this.priority = priority;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String toString() {
        return name;
    }

    public String toString2() {
        return name + ";" +  subjectString.toString() + ";" + priorityString.toString() + ";" + finished;
    }

    // "Název: " + name + " , Předmět: " + subject + " , Priorita: " + priority + " , Dokončeno: " + finished;


}
