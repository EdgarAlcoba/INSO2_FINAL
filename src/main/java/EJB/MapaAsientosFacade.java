/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.MapaAsientos;
import modelo.ModeloAvion;

import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
@Stateless
public class MapaAsientosFacade extends AbstractFacade<MapaAsientos> implements MapaAsientosFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MapaAsientosFacade() {
        super(MapaAsientos.class);
    }

    private MapaAsientos getMap(String sectionClass, ModeloAvion modeloAvion) {
        ArrayList<MapaAsientos> seatMaps = new ArrayList<>(findAll());

        for (MapaAsientos seatMap : seatMaps) {
            if (seatMap.getModeloAvion().equals(modeloAvion)) {
                if (sectionClass.equals("EconomyMap")) {
                    if (seatMap.getSeccion3() == null && seatMap.getSeccion2() == null) {
                        return seatMap;
                    }
                }
                if (sectionClass.equals("NormalMap")) {
                    if (seatMap.getSeccion3() == null && seatMap.getSeccion2() != null) {
                        return seatMap;
                    }
                }
                if (sectionClass.equals("PremiumMap")) {
                    if (seatMap.getSeccion3() != null) {
                        return seatMap;
                    }
                }
            }
        }

        System.out.println("No se pudo encontrar un seat map para el modelo de avion: " + modeloAvion.getNombre());
        return null;
    }

    @Override
    public MapaAsientos getPremiumMap(ModeloAvion modeloAvion) {
        return getMap("PremiumMap", modeloAvion);
    }

    @Override
    public MapaAsientos getNormalMap(ModeloAvion modeloAvion) {
        return getMap("NormalMap", modeloAvion);
    }

    @Override
    public MapaAsientos getEconomyMap(ModeloAvion modeloAvion) {
        return getMap("EconomyMap", modeloAvion);
    }
}
