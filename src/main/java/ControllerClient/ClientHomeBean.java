/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author extre
 */
@ManagedBean (name = "clientHomeBean")
@ViewScoped
public class ClientHomeBean {
    private String currentView = "";

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }
    
    public void setView(String view){
        System.out.println(view);
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
    }
    
}
