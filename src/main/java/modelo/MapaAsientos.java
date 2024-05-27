package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Mapas_asientos")
public class MapaAsientos implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "SECCION_ECONOMY")
    private Seccion seccionEconomy;

    @ManyToOne
    @JoinColumn(name = "SECCION_NORMAL")
    private Seccion seccionNormal;

    @ManyToOne
    @JoinColumn(name = "SECCION_PREMIUM")
    private Seccion seccionPremium;

    @Column(name = "DISTRIBUCION")
    private String distribucion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seccion getSeccionEconomy() {
        return seccionEconomy;
    }

    public void setSeccionEconomy(Seccion seccionEconomy) {
        this.seccionEconomy = seccionEconomy;
    }

    public Seccion getSeccionNormal() {
        return seccionNormal;
    }

    public void setSeccionNormal(Seccion seccionNormal) {
        this.seccionNormal = seccionNormal;
    }

    public Seccion getSeccionPremium() {
        return seccionPremium;
    }

    public void setSeccionPremium(Seccion seccionPremium) {
        this.seccionPremium = seccionPremium;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String distribucion) {
        this.distribucion = distribucion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapaAsientos that = (MapaAsientos) o;
        return id == that.id && Objects.equals(seccionEconomy, that.seccionEconomy) && Objects.equals(seccionNormal, that.seccionNormal) && Objects.equals(seccionPremium, that.seccionPremium) && Objects.equals(distribucion, that.distribucion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seccionEconomy, seccionNormal, seccionPremium, distribucion);
    }
}
