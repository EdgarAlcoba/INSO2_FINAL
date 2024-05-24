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
    
}
