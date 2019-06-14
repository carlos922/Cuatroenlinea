/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.modelo;

import grafociudades.excepciones.GrafoExcepcion;
import java.io.Serializable;

/**
 *
 * @author carloaiza
 */
public class GrafoDirigido extends GrafoAbstract implements Serializable{

    @Override
    public void verificarArista(int origen, int destino) throws GrafoExcepcion {
        for(Arista ar: this.getAristas())
        {
            if(ar.getOrigen()==origen && ar.getDestino()==destino)
            {
                throw new GrafoExcepcion("Ya existe comunicación entre los dos vertices");
            }    
            
            
        }
    }
    
}
