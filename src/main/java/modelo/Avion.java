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

    @Column(name = "PRECIO_COMPRA")
    private BigDecimal precioCompra;

    @ManyToOne
    @JoinColumn(name = "MODELO")
    private ModeloAvion modelo;

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

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public ModeloAvion getModelo() {
        return modelo;
    }

    public void setModelo(ModeloAvion modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return Objects.equals(msn, avion.msn) && Objects.equals(matricula, avion.matricula) && Objects.equals(precioCompra, avion.precioCompra) && Objects.equals(modelo, avion.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msn, matricula, precioCompra, modelo);
    }
}
