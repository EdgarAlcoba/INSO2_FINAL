package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Tipos_Aviones")
public class TipoAvion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ICAO")
    private String icao;

    @Column(name = "IATA")
    private String iata;

    /**
     * A hasta F segun tabla FAA
     */
    @Column(name = "CATEGORIA")
    private String categoria;

    /**
     * En kg/h
     */
    @Column(name = "GASTO_COMBUSTIBLE")
    private double gastoCombustible;

    @Column(name = "PRECIO")
    private BigDecimal precio;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAvion tipoAvion = (TipoAvion) o;
        return Double.compare(gastoCombustible, tipoAvion.gastoCombustible) == 0 && Objects.equals(icao, tipoAvion.icao) && Objects.equals(iata, tipoAvion.iata) && Objects.equals(categoria, tipoAvion.categoria) && Objects.equals(precio, tipoAvion.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icao, iata, categoria, gastoCombustible, precio);
    }
}
