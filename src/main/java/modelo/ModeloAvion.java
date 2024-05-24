package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="Tipos_Aviones")
public class ModeloAvion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ICAO")
    private String icao;

    @Column(name = "IATA")
    private String iata;

    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * En kg/h
     */
    @Column(name = "GASTO_COMBUSTIBLE")
    private double gastoCombustible;

    @Column(name = "PRECIO")
    private BigDecimal precio;

    @Column(name = "CAPACIDAD")
    private int capacidad;

    @Column(name = "HEAVY")
    private boolean heavy;

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getGastoCombustible() {
        return gastoCombustible;
    }

    public void setGastoCombustible(double gastoCombustible) {
        this.gastoCombustible = gastoCombustible;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isHeavy() {
        return heavy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloAvion that = (ModeloAvion) o;
        return Double.compare(gastoCombustible, that.gastoCombustible) == 0 && capacidad == that.capacidad && heavy == that.heavy && Objects.equals(icao, that.icao) && Objects.equals(iata, that.iata) && Objects.equals(nombre, that.nombre) && Objects.equals(precio, that.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icao, iata, nombre, gastoCombustible, precio, capacidad, heavy);
    }
}
