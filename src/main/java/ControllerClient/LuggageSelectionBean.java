/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import modelo.Maleta;

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
    
    @ManagedProperty (value ="#{clientHomeBean}")
    private ClientHomeBean CHB;
    
    
    private ArrayList<Maleta> luggage = new ArrayList<>();

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
