/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.UsuarioFacadeLocal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class ModifyProfileBean {

    private Usuario user;
    private String confirmPassword;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @PostConstruct
    public void init() {
        Usuario thisUser = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        setUser(thisUser);
    }

    @EJB
    private UsuarioFacadeLocal UFL;

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

    public void saveData() {
        if (this.user.getContrasena() != null && this.confirmPassword != null) {
            if (!checkPassword()) {
                showAlert(2, "Error en la contraseña", "Las contraseñas introducidas no coinciden");
            } else if (!isValidEmail()) {
                showAlert(2, "Error en el correo", "El correo introducido no es un correo valido");
            } else {
                this.UFL.edit(this.user);
            }
        } else if (!isValidEmail()) {
            showAlert(2, "Error en el correo", "El correo introducido no es un correo valido");
        } else {
            this.UFL.edit(this.user);
        }
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
        switch (severity) {
            case 2:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, name, text));
                break;
            case 3:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, name, text));
                break;
        }
    }

    private boolean checkPassword() {
        return this.user.getContrasena().equals(this.confirmPassword);
    }

}
