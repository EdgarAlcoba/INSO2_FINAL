/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.VueloFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Vuelo;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class MyFlightsBean {
    private ArrayList<Vuelo> foundFlights;
    
    private Date dateTimeFrom;
    private Date dateTimeTo;
    
    private Vuelo selectedFlight;
    
    @EJB
    private VueloFacadeLocal VFL;

    public ArrayList<Vuelo> getFoundFlights() {
        return foundFlights;
    }

    public void setFoundFlights(ArrayList<Vuelo> foundFlights) {
        this.foundFlights = foundFlights;
    }

    public Date getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Date dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Date getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(Date dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public Vuelo getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Vuelo selectedFlight) {
        this.selectedFlight = selectedFlight;
    }
    
    public void searchBtn(){
        VFL.searchBetween(dateTimeFrom, dateTimeTo);
        PrimeFaces.current().executeScript("updateTable()");
    }
}
