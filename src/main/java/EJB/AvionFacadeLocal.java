/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreateAirplaneException;
import modelo.Avion;

/**
 *
 * @author Administrador
 */
@Local
public interface AvionFacadeLocal {

    void create(Avion avion);

    void createAvion(Avion avion) throws CreateAirplaneException;

    void edit(Avion avion);

    void remove(Avion avion);

    Avion find(Object id);

    List<Avion> findAll();

    List<Avion> findRange(int[] range);

    int count();
    
}
