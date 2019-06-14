/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.modelo;

import grafociudades.excepciones.GrafoExcepcion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carloaiza
 */
public abstract class GrafoAbstract implements Serializable{
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public GrafoAbstract() {
        vertices= new ArrayList<>();
        aristas = new ArrayList<>();
    }
    
    

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(List<Arista> aristas) {
        this.aristas = aristas;
    }
    
    
    
    public void adicionarVertice(Vertice vertice) 
    {
        vertices.add(vertice);
    }
    
    public void adicionarArista(Arista arista) 
    {
        aristas.add(arista);
    }
    
    public void removerArista(int origen, int destino) 
    {
        List<Arista> tem= new ArrayList<>();
        for(Arista ar: aristas)
        {
            if(!(ar.getOrigen()==origen && ar.getDestino()==destino))
            {
                tem.add(ar);
            }
        }
        aristas = tem;
        
    }
    
    
   public abstract void verificarArista(int origen, int destino) throws GrafoExcepcion;
    
   
   public Vertice obtenerVerticexCodigo(int codigo)
   {
       for(Vertice vert: vertices)
       {
           if(vert.getCodigo()==codigo)
           {
               return vert;
           }    
       }    
       
       return null;
   }
    
}
