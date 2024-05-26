package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="Flota")
public class Avion implements Serializable {
    @Id
    @Column(name = "MSN")
    private String msn;

    @Column(name = "MATRICULA")
    private String matricula;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "PRECIO_COMPRA")
    private BigDecimal precioCompra;

    @Column(name = "DISTRIBUCION")
    private String distribucion = "Economy";

    @ManyToOne
    @JoinColumn(name = "SECCION_ECONOMY")
    private Seccion seccionEconomy;

    @ManyToOne
    @JoinColumn(name = "SECCION_NORMAL")
    private Seccion seccionNormal;

    @ManyToOne
    @JoinColumn(name = "SECCION_PREMIUM")
    private Seccion seccionPremium;

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String distribucion) {
        this.distribucion = distribucion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return Objects.equals(msn, avion.msn) && Objects.equals(matricula, avion.matricula) && Objects.equals(modelo, avion.modelo) && Objects.equals(precioCompra, avion.precioCompra) && Objects.equals(distribucion, avion.distribucion) && Objects.equals(seccionEconomy, avion.seccionEconomy) && Objects.equals(seccionNormal, avion.seccionNormal) && Objects.equals(seccionPremium, avion.seccionPremium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msn, matricula, modelo, precioCompra, distribucion, seccionEconomy, seccionNormal, seccionPremium);
    }
}
