package com.develop.machinecomm.Machinecomm.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "machine")
@EntityListeners(AuditingEntityListener.class)

public class Machine implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String macid;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String machineIP;

    @Column(nullable = true)
    private String machinePort;

    public Machine() {}

    public Machine(String macid, String description, String machineIP, String machinePort) {
        this.macid = macid;
        this.description = description;
        this.machineIP = machineIP;
        this.machinePort = machinePort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacid() {
        return macid;
    }

    public void setMacid(String macid) {
        this.macid = macid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMachineIP() {
        return machineIP;
    }

    public void setMachineIP(String machineIP) {
        this.machineIP = machineIP;
    }

    public String getMachinePort() {
        return machinePort;
    }

    public void setMachinePort(String machinePort) {
        this.machinePort = machinePort;
    }
}
