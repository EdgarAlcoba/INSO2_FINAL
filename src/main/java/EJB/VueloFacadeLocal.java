/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreateFlightException;
import modelo.Asiento;
import modelo.Billete;
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

    Vuelo searchById(int id);

    /**
     * IMPORTANTE: Solo se puede llamar a este metodo una vez creado el vuelo en la DB
     * Obtiene los precios del vuelo
     * Si el avion tiene solo clase turista len = 1
     * Si el avion tiene clase turista + normal len = 2
     * Si el avion tiene clase turista + normal + premium len = 3
     * En caso de error len = 0
     * @param flight Vuelo a obtener los precios
     * @return ArrayList con los precios
     */
    ArrayList<BigDecimal> getPrices(Vuelo flight);

    ArrayList<Asiento> getSeats(Vuelo flight);

    HashMap<Asiento, Boolean> getSeatMap(Vuelo flight);

    Asiento[][] getSeatMatrix(Vuelo flight, String cabin);

    /**
     *
     * @param flight Vuelo a obtener el asiento.
     * @param cabin Economy, Normal o Premium (If null or empty will be Economy)
     * @param preferredSeat Asiento predefinido. NULL para obtener uno aleatorio.
     * @return NULL si el asiento esta reservado o no hay disponibles
     */
    Asiento bookSeat(Vuelo flight, String cabin, Asiento preferredSeat);

    ArrayList<Integer> getAvailableSeats(Vuelo flight);

    ArrayList<Billete> getTickets(Vuelo flight);


}
