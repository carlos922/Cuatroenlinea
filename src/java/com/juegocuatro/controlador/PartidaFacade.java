/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juegocuatro.controlador;

import com.juegocuatro.modelo.Partida;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario
 */
@Stateless
public class PartidaFacade extends AbstractFacade<Partida> {

    @PersistenceContext(unitName = "juegoCuatroPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartidaFacade() {
        super(Partida.class);
    }
    
}
