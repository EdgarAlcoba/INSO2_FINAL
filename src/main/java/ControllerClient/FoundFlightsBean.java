/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.VueloFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Vuelo;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class FoundFlightsBean {

    private ArrayList<Vuelo> foundFlights;

    private Vuelo selectedFlight;

    @ManagedProperty(value = "#{flightSearchBean}")
    private FlightSearchBean FSB;

    @ManagedProperty(value = "#{clientHomeBean}")
    private ClientHomeBean CHB;

    @EJB
    private VueloFacadeLocal VFL;

    @PostConstruct
    public void init() {
        String paramOri = FSB.getOrigin();
        String paramDest = FSB.getDestination();
        Date paramDate = FSB.getRange();
        this.foundFlights = this.VFL.searchAllDay(paramDate, paramOri, paramDest);
    }

    public ArrayList<Vuelo> getFoundFlights() {
        return foundFlights;
    }

    public void setFoundFlights(ArrayList<Vuelo> foundFlights) {
        this.foundFlights = foundFlights;
    }

    public Vuelo getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Vuelo selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public FlightSearchBean getFSB() {
        return FSB;
    }

    public void setFSB(FlightSearchBean FSB) {
        this.FSB = FSB;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedFlight = (Vuelo) event.getObject();
    }

    public void selectClass() {
        if (this.selectedFlight != null) {
            this.CHB.setCurrentView("classSelection.xhtml");
            this.CHB.update();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al seleccionar un vuelo", "No se ha seleccionado ningun vuelo"));
        }
    }

}
