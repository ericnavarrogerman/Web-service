package com.pqr.demopqr.dto;

import com.pqr.demopqr.model.Usuario;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private RolDTO rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String apellido, String username, String password, RolDTO rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public UsuarioDTO(@NotNull Usuario usuario, boolean withRol){
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();

        if(withRol && usuario.getRol() != null){
            this.rol = new RolDTO(usuario.getRol());
        }
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }
}
