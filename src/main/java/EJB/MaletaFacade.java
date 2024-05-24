/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
