/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerManager;

import EJB.AvionFacadeLocal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import modelo.Vuelo;
import EJB.VueloFacadeLocal;
import es.unileon.inso2.aerolinea.exceptions.CreateFlightException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Avion;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@SessionScoped
public class ManagerHomeBean {

    private Date dateTimeFrom;
    private Date dateTimeTo;

    private ArrayList<Vuelo> foundFlights;
    private Vuelo selectedFlight;
    private Vuelo newFlight = new Vuelo();

    private Avion selectedPlane;

    private Map<String, String> planes;
    private String msn;

    @EJB
    private VueloFacadeLocal VFL;

    @EJB
    private AvionFacadeLocal AFL;

    @PostConstruct
    public void update() {
        List<Avion> planeList = AFL.findAll();
        planes = planeList.stream().collect(Collectors.toMap(Avion::getMatricula, Avion::getMsn));
        PrimeFaces.current().executeScript("updateTable()");
    }

    public Avion getSelectedPlane() {
        return selectedPlane;
    }

    public void setSelectedPlane(Avion selectedPlane) {
        this.selectedPlane = selectedPlane;
    }

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

    public Map<String, String> getPlanes() {
        return planes;
    }

    public void setPlanes(Map<String, String> planes) {
        this.planes = planes;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public void createFlight() {
        try {
            this.newFlight.setAvion(this.AFL.find(this.msn));
            this.VFL.createFlight(newFlight);
        } catch (CreateFlightException e) {
            System.out.println("Llega al break");
            showAlert(e.getMessage());
        }
        this.searchBtn();
    }

    public void searchBtn() {
        this.foundFlights = this.VFL.searchBetween(dateTimeFrom, dateTimeTo);
        update();
    }

    public void modifyFlight() {
        this.VFL.edit(selectedFlight);
        update();
    }
    
     private void showAlert(String text) {
         System.out.println("LLega al metodo");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear un vuelo", text));
    }

    public void viewFlightDialog() {
    }
}
