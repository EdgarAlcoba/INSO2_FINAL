/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;

/**
 *
 * @author extre
 */
@ManagedBean
@SessionScoped
public class AdminHomeBean {
    private String currentView = "" ;

    public String getCurrentView() {
        return currentView;
    }
    
    @PostConstruct
    public void init(){
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        try{
            if(user == null || !"Admin".equals(user.getRol())){
                FacesContext.getCurrentInstance().getExternalContext().redirect("../webAccess/login.xhtml");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    
}
    public void setView(String view){
        switch(view){
            case "fleet":
                currentView = "fleet.xhtml";
                break;
            case "userSearch":
                currentView = "userSearch.xhtml";
                break;
            case "salesHistory":
                currentView = "salesHistory.xhtml";
                break;
        }
    }
    
}
