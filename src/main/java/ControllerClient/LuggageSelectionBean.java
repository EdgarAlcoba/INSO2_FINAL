/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import EJB.BilleteFacadeLocal;

import javafx.util.Pair;
import modelo.Billete;
import modelo.Maleta;
import modelo.Vuelo;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class LuggageSelectionBean {
    private int light;
    private int normal;
    private int heavy;

    private BigDecimal bag12kgPrice = BigDecimal.ZERO;
    private BigDecimal bag20kgPrice = BigDecimal.ZERO;
    private BigDecimal bag25kgPrice = BigDecimal.ZERO;

    private Vuelo vuelo;
    
    @ManagedProperty (value ="#{clientHomeBean}")
    private ClientHomeBean CHB;

    @ManagedProperty(value = "#{classSelectionBean}")
    private ClassSelectionBean classSelectionBean;

    @EJB
    private BilleteFacadeLocal billeteFacadeLocal;
    
    private ArrayList<Maleta> luggage = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.vuelo = classSelectionBean.getSelectedFlight();
        Billete mockBillete = new Billete();
        mockBillete.setVuelo(vuelo);
        mockBillete.setUsuario(CHB.getUser());

        Maleta maleta12 = new Maleta();
        maleta12.setPesoKg(12);

        Maleta maleta20 = new Maleta();
        maleta20.setPesoKg(20);

        Maleta maleta25 = new Maleta();
        maleta25.setPesoKg(25);

        mockBillete.addMaleta(maleta12);
        mockBillete.addMaleta(maleta20);
        mockBillete.addMaleta(maleta25);

        ArrayList<Pair<Maleta, BigDecimal>> bagsPrices = billeteFacadeLocal.getBagsPrice(mockBillete);

        if (!bagsPrices.isEmpty()) {
            bag12kgPrice = bagsPrices.get(0).getValue();
        }
        if (bagsPrices.size() > 1) {
            bag20kgPrice = bagsPrices.get(1).getValue();
        }
        if (bagsPrices.size() > 2) {
            bag25kgPrice = bagsPrices.get(2).getValue();
        }
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getHeavy() {
        return heavy;
    }

    public void setHeavy(int heavy) {
        this.heavy = heavy;
    }

    public ClientHomeBean getCHB() {
        return CHB;
    }

    public void setCHB(ClientHomeBean CHB) {
        this.CHB = CHB;
    }

    public ArrayList<Maleta> getLuggage() {
        return luggage;
    }

    public void setLuggage(ArrayList<Maleta> luggage) {
        this.luggage = luggage;
    }

    public ClassSelectionBean getClassSelectionBean() {
        return classSelectionBean;
    }

    public void setClassSelectionBean(ClassSelectionBean classSelectionBean) {
        this.classSelectionBean = classSelectionBean;
    }

    public BigDecimal getBag12kgPrice() {
        return bag12kgPrice;
    }

    public void setBag12kgPrice(BigDecimal bag12kgPrice) {
        this.bag12kgPrice = bag12kgPrice;
    }

    public BigDecimal getBag20kgPrice() {
        return bag20kgPrice;
    }

    public void setBag20kgPrice(BigDecimal bag20kgPrice) {
        this.bag20kgPrice = bag20kgPrice;
    }

    public BigDecimal getBag25kgPrice() {
        return bag25kgPrice;
    }

    public void setBag25kgPrice(BigDecimal bag25kgPrice) {
        this.bag25kgPrice = bag25kgPrice;
    }

    public void createLuggage(){
        for(int i = 0; i < this.light; i++){
            Maleta ligero = new Maleta();
            ligero.setPesoKg(12);
            this.luggage.add(ligero);
        }
        for(int i = 0; i < this.normal; i++){
            Maleta comun = new Maleta();
            comun.setPesoKg(20);
            this.luggage.add(comun);
        }
        for(int i = 0; i < this.heavy; i++){
            Maleta pesado = new Maleta();
            pesado.setPesoKg(25);
            this.luggage.add(pesado);
        }
    }
    
    public void passengerData(){
        this.createLuggage();
        this.CHB.setCurrentView("passengerData.xhtml");
        this.CHB.update();
    }
    
}
