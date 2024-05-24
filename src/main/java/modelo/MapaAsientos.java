package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Mapa_Asientos")
public class MapaAsientos implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "SECCION_1")
    private Seccion seccion1;

    @ManyToOne
    @JoinColumn(name = "SECCION_2")
    private Seccion seccion2;

    @ManyToOne
    @JoinColumn(name = "SECCION_3")
    private Seccion seccion3;

    @ManyToOne
    @JoinColumn(name = "TIPO_AVION")
    private TipoAvion tipoAvion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seccion getSeccion1() {
        return seccion1;
    }

    public void setSeccion1(Seccion seccion1) {
        this.seccion1 = seccion1;
    }

    public Seccion getSeccion2() {
        return seccion2;
    }

    public void setSeccion2(Seccion seccion2) {
        this.seccion2 = seccion2;
    }

    public Seccion getSeccion3() {
        return seccion3;
    }

    public void setSeccion3(Seccion seccion3) {
        this.seccion3 = seccion3;
    }

    public TipoAvion getTipoAvion() {
        return tipoAvion;
    }

    public void setTipoAvion(TipoAvion tipoAvion) {
        this.tipoAvion = tipoAvion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapaAsientos that = (MapaAsientos) o;
        return id == that.id && Objects.equals(seccion1, that.seccion1) && Objects.equals(seccion2, that.seccion2) && Objects.equals(seccion3, that.seccion3) && Objects.equals(tipoAvion, that.tipoAvion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seccion1, seccion2, seccion3, tipoAvion);
    }
}
