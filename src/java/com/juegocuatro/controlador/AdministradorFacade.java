/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.controlador;

import com.juegocuatro.modelo.Administrador;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class AdministradorFacade extends AbstractFacade<Administrador> {

    @PersistenceContext(unitName = "juegoCuatroPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministradorFacade() {
        super(Administrador.class);
    }
    
    
    public Administrador encontrarAdministradorxCorreo(String correo)
    {
        Query q= em.createNamedQuery("Administrador.findByCorreo", Administrador.class)
                .setParameter("correo", correo);
        
       
        List<Administrador> list= q.getResultList();
        
        if(list.isEmpty())
        {
            return null;
        }
        else
        {
            return list.get(0);
        }    
    }   
    
}
