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

import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author extre
 */
@ManagedBean
@RequestScoped
public class RegisterBean {
    private Usuario user = new Usuario();
    private String confirmPassword;
    
    private static final String EMAIL_REGEX =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @EJB
    private UsuarioFacadeLocal UFL;

    public RegisterBean() {
    }
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public void register(){
        if(!checkPassword()){
            //TODO avisar de que la contraseña esta mal
        }else if(!isValidEmail()){
            //TODO avisar ed email defectuoso
        }else{
            this.user.setRol("cliente");
            int newUser = UFL.register(user);
            switch(newUser){
                case 0:
                    //TODO mover a login
                    break;
                case 1:
                    //TODO email ya existe
                    break;
                case 2:
                    //TODO fallo registro
                    break;
            }
        }
    }
    
    private boolean checkPassword(){
        return this.user.getContrasena().equals(this.confirmPassword);
    }
    
    private boolean isValidEmail(){
        if(this.user.getCorreo() == null){
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(this.user.getCorreo());
        return matcher.matches();
    }

}
