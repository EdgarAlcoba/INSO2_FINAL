/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Maleta;

/**
 *
 * @author Administrador
 */
@Local
public interface MaletaFacadeLocal {

    void create(Maleta maleta);

    void edit(Maleta maleta);

    void remove(Maleta maleta);

    Maleta find(Object id);

    List<Maleta> findAll();

    List<Maleta> findRange(int[] range);

    int count();
    
}
