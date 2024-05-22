/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author extre
 */
@ManagedBean
@RequestScoped
public class AdminHomeBean {
    private String currentView = "" ;

    public String getCurrentView() {
        return currentView;
    }

    
    public void setView(String view){
        switch(view){
            case "fleet":
                currentView = "fleet.xhtml";
                break;
            case "userSearch":
                currentView = ("userSearch.xhtml");
                break;
            case "salesHistory":
                currentView = ("salesHistory.xhtml");
                break;
        }
    }
    
}
