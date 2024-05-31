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
import javafx.util.Pair;
import modelo.*;
import sun.security.krb5.internal.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
                ArrayList<BigDecimal> prices = getPrices(flight);
                flight.setPrecio(prices.isEmpty() ? BigDecimal.ZERO : prices.get(0).setScale(2, RoundingMode.CEILING));
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

    @Override
    public Vuelo searchById(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vuelo> cq = cb.createQuery(Vuelo.class);
        Root<Vuelo> vuelo = cq.from(Vuelo.class);

        Predicate condition = cb.equal(vuelo.get("id"), id);

        cq.where(condition);

        List<Vuelo> result = em.createQuery(cq).getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public ArrayList<BigDecimal> getPrices(Vuelo flight) {
        ArrayList<BigDecimal> prices = new ArrayList<>();

        if (flight == null) return prices;
        if (flight.getAvion() == null) return prices;
        MapaAsientos seatMap = flight.getAvion().getMapaAsientos();
        if (seatMap == null) return prices;


        BigDecimal averageKgCost = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.75, 1.31));
        BigDecimal costPrice = averageKgCost.multiply(BigDecimal.valueOf(flight.getGastoCombustibleKg()));

        int numberOfSeatsEconomy = flight.getAvion().getMapaAsientos().getSeccionEconomy().getNumeroAsientos();

        BigDecimal costPricePerPax = costPrice.divide(BigDecimal.valueOf(numberOfSeatsEconomy), RoundingMode.CEILING);

        BigDecimal minCost = costPricePerPax.multiply(BigDecimal.valueOf(1.3));

        double economyIncrease = ThreadLocalRandom.current().nextDouble(1, 1.31);
        double normalIncrease = ThreadLocalRandom.current().nextDouble(1.32, 1.91);
        double premiumIncrease = ThreadLocalRandom.current().nextDouble(2, 4.01);

        BigDecimal economyCost = minCost.multiply(BigDecimal.valueOf(economyIncrease)).setScale(2, RoundingMode.CEILING);
        BigDecimal normalCost = minCost.multiply(BigDecimal.valueOf(normalIncrease)).setScale(2, RoundingMode.CEILING);
        BigDecimal premiumCost = minCost.multiply(BigDecimal.valueOf(premiumIncrease)).setScale(2, RoundingMode.CEILING);

        if (seatMap.getDistribucion().equals("Economy")) {
            prices.add(economyCost);
        }

        if (seatMap.getDistribucion().equals("Normal")) {
            prices.add(economyCost);
            prices.add(normalCost);
        }

        if (seatMap.getDistribucion().equals("Premium")) {
            prices.add(economyCost);
            prices.add(normalCost);
            prices.add(premiumCost);
        }

        return prices;
    }

    @Override
    public ArrayList<Asiento> getSeats(Vuelo flight) {
        ArrayList<Asiento> seats = new ArrayList<>();
        if (flight.getAvion().getMapaAsientos().getDistribucion().equals("Economy")) {
            seats = new ArrayList<>(flight.getAvion().getMapaAsientos().getSeccionEconomy().getAsientos());
        }
        if (flight.getAvion().getMapaAsientos().getDistribucion().equals("Normal")) {
            seats = new ArrayList<>(flight.getAvion().getMapaAsientos().getSeccionEconomy().getAsientos());
            seats.addAll(flight.getAvion().getMapaAsientos().getSeccionNormal().getAsientos());
        }
        if (flight.getAvion().getMapaAsientos().getDistribucion().equals("Premium")) {
            seats = new ArrayList<>(flight.getAvion().getMapaAsientos().getSeccionEconomy().getAsientos());
            seats.addAll(flight.getAvion().getMapaAsientos().getSeccionNormal().getAsientos());
            seats.addAll(flight.getAvion().getMapaAsientos().getSeccionPremium().getAsientos());
        }
        return seats;
    }


    public ArrayList<Billete> getTickets(Vuelo flight) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Billete> cq = cb.createQuery(Billete.class);
        Root<Billete> ticketsRoot = cq.from(Billete.class);

        Predicate condition = cb.equal(ticketsRoot.get("vuelo"), flight);

        cq.where(condition);

        return new ArrayList<>(em.createQuery(cq).getResultList());
    }

    @Override
    public HashMap<Asiento, Boolean> getSeatMap(Vuelo flight) {
        HashMap<Asiento, Boolean> seatMap = new HashMap<>();
        ArrayList<Asiento> allSeats = getSeats(flight);
        ArrayList<Billete> tickets = getTickets(flight);

        for (Asiento seat: allSeats) {
            boolean reserved = false;
            for (Billete ticket: tickets) {
                reserved = ticket.getAsiento().equals(seat);
                if (reserved) break;
            }
            seatMap.put(seat, reserved);
        }

        return seatMap;
    }

    @Override
    public ArrayList<ArrayList<Pair<Asiento, Boolean>>> getSeatLists(Vuelo flight, String cabin) {
        Seccion cabinSection = flight.getAvion().getMapaAsientos().getSeccionEconomy();

        if (cabin.equals("Normal")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionNormal();
        }
        if (cabin.equals("Premium")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionPremium();
        }

        HashMap<Asiento, Boolean> seatMap = getSeatMap(flight);
        ArrayList<ArrayList<Pair<Asiento, Boolean>>> seatLists = new ArrayList<>();
        int rows = cabinSection.getNumFilas();
        int cols = cabinSection.getNumColumnas();

        for (int i=0; i<rows; i++) {
            ArrayList<Pair<Asiento, Boolean>> row = new ArrayList<>();
            for (int j=0; j<cols; j++) {
                for (Asiento seat: seatMap.keySet()) {
                    if (seat.getSeccion().getClase().equals(cabin)) {
                        if (seat.getPosicionX() == i && seat.getPosicionY() == j) {
                            row.add(new Pair<>(seat, seatMap.get(seat)));
                        }
                    }
                }
            }
            seatLists.add(row);
        }

        return seatLists;
    }

    @Override
    public int getSeatMatrixRows(Vuelo flight, String cabin) {
        Seccion cabinSection = flight.getAvion().getMapaAsientos().getSeccionEconomy();

        if (cabin.equals("Normal")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionNormal();
        }
        if (cabin.equals("Premium")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionPremium();
        }

        return cabinSection.getNumFilas();
    }

    @Override
    public int getSeatMatrixColumns(Vuelo flight, String cabin) {
        Seccion cabinSection = flight.getAvion().getMapaAsientos().getSeccionEconomy();

        if (cabin.equals("Normal")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionNormal();
        }
        if (cabin.equals("Premium")) {
            cabinSection = flight.getAvion().getMapaAsientos().getSeccionPremium();
        }

        return cabinSection.getNumColumnas();
    }

    @Override
    public Asiento bookSeat(Vuelo flight, String cabin, Asiento preferredSeat) {
        if (flight == null) return null;
        if (cabin == null || cabin.isEmpty()) cabin = "Economy";
        ArrayList<Asiento> seats = getSeats(flight);
        boolean selectedFound = false;

        for (Asiento seat: seats) {
            if (!seat.getTipo().equals("Seat")) continue;
            if (!seat.getSeccion().getClase().equals(cabin)) continue;
            boolean reserved = false;
            ArrayList<Billete> tickets = new ArrayList<>(getTickets(flight));
            for (Billete ticket: tickets) {
                reserved = ticket.getAsiento().equals(seat);
                if (reserved) break;
            }

            if (preferredSeat == null) {
                if (reserved) {
                    continue;
                } else {
                    return seat;
                }
            } else {
                if (seat.equals(preferredSeat) && reserved) {
                    selectedFound = true;
                    System.out.println("The selected seat was already occupied");
                }
            }
        }

        if (!selectedFound) {
            System.out.println("The selected seat was not found");
        }

        return null;
    }

    @Override
    public ArrayList<Integer> getAvailableSeats(Vuelo flight) {
        HashMap<Asiento, Boolean> seatMap = getSeatMap(flight);
        int freeEconomy = 0;
        int freeNormal = 0;
        int freePremium = 0;
        for (Asiento seat: seatMap.keySet()) {
            if (!seat.getTipo().equals("Seat")) continue;
            if (!seatMap.get(seat)) {
                String seatClass = seat.getSeccion().getClase();
                switch (seatClass) {
                    case "Economy":
                        freeEconomy++;
                        break;
                    case "Normal":
                        freeNormal++;
                        break;
                    case "Premium":
                        freePremium++;
                        break;
                }
            }
        }
        return new ArrayList<>(Arrays.asList(freeEconomy, freeNormal, freePremium));
    }
}
