package com.pqr.demopqr.controller;

import com.pqr.demopqr.controller.security.UserSecurityController;
import com.pqr.demopqr.dto.UsuarioDTO;
import com.pqr.demopqr.service.UsuarioService;
import com.pqr.demopqr.util.BackendResponse;
import com.pqr.demopqr.util.enums.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    public static Logger logger = Logger.getLogger(UsuarioController.class.getName());

    private final UsuarioService usuarioService;
    private final UserSecurityController userSecurityController;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UserSecurityController userSecurityController){
        this.usuarioService = usuarioService;
        this.userSecurityController = userSecurityController;
    }

    @GetMapping("/getAllUsers")
    public BackendResponse getAllUsers(){
        return new BackendResponse(usuarioService.getAllUsersByRoleOrderByIdasc());
    }

    @GetMapping("/getById")
    public BackendResponse getById(@RequestParam("id")Long id){
        try{
            return new BackendResponse(usuarioService.getUsuarioById(id));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/getByUsername")
    public BackendResponse getByUsername(@RequestParam("username")String username){
        try{
            return new BackendResponse(usuarioService.getUsuarioByUsername(username));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @PostMapping("/createUsuario")
    public BackendResponse createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        try{
            UsuarioDTO usuario = usuarioService.createUsuario(usuarioDTO);

            assert usuario != null;

            return userSecurityController.login(usuarioDTO.getUsername(), usuarioDTO.getPassword());
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @PutMapping("/updateUsuario")
    public BackendResponse updateUsuario(@RequestBody UsuarioDTO usuarioDTO){
        try{
            return new BackendResponse(usuarioService.updateUsuario(usuarioDTO));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @PostMapping("/login")
    public BackendResponse login(@RequestBody UsuarioDTO usuarioDTO){
        try{
            return new BackendResponse(usuarioService.loginUser(usuarioDTO));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
