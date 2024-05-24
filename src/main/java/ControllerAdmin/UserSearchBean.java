/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;
import EJB.UsuarioFacadeLocal;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean (name = "userSearchBean")
@SessionScoped
public class UserSearchBean {

    private String searchTF;
    private ArrayList<Usuario> searchResults;
    
    @EJB
    private UsuarioFacadeLocal UFL;
    
    public ArrayList<Usuario> getSearchResults() {
        return searchResults;
    }
    
    public String getSearchTF() {
        return searchTF;
    }

    public void setSearchTF(String searchTF) {
        this.searchTF = searchTF;
    }

    public void searchBtn() {
        this.searchResults = UFL.searchByEmail(searchTF);
        PrimeFaces.current().executeScript("updateList()");
    }
      
}
