/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.controlador;

import grafociudades.excepciones.GrafoExcepcion;
import grafociudades.modelo.Arista;
import grafociudades.modelo.Ciudad;
import grafociudades.modelo.GrafoAbstract;
import grafociudades.modelo.GrafoNoDirigido;
import grafociudades.modelo.Vertice;
import grafociudades.utilidad.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author carloaiza
 */
@Named(value = "controladorGrafo")
@SessionScoped
public class ControladorGrafo implements Serializable {

    private GrafoNoDirigido grafoND;
    private DefaultDiagramModel model;
    private Ciudad ciudad = new Ciudad();
    private boolean suspendEvent;
    private List<Vertice> rutaCorta;

    private int codigoInicio = 0;
    private int codigoFinal = 0;
    private int numColumna = 1;
         int numeroFicha=1;
    int ancho = 7;
    int alto = 6;
    int numVerticesTotal = ancho * alto;

    /**
     * Creates a new instance of ControladorGrafo
     */
    public ControladorGrafo() {
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public GrafoNoDirigido getGrafoND() {
        return grafoND;
    }

    public void setGrafoND(GrafoNoDirigido grafoND) {
        this.grafoND = grafoND;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public List<Vertice> getRutaCorta() {
        return rutaCorta;
    }

    public void setRutaCorta(List<Vertice> rutaCorta) {
        this.rutaCorta = rutaCorta;
    }

    public int getCodigoInicio() {
        return codigoInicio;
    }

    public void setCodigoInicio(int codigoInicio) {
        this.codigoInicio = codigoInicio;
    }

    public int getCodigoFinal() {
        return codigoFinal;
    }

    public void setCodigoFinal(int codigoFinal) {
        this.codigoFinal = codigoFinal;
    }
    
    public int getNumColumna() {
        return numColumna;
    }

    public void setNumColumna(int numColumna) {
        this.numColumna = numColumna;
    }

    @PostConstruct
    public void inicializar() {
        grafoND = new GrafoNoDirigido();

        //ciclo para crear los vertices
        int contNumVerticePintado = 1;
        int x = 0;
        int y = 0;
        int contadorSalto = 1;
        while (contNumVerticePintado <= numVerticesTotal) {

            grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                    new Ciudad("", false, x, y)));

            contNumVerticePintado++;

            if (contadorSalto == ancho) {
                y = y + 4;
                x = 0;
                contadorSalto = 0;
            } else {
                x = x + 4;
            }
            
            
            contadorSalto++;
        }

        //ciclo para crear las aristas horizontales
        int contNumAristaPintadoHorizon = 1;
        //contador para determinar cuando debemos de saltar a la siguiete fila
        int contadorInternoVertHori = 1;
        int inicioVertHorizon = 1;
        while (contNumAristaPintadoHorizon <= (numVerticesTotal - alto)) {
            //si el contador interno es menor al numero de columnas hace la coneccion
            //sino, salta a la siguiente fila para continuar con las conexiones
            if (contadorInternoVertHori < ancho) {
                grafoND.getAristas().add(new Arista(inicioVertHorizon, inicioVertHorizon + 1, 1));
            } else {
                inicioVertHorizon = inicioVertHorizon + 1;
                grafoND.getAristas().add(new Arista(inicioVertHorizon, inicioVertHorizon + 1, 1));
                contadorInternoVertHori = 1;
            }
            inicioVertHorizon++;
            contadorInternoVertHori++;
            contNumAristaPintadoHorizon++;
        }

        //ciclo para pintar las aristas verticales
        //el vertice actual mas el numero de columnas para conectar el de arriba con el de abajo
        int contNumAristaPintadoVertical = 1;
        int inicioVertical = 1;

        while (contNumAristaPintadoVertical <= (numVerticesTotal - ancho)) {
            grafoND.getAristas().add(new Arista(inicioVertical, inicioVertical + ancho, 1));
            inicioVertical++;
            contNumAristaPintadoVertical++;
        }

        //ciclo para crear las aristas diagonales derechas
        //contador de vertices que se inicializa en 1 al terminar una fila. Permite saltar de fila
        int contVertiSaltoFilaDere = 1;
        //contador almacena el vertice diagonal, inicia en 2, seria el nodo destino si le sumamos el ancho de la cuadricula
        int contDestinoDiagonalDere = 2;
        //contador para permitir saltar de vertice, este seria el vertice referencia o inicio.
        int contVerticePrincipioDere = 1;
        //contador del ciclo, se para cuando es igual al número del ultimo vertice o el ultimo vertice
        int conCicloDere = 1;
        //contar el numero de aristas diagonales que se pusieron
        int contAristasDiagonalesDere=1;
        //contadro para enviar el numero de aristas diagonales creadas
        int contAristasDiagonales =0;
        while (conCicloDere != (numVerticesTotal)) {

            if (contVertiSaltoFilaDere != ancho) {

                grafoND.getAristas().add(new Arista(contVerticePrincipioDere, contDestinoDiagonalDere + ancho, 1));
                conCicloDere = contDestinoDiagonalDere + ancho;
                contVerticePrincipioDere++;
                contDestinoDiagonalDere++;
                contVertiSaltoFilaDere++;
                contAristasDiagonalesDere++;
                contAristasDiagonales++;
            } else {
                contVerticePrincipioDere++;
                contDestinoDiagonalDere++;
                contVertiSaltoFilaDere = 1;
            }
            
        }

        ////aristas diagonales izquierdas
        //ciclo para crear las aristas diagonales izquierdas
        //contador de vertices que se inicializa en 1 al terminar una fila. Permite saltar de fila
        int contVertiSaltoFilaIzqui = 1;
        //contador almacena el vertice diagonal, inicia en 1, seria el nodo destino si le sumamos el ancho de la cuadricula
        int contDestinoDiagonalIzqui = 1;
        //contador para permitir saltar de vertice, este seria el vertice referencia o inicio.
        int contVerticePrincipioIzqui = 2;
        //contador terminar
        int terminar =1;
 
        while (terminar != contAristasDiagonales+1) {

            if (contVertiSaltoFilaIzqui != ancho) {

                grafoND.getAristas().add(new Arista(contVerticePrincipioIzqui, contDestinoDiagonalIzqui + ancho, 1));
                contVerticePrincipioIzqui++;
                contDestinoDiagonalIzqui++;
                contVertiSaltoFilaIzqui++;
                terminar++;
            } else {
                contVerticePrincipioIzqui++;
                contDestinoDiagonalIzqui++;
                contVertiSaltoFilaIzqui = 1;
            }
        
         }
               
        pintarGrafo(grafoND, model, ancho, alto, contAristasDiagonales);

    }

