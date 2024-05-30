/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.BilleteFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Billete;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class MyFlightsBean {
    private ArrayList<Billete> foundTickets;
    
    private Date dateTimeFrom;
    private Date dateTimeTo;
    
    private Billete selectedTicket;
    
    @EJB
    private BilleteFacadeLocal BFL;

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

    public ArrayList<Billete> getFoundTickets() {
        return foundTickets;
    }

    public void setFoundTickets(ArrayList<Billete> foundTickets) {
        this.foundTickets = foundTickets;
    }

    public Billete getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Billete selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public void searchBtn(){
        this.foundTickets = BFL.searchBetween(dateTimeFrom, dateTimeTo);
        update();
    }
    
    public void update(){
        PrimeFaces.current().executeScript("updateTable()");
    }
}
