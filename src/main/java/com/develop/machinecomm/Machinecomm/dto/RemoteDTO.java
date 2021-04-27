package com.develop.machinecomm.Machinecomm.dto;

public class RemoteDTO {

    private String macId;
    private String factory;
    private String companyLogo;
    private String autoLogOutTime;
    private String machineIP;
    private String machinePort;

    RemoteDTO() {}

    public RemoteDTO(String macId, String factory, String companyLogo, String autoLogOutTime, String machineIP, String machinePort) {
        this.macId = macId;
        this.factory = factory;
        this.companyLogo = companyLogo;
        this.autoLogOutTime = autoLogOutTime;
        this.machineIP = machineIP;
        this.machinePort = machinePort;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getAutoLogOutTime() {
        return autoLogOutTime;
    }

    public void setAutoLogOutTime(String autoLogOutTime) {
        this.autoLogOutTime = autoLogOutTime;
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
