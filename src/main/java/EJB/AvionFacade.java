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

import es.unileon.inso2.aerolinea.exceptions.CreateAirplaneException;
import modelo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Administrador
 */
@Stateless
public class AvionFacade extends AbstractFacade<Avion> implements AvionFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AvionFacade() {
        super(Avion.class);
    }

    public void createAvion(Avion avion) throws CreateAirplaneException {
        if (avion.getMsn() == null || avion.getMsn().isEmpty()) {
            throw new CreateAirplaneException("El MSN no puede ser nulo o vacío");
        }

        if (msnExists(avion.getMsn())) {
            throw new CreateAirplaneException("Un avión con el mismo MSN ya existe");
        }

        if (avion.getMatricula() == null || avion.getMatricula().isEmpty()) {
            throw new CreateAirplaneException("La matrícula del avión no puede ser nula o vacía");
        }

        if (registrationExists(avion.getMatricula())) {
            throw new CreateAirplaneException("Un avión con la misma matrícula ya existe");
        }

        if (avion.getModelo() == null || avion.getModelo().isEmpty()) {
            throw new CreateAirplaneException("El modelo del avión no puede ser nulo o vacío");
        }

        if (avion.getPrecioCompra() == null || avion.getPrecioCompra().floatValue() <= 0) {
            throw new CreateAirplaneException("El modelo del avión no puede ser nulo o vacío");
        }

        if (avion.getMapaAsientos() == null) {
            throw new CreateAirplaneException("El mapa de asientos seleccionado no está disponible");
        }

        em.persist(avion);
    }

    private boolean msnExists(String msn) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Avion> cq = cb.createQuery(Avion.class);
        Root<Avion> airplaneRoot = cq.from(Avion.class);

        cq.select(airplaneRoot).where(cb.equal(airplaneRoot.get("msn"), msn));

        List<Avion> airplanes = em.createQuery(cq).getResultList();
        return !airplanes.isEmpty();
    }

    private boolean registrationExists(String registration) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Avion> cq = cb.createQuery(Avion.class);
        Root<Avion> airplaneRoot = cq.from(Avion.class);

        cq.select(airplaneRoot).where(cb.equal(airplaneRoot.get("matricula"), registration));

        List<Avion> airplanes = em.createQuery(cq).getResultList();
        return !airplanes.isEmpty();
    }
}