    private void pintarGrafo(GrafoAbstract grafo, DefaultDiagramModel modelo, int ancho, int alto,int contAristasDiagonales) {

         
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        // model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");

        model.setDefaultConnector(connector);

        for (Vertice vert : grafo.getVertices()) {
            Element element = new Element(vert);

            element.setX(String.valueOf(vert.getDato().getPosx()) + "em");
            element.setY(String.valueOf(vert.getDato().getPosy()) + "em");
            element.setId(String.valueOf(vert.getCodigo()));


            /* Puntos de coneccion para comunicar los vertices */
            //0 conector de arriba en el vertice
            EndPoint endPointSourceTop = createRectangleEndPoint(EndPointAnchor.TOP);
            endPointSourceTop.setSource(true);
            element.addEndPoint(endPointSourceTop);

            //1 conector de abajo en el vertice
            EndPoint endPointSourceBottom = createRectangleEndPoint(EndPointAnchor.BOTTOM);
            endPointSourceBottom.setSource(true);
            element.addEndPoint(endPointSourceBottom);

            //2 conector izquierdo del vertice
            EndPoint endPointSource = createRectangleEndPoint(EndPointAnchor.RIGHT);
            endPointSource.setSource(true);
            element.addEndPoint(endPointSource);

            //3 conector derecha del vertice
            EndPoint endPointSourceLeft = createRectangleEndPoint(EndPointAnchor.LEFT);
            endPointSourceLeft.setSource(true);
            element.addEndPoint(endPointSourceLeft);

            //4 conector ariba derecha del vertice
            EndPoint endPointSourceLeftTop = createRectangleEndPoint(EndPointAnchor.TOP_LEFT);
            endPointSourceLeftTop.setSource(true);
            element.addEndPoint(endPointSourceLeftTop);

            //5 conector ariba izquierda del vertice
            EndPoint endPointSourceRightTop = createRectangleEndPoint(EndPointAnchor.TOP_RIGHT);
            endPointSourceRightTop.setSource(true);
            element.addEndPoint(endPointSourceRightTop);

            //6 conector abajo derecha del vertice
            EndPoint endPointSourceBottomLeft = createRectangleEndPoint(EndPointAnchor.BOTTOM_LEFT);
            endPointSourceBottomLeft.setSource(true);
            element.addEndPoint(endPointSourceBottomLeft);

            //7 conector abajo izquierda del vertice
            EndPoint endPointSourceBottomRight = createRectangleEndPoint(EndPointAnchor.BOTTOM_RIGHT);
            endPointSourceBottomRight.setSource(true);
            element.addEndPoint(endPointSourceBottomRight);

            //determinar si los nodos se deben mover o no
            element.setDraggable(true);
            model.addElement(element);

        }

        
        int contadorNumAristaApintar = 1;

        //Pintar aristas, conexiones: verticales, horizontales, diagonales      
        for (Arista ar : grafoND.getAristas()) {
            //Encuentro origen
            for (Element el : model.getElements()) {
                if (el.getId().compareTo(String.valueOf(ar.getOrigen())) == 0) {
                    for (Element elDes : model.getElements()) {
                        if (elDes.getId().compareTo(String.valueOf(ar.getDestino())) == 0) {
                            //pintar las conexiones horizontales del conector 2 al 3
                            if (contadorNumAristaApintar <= (numVerticesTotal) - alto) {
                                Connection conn = new Connection(el.getEndPoints().get(2), elDes.getEndPoints().get(3));
                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                model.connect(conn);
                            } else 
                                //pintar las conexiones verticales del conector 1 al 0
                                if (contadorNumAristaApintar > ((numVerticesTotal) - alto) && contadorNumAristaApintar <= (((numVerticesTotal) - alto) + ((numVerticesTotal) - ancho))) {

                                Connection conn = new Connection(el.getEndPoints().get(1), elDes.getEndPoints().get(0));
                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                model.connect(conn);

                            } else 
                                //pintar las conexiones diagonales derechas
                                    if (contadorNumAristaApintar > (((numVerticesTotal) - alto) + ((numVerticesTotal) - ancho)) && contadorNumAristaApintar<=(contAristasDiagonales+(((numVerticesTotal) - alto) + ((numVerticesTotal) - ancho)))) {

                                Connection conn = new Connection(el.getEndPoints().get(7), elDes.getEndPoints().get(4));
                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                model.connect(conn);

                            } else{
                                       
                                 Connection conn = new Connection(el.getEndPoints().get(6), elDes.getEndPoints().get(5));
                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                model.connect(conn);
                            
                            }
                            
                             

                            break;
                        }
                    }
                }
            }
        
            contadorNumAristaApintar++;
        }
        
       

    }

