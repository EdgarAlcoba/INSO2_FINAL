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

import es.unileon.inso2.aerolinea.exceptions.CreateBagException;
import es.unileon.inso2.aerolinea.exceptions.CreateFlightException;
import modelo.Avion;
import modelo.Usuario;
import modelo.Vuelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    Pattern icaoPattern = Pattern.compile("[A-Z]{4}", Pattern.CASE_INSENSITIVE);

    public VueloFacade() {
        super(Vuelo.class);
    }

    public void createFlight(Vuelo vuelo) throws CreateFlightException {
        if (vuelo == null || vuelo.getNumero().isEmpty()) {
            throw new CreateFlightException("El vuelo no puede ser nulo o vacío", vuelo);
        }

        if (exists(vuelo.getNumero())) {
            throw new CreateFlightException("El número de vuelo ya existe", vuelo);
        }

        if (vuelo.getOrigen() == null || vuelo.getOrigen().isEmpty()) {
            throw new CreateFlightException("El origen del vuelo no puede ser nulo o vacio", vuelo);
        }

        if (vuelo.getDestino() == null || vuelo.getDestino().isEmpty()) {
            throw new CreateFlightException("El destino del vuelo no puede ser nulo o vacio", vuelo);
        }

        if (vuelo.getOrigen().equals(vuelo.getDestino())) {
            throw new CreateFlightException("El origen y destino del vuelo no pueden ser iguales", vuelo);
        }

        if (!icaoPattern.matcher(vuelo.getOrigen()).find()) {
            throw new CreateFlightException("El código del aeropuerto de origen debe ser formato ICAO. Ejemplo: LECO", vuelo);
        }

        if (!icaoPattern.matcher(vuelo.getDestino()).find()) {
            throw new CreateFlightException("El código del aeropuerto de destino debe ser formato ICAO. Ejemplo: LEST", vuelo);
        }

        if (vuelo.getSalida() == null) {
            throw new CreateFlightException("La hora de salida del vuelo no puede ser nula", vuelo);
        }

        if (vuelo.getLlegada() == null) {
            throw new CreateFlightException("La hora de llegada del vuelo no puede ser nula", vuelo);
        }

        if (vuelo.getSalida().after(vuelo.getLlegada()) || vuelo.getSalida().equals(vuelo.getLlegada())) {
            throw new CreateFlightException("La hora de salida no puede ser igual o despues que la de llegada", vuelo);
        }

        if (vuelo.getLlegada().before(vuelo.getSalida()) || vuelo.getLlegada().equals(vuelo.getSalida())) {
            throw new CreateFlightException("La hora de llegada no puede ser igual o antes que la de salida", vuelo);
        }

        if (vuelo.getGastoCombustibleKg() <= 0) {
            throw new CreateFlightException("El gasto de combustible en kg tiene que ser un número real positivo", vuelo);
        }

        if (vuelo.getAvion() == null) {
            throw new CreateFlightException("El avion del vuelo no puede ser nulo", vuelo);
        }

        if (!airplaneAvailable(vuelo)) {
            throw new CreateFlightException("El avion seleccionado no está disponible en esas fechas", vuelo);
        }

        if (vuelo.getPrecioMaleta().doubleValue() < 0) {
            throw new CreateFlightException("El precio de la maleta por kg debe ser un número real positivo", vuelo);
        }

        em.persist(vuelo);
    }

    @Override
    public ArrayList<Vuelo> searchAllDay(Date day, String origin, String destination) {
        if (day == null || origin == null || destination == null) {
            System.out.println("searchAllDay day origin or destination is null");
            return new ArrayList<>();
        }

        ArrayList<Vuelo> dayFlights = search(day);
        ArrayList<Vuelo> flights = new ArrayList<>();

        for (Vuelo flight : dayFlights) {
            if (flight.getOrigen().equals(origin) && flight.getDestino().equals(destination)) {
                flights.add(flight);
            }
        }

        return flights;
    }

    private boolean exists(String flightNumber) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vuelo> cq = cb.createQuery(Vuelo.class);
        Root<Vuelo> root = cq.from(Vuelo.class);
        Predicate predicate = cb.equal(root.get("numero"), flightNumber);
        cq.select(root).where(predicate);
        List<Vuelo> flights = em.createQuery(cq).getResultList();
        return !flights.isEmpty();
    }

    private boolean airplaneAvailable(Vuelo vuelo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Vuelo> vueloRoot = cq.from(Vuelo.class);
        Join<Vuelo, Avion> avionJoin = vueloRoot.join("avion");

        Predicate avionMatch = cb.equal(avionJoin, vuelo.getAvion());
        Predicate overlap = cb.and(
            cb.lessThanOrEqualTo(vueloRoot.get("salida"), vuelo.getLlegada()),
            cb.greaterThanOrEqualTo(vueloRoot.get("llegada"), vuelo.getSalida())
        );

        cq.select(vueloRoot.get("id")).where(cb.and(avionMatch, overlap));

        List<Long> flightIds = em.createQuery(cq).getResultList();
        return flightIds.isEmpty();
    }

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

    public ArrayList<Vuelo> search(Date date) {
        if (date == null) {
            System.out.println("The date must not be null");
            return new ArrayList<>();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = calendar.getTime();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vuelo> cq = cb.createQuery(Vuelo.class);
            Root<Vuelo> vuelo = cq.from(Vuelo.class);

            Predicate condition = cb.between(vuelo.get("salida"), startOfDay, endOfDay);

            cq.where(condition);

            List<Vuelo> result = em.createQuery(cq).getResultList();

            return new ArrayList<>(result);
        } catch (Exception e) {
            System.out.println("Hubo un error buscando vuelos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
