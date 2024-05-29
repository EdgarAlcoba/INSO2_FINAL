/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.MapaAsientos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

    @Override
    public MapaAsientos getMapaAsientos(String distribution) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MapaAsientos> cq = cb.createQuery(MapaAsientos.class);
        Root<MapaAsientos> root = cq.from(MapaAsientos.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("distribucion"), distribution));
        cq.where(predicates.toArray(new Predicate[0]));
        List<MapaAsientos> seatMaps = em.createQuery(cq).getResultList();

        if (seatMaps.isEmpty()) {
            System.out.println("Cannot create Avion. Could not find a distribution map for: " + distribution);
            return null;
        }

        int randomSeatMapIndex = ThreadLocalRandom.current().nextInt(0, (seatMaps.size()-1) + 1);

        return seatMaps.get(randomSeatMapIndex);
    }

    @Override
    public int getNumeroAsientos() {
        return 0;
    }
}
