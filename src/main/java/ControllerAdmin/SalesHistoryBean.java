/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

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
public class SalesHistoryBean {

    private Date DateTimeFrom;
    private Date DateTimeTo;

    private ArrayList<Billete> foundTickets;

    private Billete selectedTicket;

    @EJB
    private BilleteFacadeLocal BFL;

    public Date getDateTimeFrom() {
        return DateTimeFrom;
    }

    public void setDateTimeFrom(Date DateTimeFrom) {
        this.DateTimeFrom = DateTimeFrom;
    }

    public Date getDateTimeTo() {
        return DateTimeTo;
    }

    public void setDateTimeTo(Date DateTimeTo) {
        this.DateTimeTo = DateTimeTo;
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

    public void searchBtn() {
        System.out.println("Entra en buscar");
        this.foundTickets = this.BFL.searchBetween(DateTimeFrom, DateTimeTo);
        update();
    }

    private void update() {
        PrimeFaces.current().executeScript("updateTable()");
    }
}
