/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreateBagException;
import modelo.Maleta;

/**
 *
 * @author Administrador
 */
@Local
public interface MaletaFacadeLocal {

    void create(Maleta maleta);

    /**
     * Crea una maleta
     * @param maleta Maleta a crear
     * @throws CreateBagException Cuando hay errores creando una maleta. Este error debe mostrarse al usuario.
     */
    void createMaleta(Maleta maleta) throws CreateBagException;

    void edit(Maleta maleta);

    void remove(Maleta maleta);

    Maleta find(Object id);

    List<Maleta> findAll();

    List<Maleta> findRange(int[] range);

    int count();
    
}