        public void realizarJugada(){
        ciudad.setEstado(true);
        //restamos uno (1) al numero de columna que envie el usuario ya que 
        //los vertices se cuentan desde cero (0)
        int almacenarNumColum = numColumna;
        
        numColumna=numColumna-1;
        
                 
        if(numColumna<=ancho-1) {
            
        
            //si el primer vertice de la columna esta usado (pintado) mostramos el mensaje "ya se uso toda la columna"  
            if(grafoND.getVertices().get(numColumna).getDato().getEstado()){
                FacesMessage msg = new FacesMessage("ya se uso toda la columna");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else 
                //sino, si no se ha pintado, se continua sumando el ancho o
                //numero de columnas para pasar al vertide de abajo 
                if(grafoND.getVertices().get(numColumna).getDato().getEstado()==false){
                boolean bandera = false;
                while(bandera==false){
                     numColumna=numColumna+ancho;
                     
                    //si es el ultimo vertice y no se ha pintado, se pinta, su estado cambia a true 
                    if(numColumna>=(numVerticesTotal)){
                         Element elem1= model.getElements().get(numColumna-ancho);
                         grafoND.getVertices().get(numColumna-ancho-1).setDato(ciudad);
                         elem1.setStyleClass("ui-diagram-element-ficha-verde"); 
                     numColumna=almacenarNumColum;
                     bandera=true;
                     break;
                     } else  
                        
                        //sino se pinta el vertice anterior
                        if(grafoND.getVertices().get(numColumna).getDato().getEstado()==true) {
                     
                        Element elem1= model.getElements().get(numColumna-ancho);
                        numColumna --;
                        grafoND.getVertices().get(numColumna-ancho).setDato(ciudad);
                        elem1.setStyleClass("ui-diagram-element-ficha-verde");
                        numColumna=almacenarNumColum;
                        bandera=true;
                        break;
                         
                     }                    
                }
            numColumna --;
            }
        }
    } 

    /*public void realizarJugada(){
        ciudad.setEstado(true);
        //restamos uno (1) al numero de columna que envie el usuario ya que 
        //los vertices se cuentan desde cero (0)
        int almacenarNumColum = numColumna;
        numColumna=numColumna-1;
        
                 
        if(numColumna<=ancho-1) {
            
        
            //si el primer vertice de la columna esta usado (pintado) mostramos el mensaje "ya se uso toda la columna"  
            if(grafoND.getVertices().get(numColumna).getDato().getEstado()){
                FacesMessage msg = new FacesMessage("ya se uso toda la columna");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else 
                //sino, si no se ha pintado, se continua sumando el ancho o
                //numero de columnas para pasar al vertide de abajo 
                if(grafoND.getVertices().get(numColumna).getDato().getEstado()==false){
                boolean bandera = false;
                while(bandera==false){
                     numColumna=numColumna+ancho;
                     
                    //si es el ultimo vertice y no se ha pintado, se pinta, su estado cambia a true 
                    if(numColumna>=(numVerticesTotal)){
                         Element elem1= model.getElements().get(numColumna-ancho);
                         grafoND.getVertices().get(numColumna-ancho).setDato(ciudad);
                         elem1.setStyleClass("ui-diagram-element-ficha-verde"); 
                     numColumna=almacenarNumColum;
                     bandera=true;
                     break;
                     } else  
                        
                        //sino se pinta el vertice anterior
                        if(grafoND.getVertices().get(numColumna).getDato().getEstado()==true) {
                     
                        Element elem1= model.getElements().get(numColumna-ancho);
                        grafoND.getVertices().get(numColumna-ancho).setDato(ciudad);
                        elem1.setStyleClass("ui-diagram-element-ficha-verde");
                        numColumna=almacenarNumColum;
                        bandera=true;
                        break;
                         
                     }                    
                }
            
            }
        }
    } */
    
    public void adicionarCiudad() {
        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                ciudad));

