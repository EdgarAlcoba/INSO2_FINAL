/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreatePassengerException;
import modelo.Pasajero;

/**
 *
 * @author Administrador
 */
@Local
public interface PasajeroFacadeLocal {

    void create(Pasajero pasajero);

    void createPasajero(Pasajero pasajero) throws CreatePassengerException;

    void edit(Pasajero pasajero);

    void remove(Pasajero pasajero);

    Pasajero find(Object id);

    List<Pasajero> findAll();

    List<Pasajero> findRange(int[] range);

    int count();
    
}
