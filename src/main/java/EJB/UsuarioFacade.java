/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import modelo.Usuario;

/**
 *
 * @author v2510
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public boolean login(String email, String password) {
        try {
            // Create a TypedQuery to find a Usuarios entity where CORREO = email and CONTRASENA = password
            TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.correo = :email AND u.contrasena = :password", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            // Try to get a single result. If a result is found, return true
            Usuario user = query.getSingleResult();
            return user != null;
        } catch (Exception e) {
            // If no result is found, getSingleResult() will throw an exception, and we catch it to return false
            return false;
        }
    }
}
