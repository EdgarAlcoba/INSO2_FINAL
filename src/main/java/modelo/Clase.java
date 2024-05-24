package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Clases")
public class Clase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "MAX_PRECIO")
    private BigDecimal maxPrecio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMaxPrecio() {
        return maxPrecio;
    }

    public void setMaxPrecio(BigDecimal maxPrecio) {
        this.maxPrecio = maxPrecio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase clase = (Clase) o;
        return Objects.equals(id, clase.id) && Objects.equals(nombre, clase.nombre) && Objects.equals(maxPrecio, clase.maxPrecio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, maxPrecio);
    }
}
