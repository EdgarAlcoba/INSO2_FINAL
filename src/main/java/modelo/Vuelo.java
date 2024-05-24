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
        return Double.compare(gastoCombustibleKg, vuelo.gastoCombustibleKg) == 0 && Objects.equals(numero, vuelo.numero) && Objects.equals(origen, vuelo.origen) && Objects.equals(destino, vuelo.destino) && Objects.equals(salida, vuelo.salida) && Objects.equals(llegada, vuelo.llegada) && Objects.equals(avion, vuelo.avion) && Objects.equals(precioMaleta, vuelo.precioMaleta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, origen, destino, salida, llegada, gastoCombustibleKg, avion, precioMaleta);
    }
}
