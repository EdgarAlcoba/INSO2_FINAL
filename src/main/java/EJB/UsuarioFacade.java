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

import java.util.List;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 *
 * @author v2510
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    private PasswordUtil passwordUtil;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Iniciar sesión
     * @param email Correo del usuario
     * @param password Contraseña del usuario en texto claro
     * @return El usuario si login ok o null en caso de no existir o que haya un error
     */
    @Override
    public Usuario login(String email, String password) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.correo = :email AND u.contrasena = :password", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            List<Usuario> users = query.getResultList();

            if (users.isEmpty()) {
                return null;
            }

            Usuario user = users.get(0);

            if (passwordUtil.checkPassword(password, user.getContrasena())) {
                return user;
            }

            return null;
        } catch (Exception e) {
            System.out.println("Hubo un error en el login: " + e.getMessage());
            return null;
        }
    }
}
