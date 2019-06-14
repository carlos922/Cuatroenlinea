/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.controlador;

import grafociudades.modelo.Arista;
import grafociudades.modelo.GrafoNoDirigido;
import grafociudades.modelo.Vertice;
import grafociudades.modelo.VerticeDijkstra;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carloaiza
 */
public class Dijkstra {
    private GrafoNoDirigido grafo;
    private Vertice nodoInicio;
    private Vertice nodoFinal;

    public Dijkstra(GrafoNoDirigido grafo, Vertice nodoInicio, Vertice nodoFinal) {
        this.grafo = grafo;
        this.nodoInicio = nodoInicio;
        this.nodoFinal = nodoFinal;
    }
   
    
    public List<Vertice> calcularRutaMasCorta()
    {
        List<Vertice> ruta= new ArrayList<>();
        VerticeDijkstra inicio= new VerticeDijkstra();
        inicio.setPesoAcumulado(0);
        inicio.setVerticeAntecesor(null);
        inicio.setUsado(true);
        inicio.setVertice(nodoInicio);
        List<VerticeDijkstra> listadoVerticesUsados=new ArrayList<>();
        listadoVerticesUsados.add(inicio);
        /// se hace dijkstra hasta que todos los vertices estén marcados
        VerticeDijkstra antecesor= inicio;
        int contVertusados=1;
        while(contVertusados< grafo.getVertices().size())
        {
            //se llenan las adyacencias del antecesor que no estén marcadas
            antecesor.llenarAdyacenciasVertice(grafo, listadoVerticesUsados);            
            VerticeDijkstra menor=antecesor.obtenerAdyacenciaMenorPeso();
            menor.setUsado(true);
            listadoVerticesUsados.addAll(this.obtenerAdyacenciasNuevas(antecesor.getListadoAdyacencias(), listadoVerticesUsados));
            antecesor=menor;            
            //se busca la menor adyacencia y se inicia de nuevo el proceso
            contVertusados= contarVerticesUsados(listadoVerticesUsados);
        }                
        //Ya deben estar los antecesores listos y enlazados, se deben recorrer de atras hacia adelante 
        //hasta llenar la ruta empezando en el nodo final
        VerticeDijkstra destino=null;
        ruta.add(nodoFinal);
        int codigoAnt=nodoFinal.getCodigo();
        do
        {
            destino=obtenerVerticeAntecesorxCodigo(codigoAnt, listadoVerticesUsados);
            codigoAnt= destino.getVerticeAntecesor().getCodigo();
            ruta.add(destino.getVerticeAntecesor());
        }while(codigoAnt!=nodoInicio.getCodigo());
        
        return ruta;
    }
    
    
    public VerticeDijkstra obtenerVerticeAntecesorxCodigo(int codigo, List<VerticeDijkstra> lista )
   {
       for(VerticeDijkstra vert: lista)
       {
           if(vert.getVertice().getCodigo()==codigo)
           {
               return vert;
           }    
       }    
       
       return null;
   }
    
    public int contarVerticesUsados(List<VerticeDijkstra> lista)
    {
        int cont =0;
        for(VerticeDijkstra vert: lista)
        {
            if(vert.isUsado())
            {
                cont++;
            }    
        }
        return cont;
    }
    
    
     public List<VerticeDijkstra> obtenerAdyacenciasNuevas(List<VerticeDijkstra> listadoAdyacencias,  List<VerticeDijkstra> listadoVerticesUsados)
    {
        List<VerticeDijkstra> nuevas= new ArrayList<>();
        for(VerticeDijkstra vert: listadoAdyacencias)
        {
            if(vert.obtenerVerticeDijkstraxCodigo(vert.getVertice().getCodigo(), listadoVerticesUsados)==null)
            {
                nuevas.add(vert);
            }    
        }
        return nuevas;
        
    }
     
     
    
}
