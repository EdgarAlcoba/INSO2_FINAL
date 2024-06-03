/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Vuelo;
import EJB.VueloFacadeLocal;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class ClassSelectionBean {

    private String classType;
    private Vuelo vuelo;

    private boolean hasEco = true;
    private boolean hasNor = true;
    private boolean hasPre = true;

    private BigDecimal economyPrice = BigDecimal.ZERO;
    private BigDecimal normalPrice = BigDecimal.ZERO;
    private BigDecimal premiumPrice = BigDecimal.ZERO;

    @ManagedProperty(value = "#{clientHomeBean}")
    private ClientHomeBean CHB;

    @ManagedProperty(value = "#{foundFlightsBean}")
    private FoundFlightsBean FFB;

    @EJB
    private VueloFacadeLocal vueloFacadeLocal;

    @PostConstruct
    public void init() {
        this.vuelo = FFB.getSelectedFlight();
        if (vuelo.getAvion().getMapaAsientos().getSeccionEconomy() == null) {
            hasEco = false;
        }
        if (vuelo.getAvion().getMapaAsientos().getSeccionNormal() == null) {
            hasNor = false;
        }
        if (vuelo.getAvion().getMapaAsientos().getSeccionPremium() == null) {
            hasPre = false;
        }
        ArrayList<BigDecimal> prices = vueloFacadeLocal.getPrices(vuelo);
        if (!prices.isEmpty()) {
            economyPrice = prices.get(0);
        }
        if (prices.size() > 1) {
            normalPrice = prices.get(1);
        }
        if (prices.size() > 2) {
            premiumPrice = prices.get(2);
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

    public BigDecimal getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(BigDecimal economyPrice) {
        this.economyPrice = economyPrice;
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(BigDecimal normalPrice) {
        this.normalPrice = normalPrice;
    }

    public BigDecimal getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(BigDecimal premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    public Vuelo getSelectedFlight() {
        return vuelo;
    }

    public void selectSeats() {
        if (this.classType != null) {
            this.CHB.setCurrentView("seatSelection.xhtml");
            this.CHB.update();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al seleccionar una clase", "No se ha seleccionado ninguna clase de asiento"));
        }
    }

}
