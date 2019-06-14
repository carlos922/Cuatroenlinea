/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.controlador;

import com.juegocuatro.modelo.Administrador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author carloaiza
 */
@Named(value = "loginControllerAdministrador")
@SessionScoped
public class LoginControllerAdministrador implements Serializable {
    private String correoAdministrador;
    private String password;
    private Administrador administradorLogueado;
    @EJB
    private AdministradorFacade conAdministrador;
    
    
   public String getCorreoAdministrador() {
        return correoAdministrador;
    }

    public void setCorreoAdministrador(String correoAdministrador) {
        this.correoAdministrador = correoAdministrador;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Administrador getAdministradorLogueado() {
        return administradorLogueado;
    }

    public void setAdministradorLogueado(Administrador administradorLogueado) {
        this.administradorLogueado = administradorLogueado;
    }
    
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginControllerAdministrador() {
    }
    
    public String autenticarAdministrador()
    {
        
  
        administradorLogueado = conAdministrador.encontrarAdministradorxCorreo(correoAdministrador);
        if(administradorLogueado!=null)
        {
            if(administradorLogueado.getPassword().equals(password))
            {                
                return "ingresar";
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Password erróneo", "Password Erróneo"));
            }    
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "El Administrador no existe", "El Administrador no existe"));
        }    
        return null;
            
    }     
    
    public String cerrarSesion()
    {
        administradorLogueado=null;
        correoAdministrador="";
        password="";
        return "cerrar";
    }
}
