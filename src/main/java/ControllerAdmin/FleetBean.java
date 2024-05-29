/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerAdmin;

import EJB.AvionFacadeLocal;
import EJB.MapaAsientosFacadeLocal;
import es.unileon.inso2.aerolinea.exceptions.CreateAirplaneException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Avion;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class FleetBean implements Serializable {

    private Avion plane = new Avion();
    private Avion selectedPlane;

    private String distribution;
    private String distributionModify;

    private ArrayList<Avion> fleet;

    @EJB
    private AvionFacadeLocal AFL;

    @EJB
    private MapaAsientosFacadeLocal MAFL;

    @PostConstruct
    public void init() {
        this.fleet = new ArrayList<>(this.AFL.findAll());
    }

    public Avion getPlane() {
        return plane;
    }

    public void setPlane(Avion plane) {
        this.plane = plane;
    }

    public ArrayList<Avion> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Avion> fleet) {
        this.fleet = fleet;
    }

    public Avion getSelectedPlane() {
        return selectedPlane;
    }

    public void setSelectedPlane(Avion selectedPlane) {
        this.selectedPlane = selectedPlane;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getDistributionModify() {
        return distributionModify;
    }

    public void setDistributionModify(String distributionModify) {
        this.distributionModify = distributionModify;
    }

    public void createPlane() {
        this.plane.setMapaAsientos(this.MAFL.getMapaAsientos(distribution));
        try{
            this.AFL.createAvion(plane);
        }catch(CreateAirplaneException e){
            System.out.println(e.getMessage());
        }
        this.update();
    }

    public void modifyPlane() {
        this.selectedPlane.setMapaAsientos(this.MAFL.getMapaAsientos(distributionModify));
        this.AFL.edit(selectedPlane);
        this.update();
    }

    private void update() {
        this.fleet = new ArrayList<>(this.AFL.findAll());
        PrimeFaces.current().executeScript("updateTable()");
    }

    public void viewPlaneDialog() {

    }
}
