package com.pqr.demopqr.controller;

import com.pqr.demopqr.service.RolService;
import com.pqr.demopqr.util.BackendResponse;
import com.pqr.demopqr.util.enums.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rol")
public class RolController {

    public static Logger logger = Logger.getLogger(RolController.class.getName());

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService){
        this.rolService = rolService;
    }

    @GetMapping("/getById")
    public BackendResponse getById(@RequestParam("id")Long id){
        try {
            return new BackendResponse(rolService.getRolById(id));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/getByNombre")
    public BackendResponse getByNombre(@RequestParam("nombre")String nombre){
        try {
            return new BackendResponse(rolService.getRolByNombre(nombre));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
