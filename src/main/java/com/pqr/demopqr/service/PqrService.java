package com.pqr.demopqr.service;

import com.pqr.demopqr.dao.IPqrRepository;
import com.pqr.demopqr.dao.IUsuarioRepository;
import com.pqr.demopqr.dto.PqrDTO;
import com.pqr.demopqr.model.Pqr;
import com.pqr.demopqr.model.enums.EstadoPQR;
import com.pqr.demopqr.model.enums.TipoPQR;
import com.pqr.demopqr.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PqrService {

    private final IPqrRepository pqrRepository;
    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public PqrService(IPqrRepository pqrRepository, IUsuarioRepository usuarioRepository){
        this.pqrRepository = pqrRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PqrDTO getPqrById(@NotNull Long id) throws Exception {
        Pqr pqr = pqrRepository.findById(id).orElse(null);
        if(pqr == null){
            throw new Exception("PQR no existe.");
        }

        return new PqrDTO(pqr, true);
    }

    public List<PqrDTO> getAll(){
        return pqrRepository.findAllOrderByFechaCreacionDesc().stream().map(p -> new PqrDTO(p, true))
                .collect(Collectors.toList());
    }

    private Date getFechaLimite(@NotNull Date fechaCreacion, @NotNull TipoPQR tipo){
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaCreacion);
        int dias;
        if(tipo.equals(TipoPQR.PETICION)){
            dias = 7;
        }else if(tipo.equals(TipoPQR.QUEJA)){
            dias = 3;
        }else{
            dias = 2;
        }

        cal.add(Calendar.DAY_OF_YEAR, dias);

        return cal.getTime();
    }

    public PqrDTO createPqr(@NotNull PqrDTO pqrDTO){
        Pqr pqr = new Pqr();

        pqr.setTipo(pqrDTO.getTipo());
        pqr.setAsunto(pqrDTO.getAsunto());
        pqr.setEstado(pqrDTO.getEstado());

        if(pqrDTO.getUsuario() != null && !Utils.isEmpty(pqrDTO.getUsuario().getUsername())){
            usuarioRepository.findByUsername(pqrDTO.getUsuario().getUsername())
                    .ifPresent(pqr::setUsuario);
        }

        if(pqrDTO.getAutor() != null && !Utils.isEmpty(pqrDTO.getAutor().getUsername())){
            usuarioRepository.findByUsername(pqrDTO.getAutor().getUsername())
                    .ifPresent(pqr::setAutor);
        }

        pqr.setFechaCreacion(Utils.convertStringToDate(pqrDTO.getStrFechaCreacion(), Utils.FECHA_GUION));

        if(pqr.getFechaCreacion() == null){
            pqr.setFechaCreacion(new Date());
        }

        pqr.setFechaLimite(getFechaLimite(pqr.getFechaCreacion(), pqr.getTipo()));

        return new PqrDTO(pqrRepository.saveAndFlush(pqr), true);
    }

    public PqrDTO updatePqr(@NotNull PqrDTO pqrDTO) throws Exception {
        Pqr pqr = null;
        if(!Utils.isEmpty(pqrDTO.getId())){
            pqr = pqrRepository.findById(pqrDTO.getId()).orElse(null);
        }

        if(pqr == null){
            throw new Exception("PQR no existe.");
        }

        if((pqr.getEstado().equals(EstadoPQR.NUEVO) && pqrDTO.getEstado().equals(EstadoPQR.EN_EJECUCION)) ||
            pqr.getEstado().equals(EstadoPQR.EN_EJECUCION) && pqrDTO.getEstado().equals(EstadoPQR.CERRADO)){
            pqr.setEstado(pqrDTO.getEstado());
        }else{
            throw new Exception("Actualizacion de estado no es valida.");
        }

        return new  PqrDTO(pqrRepository.saveAndFlush(pqr), true);
    }

    public boolean deletePqr(@NotNull Long id) throws Exception {
        Pqr pqr = pqrRepository.findById(id).orElse(null);
        if(pqr == null){
            throw new Exception("PQR no existe.");
        }

        pqrRepository.delete(pqr);

        return true;
    }
}
