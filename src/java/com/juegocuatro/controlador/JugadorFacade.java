/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.controlador;

import com.juegocuatro.modelo.Jugador;
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
public class JugadorFacade extends AbstractFacade<Jugador> {

    @PersistenceContext(unitName = "juegoCuatroPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JugadorFacade() {
        super(Jugador.class);
    }
    
     public Jugador encontrarJugadorxNombre(String nombre)
    {
        Query q= em.createNamedQuery("Administrador.findByCorreo", Jugador.class)
                .setParameter("nombre", nombre);
        
       
        List<Jugador> list= q.getResultList();
        
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
