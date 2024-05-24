/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.MapaAsientos;
import modelo.ModeloAvion;

/**
 *
 * @author Administrador
 */
@Local
public interface MapaAsientosFacadeLocal {

    void create(MapaAsientos mapaAsientos);

    void edit(MapaAsientos mapaAsientos);

    void remove(MapaAsientos mapaAsientos);

    MapaAsientos find(Object id);

    List<MapaAsientos> findAll();

    List<MapaAsientos> findRange(int[] range);

    int count();

    MapaAsientos getPremiumMap(ModeloAvion modeloAvion);
    MapaAsientos getNormalMap(ModeloAvion modeloAvion);
    MapaAsientos getEconomyMap(ModeloAvion modeloAvion);
    
}
