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

import es.unileon.inso2.aerolinea.exceptions.CreatePassengerException;
import modelo.Billete;
import modelo.Pasajero;
import modelo.Vuelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
@Stateless
public class PasajeroFacade extends AbstractFacade<Pasajero> implements PasajeroFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    Pattern niePattern = Pattern.compile("([XYZxyz]\\d{7}[A-HJ-NP-TV-Za-hj-np-tv-z])", Pattern.CASE_INSENSITIVE);
    Pattern dniPattern = Pattern.compile("(\\d{8}[A-HJ-NP-TV-Za-hj-np-tv-z])", Pattern.CASE_INSENSITIVE);
    Pattern passportPattern = Pattern.compile("^(?!^0+$)[a-zA-Z0-9]{3,20}", Pattern.CASE_INSENSITIVE);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasajeroFacade() {
        super(Pasajero.class);
    }

    public Pasajero createPasajero(Pasajero passenger) throws CreatePassengerException {
        if (passenger == null) {
            throw new IllegalArgumentException("createPasajero: passenger must not be null");
        }

        if (passenger.getNombre() == null || passenger.getNombre().isEmpty()) {
            throw new CreatePassengerException("El nombre no puede ser nulo o vacío");
        }

        if (passenger.getApellido1() == null || passenger.getApellido1().isEmpty()) {
            throw new CreatePassengerException("El primer apellido no puede ser nulo o vacío");
        }

        if (passenger.getDniNIE() == null || passenger.getDniNIE().isEmpty()) {
            throw new CreatePassengerException("El DNI/NIE no puede ser nulo o vacio");
        }

        nifNieOk(passenger.getDniNIE());

        if (passenger.getPasaporte() != null && !passenger.getPasaporte().isEmpty()) {
            if (!passportPattern.matcher(passenger.getPasaporte()).find()) {
                throw new CreatePassengerException("Número de pasaporte inválido");
            }
        }

        Pasajero oldPassenger = exists(passenger.getDniNIE());
        if (oldPassenger == null) {
            em.persist(passenger);
            return passenger;
        } else {
            oldPassenger.setNombre(passenger.getNombre());
            oldPassenger.setApellido1(passenger.getApellido1());
            oldPassenger.setApellido2(passenger.getApellido2());
            oldPassenger.setDniNIE(passenger.getDniNIE());
            oldPassenger.setPasaporte(passenger.getPasaporte());
            edit(oldPassenger);
            return oldPassenger;
        }
    }

    private Pasajero exists(String dniNIE) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pasajero> cq = cb.createQuery(Pasajero.class);
        Root<Pasajero> passengerRoot = cq.from(Pasajero.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(passengerRoot.get("dniNIE"), dniNIE));

        cq.where(predicates.toArray(new Predicate[0]));

        List<Pasajero> result = em.createQuery(cq).getResultList();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    private void nifNieOk(String dniNie) throws CreatePassengerException {
        boolean dniOk = dniPattern.matcher(dniNie).find();
        boolean nieOk = niePattern.matcher(dniNie).find();
        char[] assignedLetters = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        if (dniOk) {
            int number = Integer.parseInt(dniNie.substring(0, dniNie.length() - 1));
            char letter = dniNie.charAt(8);

            if (letter != assignedLetters[number % 23]) {
                throw new CreatePassengerException("El DNI introducido es incorrecto");
            }
        }

        if (nieOk) {
            char firstLetter = dniNie.charAt(0);
            char lastLetter = dniNie.charAt(8);

            char[] firstAssignedLetters = {'X', 'Y', 'Z'};

            boolean firstLetterOk = false;

            for (char letter : firstAssignedLetters) {
                if (firstLetter == letter) {
                    firstLetterOk = true;
                    break;
                }
            }

            if (!firstLetterOk) {
                throw new CreatePassengerException("El NIF introducido es incorrecto");
            }

            String firstLetterNumber = "0";
            if (firstLetter == 'Y') firstLetterNumber = "1";
            if (firstLetter == 'Z') firstLetterNumber = "2";

            int number = Integer.parseInt(firstLetterNumber + dniNie.substring(1).substring(0, dniNie.length() - 2));

            if (lastLetter != assignedLetters[number % 23]) {
                throw new CreatePassengerException("El NIE introducido es incorrecto");
            }
        }

        if (!dniOk && !nieOk) {
            throw new CreatePassengerException("El DNI/NIE es incorrecto");
        }
    }
}
