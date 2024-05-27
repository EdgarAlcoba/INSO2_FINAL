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

    @ManyToOne
    @JoinColumn(name = "MAPA_ASIENTOS")
    private MapaAsientos mapaAsientos;

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

    public MapaAsientos getMapaAsientos() {
        return mapaAsientos;
    }

    public void setMapaAsientos(MapaAsientos mapaAsientos) {
        this.mapaAsientos = mapaAsientos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return Objects.equals(msn, avion.msn) && Objects.equals(matricula, avion.matricula) && Objects.equals(modelo, avion.modelo) && Objects.equals(precioCompra, avion.precioCompra) && Objects.equals(mapaAsientos, avion.mapaAsientos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msn, matricula, modelo, precioCompra, mapaAsientos);
    }
}
