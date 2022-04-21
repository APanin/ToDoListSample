package com.apanin.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class PermissionEntity extends SuperIdUpdateEntity {

    private String route;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