        JsfUtil.addSuccessMessage("Ciudad Adicionada");

        ciudad = new Ciudad();
        //pintarGrafo(grafoND, model);
    }

    public void limpiarCiudad() {
        ciudad = new Ciudad();
    }

    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("ciudad");
        endPoint.setSource(true);
        endPoint.setWidth(5);
        endPoint.setHeight(5);
        endPoint.setStyle("{fillStyle:'#FFFFFF'}");
        endPoint.setHoverStyle("{fillStyle:'#00ffff'}");

        return endPoint;
    }

    public void onConnect(ConnectEvent event) {
        if (!suspendEvent) {

            int origen = Integer.parseInt(event.getSourceElement().getId());
            int destino = Integer.parseInt(event.getTargetElement().getId());
            FacesMessage msg = null;
            try {
                grafoND.verificarArista(origen, destino);
                grafoND.adicionarArista(new Arista(origen, destino, 1));
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conectado",
                        "Desde " + event.getSourceElement().getData() + " hacia " + event.getTargetElement().getData());

            } catch (GrafoExcepcion ex) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");

            }
            //pintarGrafo(grafoND, model);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("frmGrafo");
            PrimeFaces.current().ajax().update("frmCiudad");
        } else {
            suspendEvent = false;
        }
    }

    public void onDisconnect(DisconnectEvent event) {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Desconectado",
                "Desde " + event.getSourceElement().getData() + " hacia " + event.getTargetElement().getData());

        int origen = Integer.parseInt(event.getSourceElement().getId());
        int destino = Integer.parseInt(event.getTargetElement().getId());
        grafoND.removerArista(origen, destino);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        PrimeFaces.current().ajax().update("frmGrafo");
        PrimeFaces.current().ajax().update("frmCiudad");
    }

    public void onConnectionChange(ConnectionChangeEvent event) {
        int origenAnt = Integer.parseInt(event.getOriginalSourceElement().getId());
        int destinoAnt = Integer.parseInt(event.getOriginalTargetElement().getId());

        int origen = Integer.parseInt(event.getNewSourceElement().getId());
        int destino = Integer.parseInt(event.getNewTargetElement().getId());
        FacesMessage msg = null;
        try {
            grafoND.removerArista(origenAnt, destinoAnt);
            grafoND.verificarArista(origen, destino);
            grafoND.adicionarArista(new Arista(origen, destino, 1));
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connección Modificada",
                    "Origen inicial: " + event.getOriginalSourceElement().getData()
                    + ", Nuevo Origen: " + event.getNewSourceElement().getData()
                    + ",Destino inicial: " + event.getOriginalTargetElement().getData()
                    + ", Nuevo Destino: " + event.getNewTargetElement().getData());

        } catch (GrafoExcepcion ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            //pintarGrafo(grafoND, model);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("frmGrafo");
        PrimeFaces.current().ajax().update("frmCiudad");
        suspendEvent = true;
    }

    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("ciudad");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

        return endPoint;
    }

    public void onRowEdit(RowEditEvent event) {
        Arista ar = ((Arista) event.getObject());

        FacesMessage msg = new FacesMessage("Arista Modificada", ar.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //pintarGrafo(grafoND, model);
        PrimeFaces.current().ajax().update("frmGrafo");

    }

    public void onRowCancel(RowEditEvent event) {
        Arista ar = ((Arista) event.getObject());
        FacesMessage msg = new FacesMessage("Edición  Cancelada", ar.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("frmGrafo");

    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().ajax().update("frmGrafo");
    }

    public void calcularRutaCorta() {
        if (codigoFinal != codigoInicio) {
            Dijkstra dijstra = new Dijkstra(grafoND,
                    grafoND.obtenerVerticexCodigo(codigoInicio), grafoND.obtenerVerticexCodigo(codigoFinal));

            rutaCorta = dijstra.calcularRutaMasCorta();
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Origen y Destino no pueden ser iguales", "Origen y Destino no pueden ser iguales");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("grwErrores");
        }
    }
}
