/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

import es.unileon.inso2.aerolinea.exceptions.CreateTicketException;
import javafx.util.Pair;
import modelo.Billete;
import modelo.Maleta;
import modelo.Usuario;
import modelo.Vuelo;

/**
 *
 * @author Administrador
 */
@Local
public interface BilleteFacadeLocal {

    void create(Billete billete);

    void createBillete(Billete billete) throws CreateTicketException, Exception;

    void edit(Billete billete);

    void remove(Billete billete);

    Billete find(Object id);

    List<Billete> findAll();

    List<Billete> findRange(int[] range);

    int count();

    ArrayList<Billete> searchBetween(Date from, Date to);

    ArrayList<Billete> searchBetween(Date from, Date to, Usuario user);

    BigDecimal getTotalPrice(Billete ticket);

    ArrayList<Pair<Maleta, BigDecimal>> getBagsPrice(Billete ticket);

    BigDecimal getTotalBagsPrice(Billete ticket);

    ArrayList<Maleta> getBags(Billete ticket);
    
}
