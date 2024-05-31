/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import modelo.Vuelo;

/**
 *
 * @author extre
 */
@ManagedBean
@SessionScoped
public class ClassSelectionBean {
    
    private String classType;
    private Vuelo vuelo;
    
    private boolean hasEco = true;
    private boolean hasNor = true;
    private boolean hasPre = true;
    
    @ManagedProperty (value = "#{clientHomeBean}")
    private ClientHomeBean CHB;
    
    @ManagedProperty (value = "#{foundFlightsBean}")
    private FoundFlightsBean FFB;
    
    @PostConstruct
    public void init(){
        this.vuelo = FFB.getSelectedFlight();
        if(vuelo.getAvion().getMapaAsientos().getSeccionEconomy() == null){
            hasEco = false;
        }
        if(vuelo.getAvion().getMapaAsientos().getSeccionNormal() == null){
            hasNor = false;
        }
        if(vuelo.getAvion().getMapaAsientos().getSeccionPremium() == null){
            hasPre = false;
        }
    }
    
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public boolean isHasEco() {
        return hasEco;
    }

    public void setHasEco(boolean hasEco) {
        this.hasEco = hasEco;
    }

    public boolean isHasNor() {
        return hasNor;
    }

    public void setHasNor(boolean hasNor) {
        this.hasNor = hasNor;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public FoundFlightsBean getFFB() {
        return FFB;
    }

    public void setFFB(FoundFlightsBean FFB) {
        this.FFB = FFB;
    }
    
    public void selectSeats(){
        this.CHB.setCurrentView("seatSelection.xhtml");
        this.CHB.update();
    }
    
}