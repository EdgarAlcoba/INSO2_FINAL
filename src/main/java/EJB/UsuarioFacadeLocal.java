/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.EditUserException;
import modelo.Usuario;

/**
 *
 * @author v2510
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void editUsuario(Usuario usuario, String oldEmail, String oldPassword) throws EditUserException;

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();

    public Usuario loginUser(String email, String password);

    public int register(Usuario user);

    /**
     * Buscar usuarios por email
     *
     * @param email Texto a buscar
     * @return Lista de usuarios encontrados
     */
    public ArrayList<Usuario> searchByEmail(String email);
    
}
