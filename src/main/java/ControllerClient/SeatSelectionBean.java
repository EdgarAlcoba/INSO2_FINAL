/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.VueloFacadeLocal;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Asiento;
import modelo.Vuelo;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class SeatSelectionBean {

    private String section;

    private ArrayList<ArrayList<Pair<Asiento, Integer >>> seatMap;

    private ArrayList<Asiento> selectedSeats = new ArrayList<>();
    
    private int people;

    private int rows;

    @ManagedProperty(value = "#{foundFlightsBean}")
    private FoundFlightsBean FFB;

    @ManagedProperty(value = "#{classSelectionBean}")
    private ClassSelectionBean CSB;

    @ManagedProperty(value = "#{clientHomeBean}")
    private ClientHomeBean CHB;

    @EJB
    private VueloFacadeLocal VFL;

    @PostConstruct
    public void init() {
        Vuelo flight = this.FFB.getSelectedFlight();
        this.section = this.CSB.getClassType();
        this.rows = this.VFL.getSeatMatrixColumns(flight, section);
        this.seatMap = this.VFL.getSeatLists(flight, section);
        this.people = this. FFB.getFSB().getNumberOfPassengers();
    }

    public FoundFlightsBean getFFB() {
        return FFB;
    }

    public void setFFB(FoundFlightsBean FFB) {
        this.FFB = FFB;
    }

    public ArrayList<ArrayList<Pair<Asiento, Integer>>> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(ArrayList<ArrayList<Pair<Asiento, Integer>>> seatMap) {
        this.seatMap = seatMap;
    }

    public ClassSelectionBean getCSB() {
        return CSB;
    }

    public void setCSB(ClassSelectionBean CSB) {
        this.CSB = CSB;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public ArrayList<Asiento> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(ArrayList<Asiento> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
    
    public void selectSeat(int row, int col, Asiento seat) {
            if (this.selectedSeats.contains(seat)) {
                Pair<Asiento, Integer> changeSeat = new Pair<>(seat, 0);
                this.seatMap.get(row).set(col, changeSeat);
                this.selectedSeats.remove(seat);
            } else  if(people > this.selectedSeats.size()){
                Pair<Asiento, Integer> changeSeat = new Pair<>(seat, 2);
                this.seatMap.get(row).set(col, changeSeat);
                this.selectedSeats.add(seat);
            }
             update();
    }
    
    public void update(){
        PrimeFaces.current().executeScript("updatePlane()");
    }
    
    public void selectLuggage() {
        if(this.people > this.selectedSeats.size()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Faltan asientos", "No ha elegido los suficientes asientos"));
            update();
        }else{
            this.CHB.setCurrentView("luggageSelection.xhtml");
            this.CHB.update();  
        }

    }

}
