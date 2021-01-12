package com.pqr.demopqr.dto;

import com.pqr.demopqr.model.Pqr;
import com.pqr.demopqr.model.enums.EstadoPQR;
import com.pqr.demopqr.model.enums.TipoPQR;
import com.pqr.demopqr.util.Utils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PqrDTO implements Serializable {
    private Long id;
    private TipoPQR tipo;
    private String asunto;
    private UsuarioDTO usuario;
    private UsuarioDTO autor;
    private EstadoPQR estado;
    private Date fechaCreacion;
    private Date fechaLimite;
    private String strFechaCreacion;
    private String strFechaLimite;
    private String vigencia;

    public PqrDTO() {
    }

    public PqrDTO(Long id, TipoPQR tipo, String asunto, UsuarioDTO usuario, UsuarioDTO autor, EstadoPQR estado, Date fechaCreacion, Date fechaLimite, String vigencia) {
        this.id = id;
        this.tipo = tipo;
        this.asunto = asunto;
        this.usuario = usuario;
        this.autor = autor;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.vigencia = vigencia;
    }

    public PqrDTO(@NotNull Pqr pqr, boolean withUsuario){
        this.id = pqr.getId();
        this.tipo = pqr.getTipo();
        this.asunto = pqr.getAsunto();
        this.estado = pqr.getEstado();
        this.fechaCreacion = pqr.getFechaCreacion();
        this.fechaLimite = pqr.getFechaLimite();

        if(!this.estado.equals(EstadoPQR.CERRADO) && this.fechaLimite.before(new Date())){
            this.vigencia = "Vencida";
        }else{
            this.vigencia = "Vigente";
        }

        if(withUsuario && pqr.getUsuario() != null){
            this.usuario = new UsuarioDTO(pqr.getUsuario(), false);
        }

        if(pqr.getAutor() != null){
            this.autor = new UsuarioDTO(pqr.getAutor(), false);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPQR getTipo() {
        return tipo;
    }

    public void setTipo(TipoPQR tipo) {
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public UsuarioDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioDTO autor) {
        this.autor = autor;
    }

    public EstadoPQR getEstado() {
        return estado;
    }

    public void setEstado(EstadoPQR estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getStrFechaCreacion() {
        return strFechaCreacion;
    }

    public void setStrFechaCreacion(String strFechaCreacion) {
        this.strFechaCreacion = strFechaCreacion;
    }

    public String getStrFechaLimite() {
        return strFechaLimite;
    }

    public void setStrFechaLimite(String strFechaLimite) {
        this.strFechaLimite = strFechaLimite;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }
}
