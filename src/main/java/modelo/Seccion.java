package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Secciones")
public class Seccion implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "NUM_FILAS")
    private int numFilas;

    @Column(name = "NUM_COLUMNAS")
    private int numColumnas;

    @JoinColumn(name = "CLASE")
    @ManyToOne
    private Clase clase;

    @OneToMany
    private List<Asiento> asientos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seccion seccion = (Seccion) o;
        return id == seccion.id && numFilas == seccion.numFilas && numColumnas == seccion.numColumnas && Objects.equals(clase, seccion.clase) && Objects.equals(asientos, seccion.asientos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numFilas, numColumnas, clase, asientos);
    }
}
