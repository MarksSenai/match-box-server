package com.develop.machinecomm.Machinecomm.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "configuration")
@EntityListeners(AuditingEntityListener.class)

public class Configuration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String volume;

    @Column(nullable = false)
    private String expirationtime;

    @Column(nullable = true)
    private String rotationtime;

    @Column(nullable = true)
    private String companyname;

    @Column(nullable = true)
    private String companypicture;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getExpirationtime() {
        return expirationtime;
    }

    public void setExpirationtime(String expirationtime) {
        this.expirationtime = expirationtime;
    }

    public String getRotationtime() {return rotationtime;}

    public void setRotationtime(String rotationtime) {this.rotationtime = rotationtime;}

    public String getCompanyname() {return companyname;}

    public void setCompanyname(String companyname) {this.companyname = companyname;}

    public String getCompanypicture() {return companypicture;}

    public void setCompanypicture(String companypicture) {this.companypicture = companypicture;}
}