/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.unileon.inso2.aerolinea.exceptions.CreatePassengerException;
import modelo.Pasajero;

import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
@Stateless
public class PasajeroFacade extends AbstractFacade<Pasajero> implements PasajeroFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    Pattern dniNiePattern = Pattern.compile("(\\d{8}[A-HJ-NP-TV-Za-hj-np-tv-z])|([XYZxyz]\\d{7}[A-HJ-NP-TV-Za-hj-np-tv-z])", Pattern.CASE_INSENSITIVE);
    Pattern passportPattern = Pattern.compile("[A-Z0-9]*", Pattern.CASE_INSENSITIVE);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasajeroFacade() {
        super(Pasajero.class);
    }

    public void createPasajero(Pasajero pasajero) throws CreatePassengerException {
        if (pasajero.getNombre() == null || pasajero.getNombre().isEmpty()) {
            throw new CreatePassengerException("El nombre no puede ser nulo o vacío");
        }

        if (pasajero.getApellido1() == null || pasajero.getApellido1().isEmpty()) {
            throw new CreatePassengerException("El primer apellido no puede ser nulo o vacío");
        }

        if (pasajero.getDniNIE() == null || pasajero.getDniNIE().isEmpty()) {
            throw new CreatePassengerException("El DNI/NIE no puede ser nulo o vacio");
        }

        if (!dniNiePattern.matcher(pasajero.getDniNIE()).find()) {
            // TODO Validar NIF / NIE correctamente
            throw new CreatePassengerException("El DNI/NIE es incorrecto");
        }

        if (pasajero.getPasaporte() != null && !pasajero.getPasaporte().isEmpty()) {
            if (!passportPattern.matcher(pasajero.getPasaporte()).find()) {
                throw new CreatePassengerException("Número de pasaporte inválido");
            }
        }

        em.persist(pasajero);
    }
}
