/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.PasajeroFacadeLocal;
import es.unileon.inso2.aerolinea.exceptions.CreatePassengerException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Pasajero;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class PassengerDataBean {

    private Pasajero pasajero;

    private ArrayList<Pasajero> pasajeros = new ArrayList<>();

    @ManagedProperty(value = "#{clientHomeBean}")
    private ClientHomeBean CHB;

    @ManagedProperty(value = "#{flightSearchBean}")
    private FlightSearchBean FSB;

    @EJB
    private PasajeroFacadeLocal PFL;

    @PostConstruct
    public void init() {
        int numPasajeros = this.FSB.getNumberOfPassengers();
        for (int i = 0; i < numPasajeros; i++) {
            pasajeros.add(new Pasajero());
        }
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(ArrayList<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public FlightSearchBean getFSB() {
        return FSB;
    }

    public void setFSB(FlightSearchBean FSB) {
        this.FSB = FSB;
    }

    public void checkPassengerData() {
        for (int i = 0; i < this.pasajeros.size(); i++) {
            try {
                Pasajero newPasajero = this.PFL.createPasajero(pasajeros.get(i));
                this.pasajeros.set(i, newPasajero);
            } catch (CreatePassengerException e) {
                showAlerts("Error en los datos del pasajero " + (i+1), e.getMessage());
                return;
            }
        }
        goToReview();
    }

    public void goToReview() {
        this.CHB.setCurrentView("orderPreview.xhtml");
        this.CHB.update();
    }
    
    public void showAlerts(String head, String text){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, head, text));
    }

}
