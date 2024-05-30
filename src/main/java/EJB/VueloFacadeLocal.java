/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreateFlightException;
import modelo.Vuelo;

/**
 *
 * @author Administrador
 */
@Local
public interface VueloFacadeLocal {

    void create(Vuelo vuelo);

    /**
     * Crea un vuelo
     * @param flight Vuelo a crear
     * @throws CreateFlightException Cuando hay errores creando un vuelo. Este error debe mostrarse al usuario.
     */
    void createFlight(Vuelo flight) throws CreateFlightException;

    void edit(Vuelo vuelo);

    void remove(Vuelo vuelo);

    Vuelo find(Object id);

    List<Vuelo> findAll();

    List<Vuelo> findRange(int[] range);

    int count();

    ArrayList<Vuelo> searchAllDay(Date day, String origin, String destination);

    ArrayList<Vuelo> searchBetween(Date from, Date to);

    ArrayList<Vuelo> search(Date date);
}
