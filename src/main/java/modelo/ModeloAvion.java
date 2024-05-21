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
    private int icao;

    @Column(name = "IATA")
    private String iata;

    @Column(name = "CATEGORIA")
    private char categoria;

    @Column(name = "GASTO_COMBUSTIBLE_KG_H")
    private double gastoCombustibleKgH;

    @Column(name = "PRECIO")
    private BigDecimal precio;

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

    public char getCategoria() {
        return categoria;
    }

    public void setCategoria(char categoria) {
        this.categoria = categoria;
    }

    public double getGastoCombustibleKgH() {
        return gastoCombustibleKgH;
    }

    public void setGastoCombustibleKgH(double gastoCombustibleKgH) {
        this.gastoCombustibleKgH = gastoCombustibleKgH;
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
        ModeloAvion modeloAvion = (ModeloAvion) o;
        return icao == modeloAvion.icao && categoria == modeloAvion.categoria && Double.compare(gastoCombustibleKgH, modeloAvion.gastoCombustibleKgH) == 0 && Objects.equals(iata, modeloAvion.iata) && Objects.equals(precio, modeloAvion.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icao, iata, categoria, gastoCombustibleKgH, precio);
    }
}
