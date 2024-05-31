/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author extre
 */
@ManagedBean
@SessionScoped
public class LuggageSelectionBean {
    private int light;
    private int normal;
    private int heavy;
    
    @ManagedProperty (value ="#{clientHomeBean}")
    private ClientHomeBean CHB;
    

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
    
    public void passengerData(){
        this.CHB.setCurrentView("passengerData.xhtml");
        this.CHB.update();
    }
    
    
}
