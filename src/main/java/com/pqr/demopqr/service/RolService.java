package com.pqr.demopqr.service;

import com.pqr.demopqr.dao.IRolRepository;
import com.pqr.demopqr.dto.RolDTO;
import com.pqr.demopqr.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RolService {

    private final IRolRepository rolRepository;

    @Autowired
    public RolService(IRolRepository rolRepository){
        this.rolRepository = rolRepository;
    }

    public RolDTO getRolById(@NotNull Long id) throws Exception {
        Rol rol = rolRepository.findById(id).orElse(null);
        if(rol == null){
            throw new Exception("El usuario rol existe.");
        }

        return new RolDTO(rol);
    }

    public RolDTO getRolByNombre(@NotNull String nombre) throws Exception {
        Rol rol = rolRepository.findByNombre(nombre).orElse(null);
        if(rol == null){
            throw new Exception("El usuario rol existe.");
        }

        return new RolDTO(rol);
    }

}
