package com.pqr.demopqr.controller;

import com.pqr.demopqr.dto.PqrDTO;
import com.pqr.demopqr.service.PqrReportService;
import com.pqr.demopqr.service.PqrService;
import com.pqr.demopqr.util.BackendResponse;
import com.pqr.demopqr.util.enums.ResponseStatus;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pqr")
public class PqrController {

    public static Logger logger = Logger.getLogger(PqrController.class.getName());

    private final PqrService pqrService;
    private final PqrReportService pqrReportService;

    @Autowired
    public PqrController(PqrService pqrService, PqrReportService pqrReportService){
        this.pqrService = pqrService;
        this.pqrReportService = pqrReportService;
    }

    @GetMapping("/getById")
    public BackendResponse getById(@RequestParam("id")Long id){
        try{
            return new BackendResponse(pqrService.getPqrById(id));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public BackendResponse getAll(){
        try{
            return new BackendResponse(pqrService.getAll());
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @PostMapping("/createPqr")
    public BackendResponse createPqr(@RequestBody PqrDTO pqrDTO){
        try{
            return new BackendResponse(pqrService.createPqr(pqrDTO));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @PutMapping("/updatePqr")
    public BackendResponse updateUsuario(@RequestBody PqrDTO pqrDTO){
        try{
            return new BackendResponse(pqrService.updatePqr(pqrDTO));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/deletePqr")
    public BackendResponse deletePqr(@RequestParam("id")Long id){
        try{
            return new BackendResponse(pqrService.deletePqr(id));
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage(), e);
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/report")
    public ResponseEntity<?> getReport(HttpServletResponse response) throws IOException {
        File file = pqrReportService.getReport();

        response.setHeader("Content-disposition", "attachment; filename=" + "PQR_Report.xlsx");
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
