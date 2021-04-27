package com.develop.machinecomm.Machinecomm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@EntityListeners(AuditingEntityListener.class)
public class Logs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users", nullable = false)
    @JsonIgnore
    private Users users;

    @ManyToOne
    @JoinColumn(name = "machine", nullable = false)
    @JsonIgnore
    private Machine machine;

    @Column(nullable = false)
    private LocalDateTime login;


    @Column(nullable = true)
    private LocalDateTime logout;


    @Column(nullable = true)
    private LocalDateTime expirationlack;


    @Column(nullable = false)
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = user;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public LocalDateTime getLogin() {
        return login;
    }

    public void setLogin(LocalDateTime login) {
        this.login = login;
    }

    public LocalDateTime getLogout() {
        return logout;
    }

    public void setLogout(LocalDateTime logout) {
        this.logout = logout;
    }

    public LocalDateTime getExpirationlack() {
        return expirationlack;
    }

    public void setExpirationlack(LocalDateTime expirationlack) {
        this.expirationlack = expirationlack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


