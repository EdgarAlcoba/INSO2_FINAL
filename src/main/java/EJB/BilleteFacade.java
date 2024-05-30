/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import modelo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Administrador
 */
@Stateless
public class BilleteFacade extends AbstractFacade<Billete> implements BilleteFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BilleteFacade() {
        super(Billete.class);
    }

    public void createBillete(Billete billete) throws Exception {
        billete.setFechaCompra(new Date(System.currentTimeMillis()));

        if (billete.getUsuario() == null) {
            throw new IllegalArgumentException("No se puede comprar un billete con un usuario nulo");
        }

        if (billete.getVuelo() == null) {
            throw new IllegalArgumentException("No se puede comprar un billete con un vuelo nulo");
        }

        if (billete.getPasajero() == null) {
            throw new IllegalArgumentException("No se puede comprar un billete con un pasajero nulo");
        }

        billete.setPrecio(billete.getPrecioTotal());

        Asiento seat = (billete.getAsiento() == null) ?
                        billete.getVuelo().getAsientoAleatorio() :
                        billete.getVuelo().getAsiento(billete.getAsiento());
        billete.setAsiento(seat);



        em.persist(billete);
    }

    @Override
    public ArrayList<Billete> searchBetween(Date from, Date to) {
        return searchBetween(from, to, null);
    }

    @Override
    public ArrayList<Billete> searchBetween(Date from, Date to, Usuario user) {
        if (from == null && to == null) {
            return new ArrayList<>(findAll());
        }

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Billete> cq = cb.createQuery(Billete.class);
            Root<Billete> ticketRoot = cq.from(Billete.class);
            Join<Billete, Vuelo> flightJoin = ticketRoot.join("vuelo");

            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(flightJoin.get("salida"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(ticketRoot.get("salida"), to));
            }

            if (user != null) {
                predicates.add(cb.equal(flightJoin.get("usuario"), user.getId()));
            }

            cq.where(predicates.toArray(new Predicate[0]));

            List<Billete> result = em.createQuery(cq).getResultList();

            return new ArrayList<>(result);
        } catch (Exception e) {
            System.out.println("Hubo un error buscando billetes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
