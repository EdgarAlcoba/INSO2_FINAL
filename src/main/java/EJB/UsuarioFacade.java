/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import modelo.Usuario;

import java.util.List;

/**
 *
 * @author v2510
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    public final int REGISTER_OK = 0;
    public final int REGISTER_FAIL_EMAIL_EXISTS = 1;
    public final int REGISTER_FAIL_GENERIC = 2;

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Inject
    private PasswordUtil passwordUtil;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Editar contraseña
     * @param user Usuario de la db
     * @param newPassword Contraseña nueva
     */
    public void editPassword(Usuario user, String newPassword) {
        String hashedNewPassword = passwordUtil.hashPassword(newPassword);
        if (!user.getContrasena().equals(hashedNewPassword)) {
            user.setContrasena(hashedNewPassword);
        }
        edit(user);
    }

    /**
     * Iniciar sesión
     * @param email Correo del usuario
     * @param password Contraseña del usuario en texto claro
     * @return El usuario si login ok o null en caso de no existir o que haya un error
     */
    public Usuario loginUser(String email, String password) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.correo = :email", Usuario.class);
            query.setParameter("email", email);

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


    /**
     * Registrar un usuario
     * @param usuario Usuario a registrar
     * @return El usuario si register ok o null en caso de error
     */
    public int register(Usuario usuario) {
        try {
            usuario.setContrasena(passwordUtil.hashPassword(usuario.getContrasena()));
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :email", Usuario.class);
            query.setParameter("email", usuario.getContrasena());

            List<Usuario> users = query.getResultList();

            if (!users.isEmpty()) {
                return REGISTER_FAIL_EMAIL_EXISTS;
            }

            create(usuario);
            return REGISTER_OK;
        } catch (Exception e) {
            System.out.println("Hubo un error creando el usuario: " + e.getMessage());
            return REGISTER_FAIL_GENERIC;
        }
    }
}
