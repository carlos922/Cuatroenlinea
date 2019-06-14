/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "partida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findById", query = "SELECT p FROM Partida p WHERE p.id = :id"),
    @NamedQuery(name = "Partida.findByTiempoEspera", query = "SELECT p FROM Partida p WHERE p.tiempoEspera = :tiempoEspera"),
    @NamedQuery(name = "Partida.findByTiempoPartidaJugador1", query = "SELECT p FROM Partida p WHERE p.tiempoPartidaJugador1 = :tiempoPartidaJugador1"),
    @NamedQuery(name = "Partida.findByTiempoPartidaJugador2", query = "SELECT p FROM Partida p WHERE p.tiempoPartidaJugador2 = :tiempoPartidaJugador2"),
    @NamedQuery(name = "Partida.findByJugada", query = "SELECT p FROM Partida p WHERE p.jugada = :jugada"),
    @NamedQuery(name = "Partida.findByEmpate", query = "SELECT p FROM Partida p WHERE p.empate = :empate")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tiempo_espera")
    private Double tiempoEspera;
    @Column(name = "tiempo_partida_jugador1")
    private Double tiempoPartidaJugador1;
    @Column(name = "tiempo_partida_jugador2")
    private Double tiempoPartidaJugador2;
    @Size(max = 10)
    @Column(name = "jugada")
    private String jugada;
    @Column(name = "empate")
    private Boolean empate;
    @JoinColumn(name = "ganador", referencedColumnName = "id")
    @ManyToOne
    private Jugador ganador;
    @JoinColumn(name = "jugador1", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jugador jugador1;
    @JoinColumn(name = "jugador2", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jugador jugador2;

    public Partida() {
    }

    public Partida(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(Double tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public Double getTiempoPartidaJugador1() {
        return tiempoPartidaJugador1;
    }

    public void setTiempoPartidaJugador1(Double tiempoPartidaJugador1) {
        this.tiempoPartidaJugador1 = tiempoPartidaJugador1;
    }

    public Double getTiempoPartidaJugador2() {
        return tiempoPartidaJugador2;
    }

    public void setTiempoPartidaJugador2(Double tiempoPartidaJugador2) {
        this.tiempoPartidaJugador2 = tiempoPartidaJugador2;
    }

    public String getJugada() {
        return jugada;
    }

    public void setJugada(String jugada) {
        this.jugada = jugada;
    }

    public Boolean getEmpate() {
        return empate;
    }

    public void setEmpate(Boolean empate) {
        this.empate = empate;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.juegocuatro.modelo.Partida[ id=" + id + " ]";
    }
    
}
