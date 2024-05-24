/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Usuario;
import EJB.UsuarioFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

/**
 *
 * @author extre
 */
@ManagedBean (name = "userItemBean")
@ViewScoped
public class UserItemBean {

    @ManagedProperty(value = "#{userSearchBean}")
    private UserSearchBean userSearchBean;
    
    @EJB
    private UsuarioFacadeLocal UFL;

    
    public UserSearchBean getUserSearchBean() {
        return userSearchBean;
    }

    public void setUserSearchBean(UserSearchBean userSearchBean) {
        this.userSearchBean = userSearchBean;
    }

    public void delete(Usuario user){
        this.UFL.remove(user);
        userSearchBean.searchBtn();
        FacesContext context = FacesContext.getCurrentInstance();
        PartialViewContext partialViewContext = context.getPartialViewContext();
        partialViewContext.getRenderIds().add("lista");
    }
    
    public void applyChanges(Usuario user){
        this.UFL.edit(user);
    }
}
