/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import modelo.Billete;

import java.util.Date;

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

    public void createBillete(Billete billete) {
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

        // Calculate flight price

        em.persist(billete);
    }
}
