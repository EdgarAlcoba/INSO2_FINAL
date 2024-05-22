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
public class UserSearchBean {
    private String searchTF;

    public String getSearchTF() {
        return searchTF;
    }

    public void setSearchTF(String searchTF) {
        this.searchTF = searchTF;
    }
    
    
    
    public void SearchBtn(){
        
    }
}
