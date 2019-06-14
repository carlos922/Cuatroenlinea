/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carloaiza
 */
public class VerticeDijkstra {
    private Vertice vertice;
    private Vertice verticeAntecesor;
    private int pesoAcumulado;    
    private List<VerticeDijkstra> listadoAdyacencias;
    private boolean usado =false;

    public VerticeDijkstra() {
    }

    public Vertice getVertice() {
        return vertice;
    }

    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    public Vertice getVerticeAntecesor() {
        return verticeAntecesor;
    }

    public void setVerticeAntecesor(Vertice verticeAntecesor) {
        this.verticeAntecesor = verticeAntecesor;
    }

    public int getPesoAcumulado() {
        return pesoAcumulado;
    }

    public void setPesoAcumulado(int pesoAcumulado) {
        this.pesoAcumulado = pesoAcumulado;
    }

    public List<VerticeDijkstra> getListadoAdyacencias() {
        return listadoAdyacencias;
    }

    public void setListadoAdyacencias(List<VerticeDijkstra> listadoAdyacencias) {
        this.listadoAdyacencias = listadoAdyacencias;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
            
    
    public void llenarAdyacenciasVertice(GrafoNoDirigido grafo, List<VerticeDijkstra> listadoVertices)
    {
        listadoAdyacencias = new ArrayList<>();
        for(Arista arista: grafo.getAristas())
        {
            int codigoDestino=0;
            if(arista.getOrigen()==vertice.getCodigo())
            {
               codigoDestino= arista.getDestino();
            }
            if(arista.getDestino()==vertice.getCodigo())
            {
               codigoDestino= arista.getOrigen();
            }
            
            if(codigoDestino!=0)
            {
                VerticeDijkstra vert= obtenerVerticeDijkstraxCodigo(codigoDestino, listadoVertices);
                if(vert !=null)
                {
                   if(!vert.isUsado()) 
                   {
                       if(vert.getVerticeAntecesor()!=null)
                       {
                           //Ya se había gestionado una ruta anterior
                           int nuevoPeso=this.getPesoAcumulado()+arista.getPeso();
                           if(nuevoPeso < vert.getPesoAcumulado())
                           {
                               vert.setVerticeAntecesor(this.vertice);
                               vert.setPesoAcumulado(nuevoPeso);                           
                           }    
                       }
                       else
                       {
                           vert.setVerticeAntecesor(this.vertice);
                           vert.setPesoAcumulado(this.getPesoAcumulado()+arista.getPeso());                           
                       } 
                       listadoAdyacencias.add(vert);  
                   }                       
                }    
                else
                {
                    vert=new VerticeDijkstra();
                    vert.setVertice(grafo.obtenerVerticexCodigo(codigoDestino));
                     vert.setPesoAcumulado(this.getPesoAcumulado()+arista.getPeso());
                    vert.setVerticeAntecesor(this.vertice);                    
                    listadoAdyacencias.add(vert);  
                }  
                          
            }    
        }    
    }    

    public VerticeDijkstra obtenerVerticeDijkstraxCodigo(int codigo, List<VerticeDijkstra> listadoVertices)
    {
        for(VerticeDijkstra vert: listadoVertices)
        {
            if(vert.getVertice().getCodigo()==codigo)
            {
                return vert;
            }    
        }
        return null;
    }
    
    public VerticeDijkstra obtenerAdyacenciaMenorPeso()
    {
        int menor=Integer.MAX_VALUE;
        VerticeDijkstra vertMenor=null;
        for(VerticeDijkstra vert: listadoAdyacencias)
        {
            if(!vert.isUsado() && vert.getPesoAcumulado()< menor )
            {
                vertMenor= vert;
                menor=vert.getPesoAcumulado();
            }
        }
        
        return vertMenor;
    }
    
    
   
    
}
