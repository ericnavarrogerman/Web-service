package com.pqr.demopqr.dto.security;

import com.pqr.demopqr.dto.UsuarioDTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    String username;
    String token;
    String password;
    UsuarioDTO usuarioDTO;

    public UserDTO() {
    }

    public UserDTO(String username, String token, String password, UsuarioDTO usuarioDTO) {
        this.username = username;
        this.token = token;
        this.password = password;
        this.usuarioDTO = usuarioDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
}
