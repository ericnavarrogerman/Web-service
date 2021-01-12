package com.pqr.demopqr.model.enums;

public enum EstadoPQR {
    NUEVO("Nuevo"), EN_EJECUCION("En Ejecucion"), CERRADO("Cerrado");

    String value;

    public String getValue() {
        return value;
    }

    EstadoPQR(String value) {
        this.value = value;
    }
}
