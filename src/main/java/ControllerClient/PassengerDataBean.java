/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Pasajero;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class PassengerDataBean {
    private Pasajero pasajero;

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
    
}
