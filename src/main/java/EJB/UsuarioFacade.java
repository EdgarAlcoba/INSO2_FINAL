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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import es.unileon.inso2.aerolinea.exceptions.EditUserException;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void editUsuario(Usuario usuario) throws EditUserException {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new EditUserException("El nombre del usuario no puede ser nulo o vacío");
        }

        if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty()) {
            throw new EditUserException("El apellido del usuario no puede ser nulo o vacio");
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new EditUserException("El correo del usuario no puede ser nulo o vacio");
        }

        if (emailExists(usuario.getCorreo())) {
            throw new EditUserException("El correo ya existe");
        }

        if (usuario.getFechaNacimiento() == null) {
            throw new EditUserException("La fecha de nacimiento no puede ser nula");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            throw new EditUserException("La contraseña del usuario no puede ser nula o vacía");
        }

        usuario.setContrasena(passwordUtil.hashPassword(usuario.getContrasena()));

        ArrayList<String> roles = new ArrayList<>(Arrays.asList("Admin", "Client", "Manager"));
        if (usuario.getRol() == null || !roles.contains(usuario.getRol())) {
            throw new EditUserException("El rol del usuario no existe");
        }

        edit(usuario);
    }

    private boolean emailExists(String email) {
        return false;
    }

    /**
     * Iniciar sesión
     * @param email Correo del usuario
     * @param password Contraseña del usuario en texto claro
     * @return El usuario si login ok o null en caso de no existir o que haya un error
     */
    public Usuario loginUser(String email, String password) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarioRoot = cq.from(Usuario.class);

            cq.select(usuarioRoot).where(cb.equal(usuarioRoot.get("correo"), email));

            List<Usuario> users = em.createQuery(cq).getResultList();

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

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarioRoot = cq.from(Usuario.class);

            cq.select(usuarioRoot).where(cb.equal(usuarioRoot.get("correo"), usuario.getCorreo()));

            List<Usuario> users = em.createQuery(cq).getResultList();

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

    /**
     * Buscar
     * @param by Buscar por
     * @param text Texto a buscar
     * @return Lista de usuarios encontrados
     */
    private ArrayList<Usuario> searchBy(String by, String text) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq;

            if (by.equals("email")) {
                cq = cb.createQuery(Usuario.class);
                Root<Usuario> user = cq.from(Usuario.class);
                cq.select(user).where(cb.like(user.get("correo"), "%" + text + "%"));
            } else {
                System.out.println("Search by " + by + " not supported");
                return new ArrayList<>();
            }

            return new ArrayList<>(em.createQuery(cq).getResultList());
        } catch (Exception e) {
            System.out.println("Hubo un error buscando en usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Usuario> searchByEmail(String email) {
        return searchBy("email", email);
    }
}
