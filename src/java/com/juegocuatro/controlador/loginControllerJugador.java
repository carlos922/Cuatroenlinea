/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.controlador;

import com.juegocuatro.modelo.Jugador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Usuario
 */
@Named(value = "loginControllerJugador")
@SessionScoped
public class loginControllerJugador implements Serializable {
    private String nombreJugador;
    private Jugador jugadorLogueado;
    @EJB
    private JugadorFacade conJugador;
    
     public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    
    public Jugador getAdministradorLogueado() {
        return jugadorLogueado;
    }

    public void setJugadorLogueado(Jugador jugadorLogueado) {
        this.jugadorLogueado = jugadorLogueado;
    }
    
      public loginControllerJugador() {
    }
    
    public String autenticarJugador()
    {
        
  
        jugadorLogueado = conJugador.encontrarJugadorxNombre(nombreJugador);
        if(jugadorLogueado!=null)
        {
            if(jugadorLogueado.getNombre().equals(nombreJugador))
            {                
                return "jugar";
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "nombre erróneo", "nombre Erróneo"));
            }    
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "El Jugador no existe", "El Jugador no existe"));
        }    
        return null;
            
    }     
    
    public String cerrarSesion()
    {
        jugadorLogueado=null;
        nombreJugador="";
        return "cerrar";
    }
    
}
