package com.pqr.demopqr.model;

import com.pqr.demopqr.model.enums.EstadoPQR;
import com.pqr.demopqr.model.enums.TipoPQR;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pqr")
public class Pqr {
    private Long id;
    private TipoPQR tipo;
    private String asunto;
    private Usuario usuario;
    private Usuario autor;
    private EstadoPQR estado;
    private Date fechaCreacion;
    private Date fechaLimite;

    public Pqr() {
    }

    public Pqr(Long id, TipoPQR tipo, String asunto, Usuario usuario, Usuario autor, EstadoPQR estado, Date fechaCreacion, Date fechaLimite) {
        this.id = id;
        this.tipo = tipo;
        this.asunto = asunto;
        this.usuario = usuario;
        this.autor = autor;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="tipo")
    @NotNull
    public TipoPQR getTipo() {
        return tipo;
    }

    public void setTipo(TipoPQR tipo) {
        this.tipo = tipo;
    }

    @Column(name="asunto")
    @NotNull
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull
    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    @Column(name="estado")
    @NotNull
    public EstadoPQR getEstado() {
        return estado;
    }

    public void setEstado(EstadoPQR estado) {
        this.estado = estado;
    }

    @Column(name="fecha_creacion")
    @NotNull
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name="fecha_limite")
    @NotNull
    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
