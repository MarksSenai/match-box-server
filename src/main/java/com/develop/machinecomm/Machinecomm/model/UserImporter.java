package com.develop.machinecomm.Machinecomm.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userImporter")
public class UserImporter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String usersFilePath;

    @Column(nullable = true)
    private String usersCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsersFilePath() {
        return usersFilePath;
    }

    public void setUsersFilePath(String usersFilePath) {
        this.usersFilePath = usersFilePath;
    }

    public String getUsersCode() {
        return usersCode;
    }

    public void setUsersCode(String usersCode) {
        this.usersCode = usersCode;
    }
}
