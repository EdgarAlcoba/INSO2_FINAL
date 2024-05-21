package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="Aeropuertos")
public class Aeropuerto implements Serializable {
    @Id
    @Column(name = "ICAO")
    private int icao;

    @Column(name = "IATA")
    private String iata;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "MAX_CATEGORIA")
    private char maxCategoria;

    @Column(name = "PRECIO_COMBUSTIBLE_KG")
    private BigDecimal precioCombustibleKg;

    public int getIcao() {
        return icao;
    }

    public void setIcao(int icao) {
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

    public char getMaxCategoria() {
        return maxCategoria;
    }

    public void setMaxCategoria(char maxCategoria) {
        this.maxCategoria = maxCategoria;
    }

    public BigDecimal getPrecioCombustibleKg() {
        return precioCombustibleKg;
    }

    public void setPrecioCombustibleKg(BigDecimal precioCombustibleKg) {
        this.precioCombustibleKg = precioCombustibleKg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aeropuerto that = (Aeropuerto) o;
        return icao == that.icao && maxCategoria == that.maxCategoria && Objects.equals(iata, that.iata) && Objects.equals(nombre, that.nombre) && Objects.equals(precioCombustibleKg, that.precioCombustibleKg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icao, iata, nombre, maxCategoria, precioCombustibleKg);
    }
}
