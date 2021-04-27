package com.develop.machinecomm.Machinecomm.model.enums;


public enum Perfil {

    USER(1, "ROLE_USER"),
    ADMIN(2, "ROLE_ADMIN");


    private int cod;
    private String description;

    private Perfil(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription () {
        return description;
    }

    public static Perfil toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Perfil x : Perfil.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
