package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="Vuelos")
public class Vuelo implements Serializable {
    @Id
    @Column(name = "NUMERO")
    private String numero;

    @JoinColumn(name = "ORIGEN")
    @ManyToOne
    private Aeropuerto origen;

    @JoinColumn(name = "DESTINO")
    @ManyToOne
    private Aeropuerto destino;

    @Column(name = "SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    @Column(name = "LLEGADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date llegada;

    @Column(name = "GASTO_COMBUSTIBLE_KG")
    private double gastoCombustibleKg;

    @JoinColumn(name = "AVION")
    @ManyToOne
    private Avion avion;

    @Column(name = "LIMITE_KG_MALETA")
    private float limiteKgMaleta = 20;

    @Column(name = "LIMITE_NUM_MALETAS")
    private int limiteNumMaletas = 1;

    @Column(name = "PRECIO_MALETA")
    private BigDecimal precioMaleta;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Date getLlegada() {
        return llegada;
    }

    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }

    public double getGastoCombustibleKg() {
        return gastoCombustibleKg;
    }

    public void setGastoCombustibleKg(double gastoCombustibleKg) {
        this.gastoCombustibleKg = gastoCombustibleKg;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public float getLimiteKgMaleta() {
        return limiteKgMaleta;
    }

    public void setLimiteKgMaleta(float limiteKgMaleta) {
        this.limiteKgMaleta = limiteKgMaleta;
    }

    public int getLimiteNumMaletas() {
        return limiteNumMaletas;
    }

    public void setLimiteNumMaletas(int limiteNumMaletas) {
        this.limiteNumMaletas = limiteNumMaletas;
    }

    public BigDecimal getPrecioMaleta() {
        return precioMaleta;
    }

    public void setPrecioMaleta(BigDecimal precioMaleta) {
        this.precioMaleta = precioMaleta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return Double.compare(gastoCombustibleKg, vuelo.gastoCombustibleKg) == 0 && Float.compare(limiteKgMaleta, vuelo.limiteKgMaleta) == 0 && limiteNumMaletas == vuelo.limiteNumMaletas && Objects.equals(numero, vuelo.numero) && Objects.equals(origen, vuelo.origen) && Objects.equals(destino, vuelo.destino) && Objects.equals(salida, vuelo.salida) && Objects.equals(llegada, vuelo.llegada) && Objects.equals(avion, vuelo.avion) && Objects.equals(precioMaleta, vuelo.precioMaleta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, origen, destino, salida, llegada, gastoCombustibleKg, avion, limiteKgMaleta, limiteNumMaletas, precioMaleta);
    }
}
