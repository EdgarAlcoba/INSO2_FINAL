/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean 
@SessionScoped
public class ClientHomeBean {
    private String currentView = "";
    
    private Usuario user;
    
    @PostConstruct
    public void init(){
        this.user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void update(){
        PrimeFaces.current().executeScript("updateView()");
    }
    
    public void setView(String view){
        switch(view){
            case "flightSearch":
                currentView = "flightSearch.xhtml";
                break;
            case "miFlights":
                currentView = "myFlights.xhtml";
                break;
            case "miProfile":
                currentView = "modifyProfile.xhtml";
                break;
        }
        update();
    }
    
}
