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

    @Column(name = "COSTE")
    private BigDecimal coste;

    @JoinColumn(name = "AVION")
    @ManyToOne
    private Avion avion;

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

    public BigDecimal getCoste() {
        return coste;
    }

    public void setCoste(BigDecimal coste) {
        this.coste = coste;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return Objects.equals(numero, vuelo.numero) && Objects.equals(origen, vuelo.origen) && Objects.equals(destino, vuelo.destino) && Objects.equals(salida, vuelo.salida) && Objects.equals(llegada, vuelo.llegada) && Objects.equals(coste, vuelo.coste) && Objects.equals(avion, vuelo.avion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, origen, destino, salida, llegada, coste, avion);
    }
}
