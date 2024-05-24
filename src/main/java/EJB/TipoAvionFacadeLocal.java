/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.TipoAvion;

/**
 *
 * @author Administrador
 */
@Local
public interface TipoAvionFacadeLocal {

    void create(TipoAvion tipoAvion);

    void edit(TipoAvion tipoAvion);

    void remove(TipoAvion tipoAvion);

    TipoAvion find(Object id);

    List<TipoAvion> findAll();

    List<TipoAvion> findRange(int[] range);

    int count();
    
}
