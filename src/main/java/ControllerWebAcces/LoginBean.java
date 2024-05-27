/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerWebAcces;

import EJB.UsuarioFacadeLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;

/**
 *
 * @author extre
 */
@ManagedBean
@RequestScoped
public class LoginBean {
    private String email;
    private String password ;
    private Usuario user;
    
    @EJB
    private UsuarioFacadeLocal UFL;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
    public String login(){
        this.user = UFL.loginUser(email, password);
        if(this.user == null){
            showAlert(" Error al iniciar sesión:", "El usuario o la contraseña introducidos no son válidos");
            return "";
        }else{
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this.user);
            switch (this.user.getRol()){
                case "Client":
                    return "/clientPages/home";
                case "Manager":
                    return "/managerPages/home";
                case "Admin" :
                    return "/administratorPages/home";
                }
        }   
        return null;
    }
    
     private void showAlert(String name, String text) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, name, text));
        }
        
    }
