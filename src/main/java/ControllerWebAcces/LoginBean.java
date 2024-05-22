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
public class LoginBean  {
    private String email;
    private String password ;
    
    @EJB
    private UsuarioFacadeLocal UFL;

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
        Usuario login = UFL.loginUser(email, password);
        if(login == null){
            showAlert(" Error al iniciar sesión:", "El usuario o la contraseña introducidos no son válidos");
            return "";
        }else{
                    switch (login.getRol()){
            case "Client":
                //TODO logica navegacion a pagina de cliente
                break;
            case "Manager":
                //TODO logica navegacion a pagina de manager
                break;
            case "Admin" :
                return "/administratorPages/home";
            }
        }   
        //TODO provisional return 
        return null;
    }
    
     private void showAlert(String name, String text) {
        System.out.println(">Llega aqui");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, name, text));
        }
        
    }
