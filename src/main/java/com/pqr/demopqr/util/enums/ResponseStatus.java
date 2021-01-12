package com.pqr.demopqr.util.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseStatus implements Serializable {

    OK(1, "OK"),

    NO_DATA(2, "NO_DATA"),

    ERROR(3, "ERROR");

    private final Integer code;

    private final String name;

    ResponseStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}