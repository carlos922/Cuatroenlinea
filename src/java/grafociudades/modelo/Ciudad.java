/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.modelo;

import java.io.Serializable;

/**
 *
 * @author carloaiza
 */
public class Ciudad implements Serializable{
    private String nombre;
    private boolean estado;
    
    private int posx;
    private int posy;

    public Ciudad() {
    }

    
    
    public Ciudad(String nombre,boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
        
    }

    public Ciudad(String nombre,boolean estado, int posx, int posy) {
        this.nombre = nombre;
        this.estado = estado;
       
        this.posx = posx;
        this.posy = posy;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }



    @Override
    public String toString() {
        return this.nombre;
    }
    
   

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
    
    
    
    
}
