/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociudades.excepciones;

/**
 *
 * @author carloaiza
 */
public class GrafoExcepcion extends Exception{

    public GrafoExcepcion() {
    }

    public GrafoExcepcion(String message) {
        super(message);
    }

    public GrafoExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

    public GrafoExcepcion(Throwable cause) {
        super(cause);
    }
    
}
