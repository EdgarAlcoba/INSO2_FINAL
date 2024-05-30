/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.UsuarioFacadeLocal;
import es.unileon.inso2.aerolinea.exceptions.EditUserException;
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
    private String oldEmail;
    private String oldPassword;

    @PostConstruct
    public void init() {
        Usuario thisUser = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        setUser(thisUser);
        this.oldEmail = thisUser.getCorreo();
        this.oldPassword = thisUser.getContrasena();
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
            } else {
                try {
                    this.UFL.editUsuario(this.user, this.oldEmail, this.oldPassword);
                    showAlert(3, "Usuario actualizado", "El usuario ha sido modificado correctamente");
                } catch (EditUserException e) {
                    showAlert(2, "Error al actualizar el usuario", e.getMessage());
                }

            }
        } else {
            showAlert(3, "Usuario actualizado", "El usuario ha sido modificado correctamente");
            this.UFL.edit(this.user);
        }
    }

    private void showAlert(int severity, String name, String text) {
        FacesContext context = FacesContext.getCurrentInstance();
        switch (severity) {
            case 2:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, name, text));
                break;
            case 3:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, name, text));
                break;
        }
    }

    private boolean checkPassword() {
        return this.user.getContrasena().equals(this.confirmPassword);
    }

}
