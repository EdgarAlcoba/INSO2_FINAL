/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.ModeloAvion;

/**
 *
 * @author Administrador
 */
@Local
public interface ModeloAvionFacadeLocal {

    void create(ModeloAvion modeloAvion);

    void edit(ModeloAvion modeloAvion);

    void remove(ModeloAvion modeloAvion);

    ModeloAvion find(Object id);

    List<ModeloAvion> findAll();

    List<ModeloAvion> findRange(int[] range);

    int count();
    
}
