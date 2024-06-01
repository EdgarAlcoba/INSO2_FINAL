/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.BilleteFacadeLocal;
import EJB.VueloFacadeLocal;
import es.unileon.inso2.aerolinea.exceptions.CreateTicketException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import modelo.Asiento;
import modelo.Billete;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class OrderPreviewBean {

    private ArrayList<Billete> order = new ArrayList<>();

    private Billete selectedOrder;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    @ManagedProperty(value = "#{clientHomeBean}")
    private ClientHomeBean CHB;

    @ManagedProperty(value = "#{luggageSelectionBean}")
    private LuggageSelectionBean LSB;

    @ManagedProperty(value = "#{passengerDataBean}")
    private PassengerDataBean PDB;

    @ManagedProperty(value = "#{seatSelectionBean}")
    private SeatSelectionBean SSB;

    @ManagedProperty(value = "#{foundFlightsBean}")
    private FoundFlightsBean FFB;

    @EJB
    private BilleteFacadeLocal BFL;

    @EJB
    private VueloFacadeLocal VFL;

    @PostConstruct
    public void init() {
        int tam = this.FFB.getFSB().getNumberOfPassengers();
        for (int i = 0; i < tam; i++) {
            Billete newBillete = new Billete();
            newBillete.setUsuario(this.CHB.getUser());
            newBillete.setVuelo(this.FFB.getSelectedFlight());
            newBillete.setPasajero(this.PDB.getPasajeros().get(i));
            newBillete.setAsiento(this.SSB.getSelectedSeats().get(i));
            if (i == 0) {
                newBillete.addMaletas(this.LSB.getLuggage(), newBillete);
            }
            newBillete.setPrecio(this.BFL.getTotalPrice(newBillete));
            this.totalPrice.add(newBillete.getPrecio());
            this.order.add(newBillete);
        }
        PrimeFaces.current().executeScript("updateOrder()");
    }

    public ArrayList<Billete> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Billete> order) {
        this.order = order;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public LuggageSelectionBean getLSB() {
        return LSB;
    }

    public void setLSB(LuggageSelectionBean LSB) {
        this.LSB = LSB;
    }

    public PassengerDataBean getPDB() {
        return PDB;
    }

    public void setPDB(PassengerDataBean PDB) {
        this.PDB = PDB;
    }

    public SeatSelectionBean getSSB() {
        return SSB;
    }

    public void setSSB(SeatSelectionBean SSB) {
        this.SSB = SSB;
    }

    public FoundFlightsBean getFFB() {
        return FFB;
    }

    public void setFFB(FoundFlightsBean FFB) {
        this.FFB = FFB;
    }

    public Billete getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Billete selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void pay() {
        for (int i = 0; i < order.size(); i++) {
            try {
                Billete billete = order.get(i);
                Asiento seat = this.VFL.bookSeat(billete.getVuelo(), billete.getAsiento().getSeccion().getClase(), billete.getAsiento());
                billete.setAsiento(seat);
                this.BFL.createBillete(billete);
            }
            catch(Exception e)
            {
                System.out.println("Error al crear un billete: " + e.getMessage());
                return;
            }
        }
        goToMenu();
    }

    public void goToMenu() {
        this.CHB.setCurrentView("");
        this.CHB.update();
    }
}
