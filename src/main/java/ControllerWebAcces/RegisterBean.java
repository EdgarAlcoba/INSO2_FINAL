/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerWebAcces;

import EJB.UsuarioFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Usuario;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author extre
 */
@ManagedBean
@RequestScoped
public class RegisterBean {

    private Usuario user = new Usuario();
    private String confirmPassword;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
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

    public String register() {
        if (!checkPassword()) {
            //TODO avisar de que la contraseña esta mal
            showAlert(2, "Error en la contraseña","Las contraseñas introducidas no coinciden");
        } else if (!isValidEmail()) {
            showAlert(2, "Error en el correo","El correo introducido no es un correo valido");
        } else {
            this.user.setRol("Client");
            int newUser = UFL.register(user);
            switch (newUser) {
                case 0:
                    return "login";
                case 1:
                    showAlert(2, "Error al registrar el usuario", "Ya existe un ususario con este correo");
                    break;
                case 2:
                    showAlert(3, "Error al registrar el usuario", "Ha ocurrido un error fatal al registar al usuario, por favor, espere un minuto y vuelva a intentarlo");
                    break;
            }
        }
        return "";
    }

    private boolean checkPassword() {
        return this.user.getContrasena().equals(this.confirmPassword);
    }

    private boolean isValidEmail() {
        if (this.user.getCorreo() == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(this.user.getCorreo());
        return matcher.matches();
    }

    private void showAlert(int severity, String name, String text) {
        System.out.println(">Llega aqui");
        FacesContext context = FacesContext.getCurrentInstance();
        switch (severity){
            case 2:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, name, text));
                break;
            case 3:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, name, text));
                break;
        }
        
    }

}
