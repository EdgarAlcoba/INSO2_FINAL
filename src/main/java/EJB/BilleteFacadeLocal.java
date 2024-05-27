/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Billete;

/**
 *
 * @author Administrador
 */
@Local
public interface BilleteFacadeLocal {

    void create(Billete billete);

    void createBillete(Billete billete);

    void edit(Billete billete);

    void remove(Billete billete);

    Billete find(Object id);

    List<Billete> findAll();

    List<Billete> findRange(int[] range);

    int count();
    
}
