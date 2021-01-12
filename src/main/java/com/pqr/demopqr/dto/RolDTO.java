package com.pqr.demopqr.dto;

import com.pqr.demopqr.model.Rol;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RolDTO implements Serializable {
    private Long id;
    private String nombre;

    public RolDTO() {
    }

    public RolDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public RolDTO(@NotNull Rol rol){
        this.id = rol.getId();
        this.nombre = rol.getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}