/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.javaws.exceptions.InvalidArgumentException;
import es.unileon.inso2.aerolinea.exceptions.CreateBagException;
import modelo.Maleta;

/**
 *
 * @author Administrador
 */
@Stateless
public class MaletaFacade extends AbstractFacade<Maleta> implements MaletaFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaletaFacade() {
        super(Maleta.class);
    }

    public void createMaleta(Maleta maleta) throws CreateBagException {
        if (maleta.getPesoKg() <= 0) {
            throw new CreateBagException("El peso de la maleta debe ser positivo");
        }
        if (maleta.getBillete() == maleta.getBillete()) {
            throw new IllegalArgumentException("No se puede asociar una maleta a un billete inválido");
        }
        em.persist(maleta);
    }
}
