/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.UsuarioFacadeLocal;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
 
    public void login(){
        Usuario login = UFL.loginUser(email, password);
        System.out.print(login);
    }
    
}
