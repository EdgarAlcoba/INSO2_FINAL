/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import es.unileon.inso2.aerolinea.exceptions.CreateTicketException;
import modelo.*;

import java.math.BigDecimal;
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

    @EJB
    private VueloFacadeLocal vueloFacadeLocal;

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
            throw new CreateTicketException("No se puede comprar un billete con un usuario nulo");
        }

        if (billete.getVuelo() == null) {
            throw new CreateTicketException("No se puede comprar un billete con un vuelo nulo");
        }

        if (billete.getVuelo().getSalida().before(new Date(System.currentTimeMillis()))) {
            throw new CreateTicketException("No se puede comprar un vuelo que ya ha salido");
        }

        if (billete.getPasajero() == null) {
            throw new CreateTicketException("No se puede comprar un billete con un pasajero nulo");
        }

        if (billete.getAsiento() == null) {
            throw new CreateTicketException("No se puede comprar un billete con asiento nulo");
        }

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

    public ArrayList<Maleta> getBags(Billete ticket) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Maleta> cq = cb.createQuery(Maleta.class);
        Root<Maleta> bagsRoot = cq.from(Maleta.class);

        Predicate condition = cb.equal(bagsRoot.get("billete"), ticket);

        cq.where(condition);

        return new ArrayList<>(em.createQuery(cq).getResultList());
    }

    @Override
    public BigDecimal getTotalPrice(Billete ticket) {
        if (ticket.getVuelo() == null) {
            throw new IllegalArgumentException("No se puede obtener el precio total de un billete sin tener un vuelo asociado");
        }

        ArrayList<BigDecimal> prices = vueloFacadeLocal.getPrices(ticket.getVuelo());
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;

        if (ticket.getVuelo().getAvion().getMapaAsientos().getDistribucion().equals("Economy")) {
            if (prices.isEmpty()) {
                throw new IllegalArgumentException("Numero de precios < 1 en economy");
            }
            basePrice = prices.get(0);
        }

        if (ticket.getVuelo().getAvion().getMapaAsientos().getDistribucion().equals("Normal")) {
            if (prices.size() < 2) {
                throw new IllegalArgumentException("Numero de precios < 2 en normal");
            }
            basePrice = prices.get(1);
        }

        if (ticket.getVuelo().getAvion().getMapaAsientos().getDistribucion().equals("Premium")) {
            if (prices.size() < 3) {
                throw new IllegalArgumentException("Numero de precios < 3 en premium");
            }
            basePrice = prices.get(2);
        }

        BigDecimal bagsPrice = getTotalBagsPrice(ticket);

        totalPrice = totalPrice.add(basePrice);
        totalPrice = totalPrice.add(bagsPrice);

        return totalPrice;
    }

    @Override
    public HashMap<Maleta, BigDecimal> getBagsPrice(Billete ticket) {
        if (ticket.getVuelo() == null) {
            throw new IllegalArgumentException("No se puede obtener precios de maletas si no hay un vuelo asociado");
        }

        HashMap<Maleta,BigDecimal> bags = new HashMap<>();

        BigDecimal kgCost = ticket.getVuelo().getPrecioMaleta();
        ArrayList<Maleta> bagsList = new ArrayList<>(getBags(ticket));
        for (Maleta bag : bagsList) {
            bags.put(bag, BigDecimal.valueOf(bag.getPesoKg()).multiply(kgCost));
        }

        return bags;
    }

    @Override
    public BigDecimal getTotalBagsPrice(Billete ticket) {
        if (ticket.getVuelo() == null) {
            throw new IllegalArgumentException("No se puede obtener precios de maletas si no hay un vuelo asociado");
        }

        BigDecimal totalBagsCost = BigDecimal.ZERO;

        ArrayList<Maleta> bags = new ArrayList<>(getBags(ticket));
        BigDecimal kgCost = ticket.getVuelo().getPrecioMaleta();
        for (Maleta bag : bags) {
            totalBagsCost = totalBagsCost.add(BigDecimal.valueOf(bag.getPesoKg()).multiply(kgCost));
        }

        return totalBagsCost;
    }
}
