/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerManager;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Vuelo;
import EJB.VueloFacadeLocal;
import java.util.Date;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class ManagerHomeBean {
    
    private Date dateTimeFrom ;
    private Date dateTimeTo ;
    
    private ArrayList<Vuelo> foundFlights;
    private Vuelo selectedFlight;
    private Vuelo newFlight;
    
    @EJB
    private VueloFacadeLocal VFL;

    public ArrayList<Vuelo> getFoundFlights() {
        return foundFlights;
    }

    public void setFoundFlights(ArrayList<Vuelo> foundFlights) {
        this.foundFlights = foundFlights;
    }

    public Vuelo getNewFlight() {
        return newFlight;
    }

    public void setNewFlight(Vuelo newFlight) {
        this.newFlight = newFlight;
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
    
    
    public void createFlight(){
        
        this.searchBtn();
    }
    
    public void searchBtn(){
        this.foundFlights = this.VFL.searchBetween(dateTimeFrom, dateTimeTo);
        PrimeFaces.current().executeScript("updateTable()");
    }
    
    public void modifyFlight(){
        System.out.println("LLega a modificar");
        this.VFL.edit(selectedFlight);
        this.searchBtn();
    }
    
    public void viewFlightDialog(){
    }
}
