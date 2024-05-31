package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Asientos")
public class Asiento implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "POSICION_X")
    private int posicionX;

    @Column(name = "POSICION_Y")
    private int posicionY;

    @Column(name = "TIPO")
    private String tipo = "Seat";

    @ManyToOne
    @JoinColumn(name = "SECCION")
    private Seccion seccion;

    public String getNumeroAsiento() {
        // TODO Calcular numero asiento
        return "1A";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asiento asiento = (Asiento) o;
        return id == asiento.id && posicionX == asiento.posicionX && posicionY == asiento.posicionY && Objects.equals(tipo, asiento.tipo) && Objects.equals(seccion, asiento.seccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posicionX, posicionY, tipo, seccion);
    }
}
