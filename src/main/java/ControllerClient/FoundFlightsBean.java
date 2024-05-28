/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Vuelo;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class FoundFlightsBean {
    
   private ArrayList<Vuelo> foundFlights;
   
   private Vuelo selectedFlight;

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
    
    
   
   
    
}
