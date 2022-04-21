package com.apanin.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class TaskEntity extends SuperIdUpdateEntity {
    private String title;
    private String description;
    private Boolean isDone;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
