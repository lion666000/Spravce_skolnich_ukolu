package com.example.ukoly;

public class Ukol {
    private String name;
    private String subject;
    private String priority;
    private boolean finished;

    public Ukol(String name, String subject, String priority,  boolean finished) {
        this.name = name;
        this.subject = subject;
        this.priority = priority;
        this.finished = finished;
    }


    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isFinished() {
        return finished;
    }

    public String toString() {
        return name;
    }

    // "Název: " + name + " , Předmět: " + subject + " , Priorita: " + priority + " , Dokončeno: " + finished;


}
