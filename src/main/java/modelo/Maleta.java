package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Maletas")
public class Maleta implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "PESO_KG")
    private float pesoKg;

    @ManyToOne
    @JoinColumn(name = "BILLETE")
    private Billete billete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Billete getBillete() {
        return billete;
    }

    public void setBillete(Billete billete) {
        this.billete = billete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maleta maleta = (Maleta) o;
        return id == maleta.id && Float.compare(pesoKg, maleta.pesoKg) == 0 && Objects.equals(billete, maleta.billete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pesoKg, billete);
    }
}
