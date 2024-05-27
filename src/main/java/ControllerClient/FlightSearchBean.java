/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class FlightSearchBean {
    private String origin;
    private String destination;
    private String tipoBillete;
    
    private Date range;
    
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
    
    
    
}
