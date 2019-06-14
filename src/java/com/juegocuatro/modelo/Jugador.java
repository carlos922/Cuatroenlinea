/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "jugador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findById", query = "SELECT j FROM Jugador j WHERE j.id = :id"),
    @NamedQuery(name = "Jugador.findByNombre", query = "SELECT j FROM Jugador j WHERE j.nombre = :nombre"),
    @NamedQuery(name = "Jugador.findByColor", query = "SELECT j FROM Jugador j WHERE j.color = :color"),
    @NamedQuery(name = "Jugador.findByTurnoPartida", query = "SELECT j FROM Jugador j WHERE j.turnoPartida = :turnoPartida")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "color")
    private String color;
    @Column(name = "turnoPartida")
    private Boolean turnoPartida;
    @OneToMany(mappedBy = "ganador")
    private List<Partida> partidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jugador1")
    private List<Partida> partidaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jugador2")
    private List<Partida> partidaList2;

    public Jugador() {
    }

    public Jugador(Integer id) {
        this.id = id;
    }

    public Jugador(Integer id, String nombre, String color) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getTurnoPartida() {
        return turnoPartida;
    }

    public void setTurnoPartida(Boolean turnoPartida) {
        this.turnoPartida = turnoPartida;
    }

    @XmlTransient
    public List<Partida> getPartidaList() {
        return partidaList;
    }

    public void setPartidaList(List<Partida> partidaList) {
        this.partidaList = partidaList;
    }

    @XmlTransient
    public List<Partida> getPartidaList1() {
        return partidaList1;
    }

    public void setPartidaList1(List<Partida> partidaList1) {
        this.partidaList1 = partidaList1;
    }

    @XmlTransient
    public List<Partida> getPartidaList2() {
        return partidaList2;
    }

    public void setPartidaList2(List<Partida> partidaList2) {
        this.partidaList2 = partidaList2;
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
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.juegocuatro.modelo.Jugador[ id=" + id + " ]";
    }
    
}
