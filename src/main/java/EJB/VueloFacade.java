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

import modelo.Usuario;
import modelo.Vuelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
@Stateless
public class VueloFacade extends AbstractFacade<Vuelo> implements VueloFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VueloFacade() {
        super(Vuelo.class);
    }

    @Override
    public ArrayList<Vuelo> searchBetween(Date from, Date to) {
        if (from == null && to == null) {
            return new ArrayList<>(findAll());
        }

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vuelo> cq = cb.createQuery(Vuelo.class);
            Root<Vuelo> vuelo = cq.from(Vuelo.class);

            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(vuelo.get("salida"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(vuelo.get("salida"), to));
            }

            cq.where(predicates.toArray(new Predicate[0]));

            List<Vuelo> result = em.createQuery(cq).getResultList();

            return new ArrayList<>(result);
        } catch (Exception e) {
            System.out.println("Hubo un error buscando vuelos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
