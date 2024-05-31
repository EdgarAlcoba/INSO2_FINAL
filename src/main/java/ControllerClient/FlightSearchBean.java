/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author extre
 */
@ManagedBean
@SessionScoped
public class FlightSearchBean {
    private String origin;
    private String destination;
    private String tipoBillete;
    
    private Date range;
    
    @ManagedProperty (value = "#{clientHomeBean}")
    private ClientHomeBean CHB;
    
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTipoBillete() {
        return tipoBillete;
    }

    public void setTipoBillete(String tipoBillete) {
        this.tipoBillete = tipoBillete;
    }

    public Date getRange() {
        return range;
    }

    public void setRange(Date range) {
        this.range = range;
    }
    
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }
    
    public void search(){
        this.CHB.setCurrentView("foundFlights.xhtml");
        this.CHB.update();
    }
    
}
