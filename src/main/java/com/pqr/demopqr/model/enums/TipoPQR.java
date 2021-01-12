package com.pqr.demopqr.model.enums;

public enum TipoPQR {
    PETICION("Peticion"), RECLAMO("Reclamo"), QUEJA("Queja");

    String value;

    public String getValue() {
        return value;
    }

    TipoPQR(String value) {
        this.value = value;
    }
}
