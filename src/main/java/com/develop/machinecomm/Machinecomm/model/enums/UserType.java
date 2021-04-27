package com.develop.machinecomm.Machinecomm.model.enums;

public enum UserType {

    TRAINEE(1, "Operador trainee"),
    COMMOM(2, "Operador habilitado"),
    EXPERT(3, "Operador especialista");

    private int cod;
    private String description;

    private UserType(int cod, String description){
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static UserType toEnum(Integer cod){
        if (cod == null) {
            return null;
        }

        for (UserType x : UserType.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
