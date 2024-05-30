package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Pasajeros")
public class Pasajero implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO_1")
    private String apellido1;

    @Column(name = "APELLIDO_2")
    private String apellido2;

    @Column(name = "DNI_NIE")
    private String dniNIE;

    @Column(name = "NUMERO_PASAPORTE")
    private String pasaporte;

    @OneToMany(mappedBy = "pasajero")
    private List<Billete> billetes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDniNIE() {
        return dniNIE;
    }

    public void setDniNIE(String dniNIE) {
        this.dniNIE = dniNIE;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public List<Billete> getBilletes() {
        return billetes;
    }

    public void setBilletes(List<Billete> billetes) {
        this.billetes = billetes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return id == pasajero.id && Objects.equals(nombre, pasajero.nombre) && Objects.equals(apellido1, pasajero.apellido1) && Objects.equals(apellido2, pasajero.apellido2) && Objects.equals(dniNIE, pasajero.dniNIE) && Objects.equals(pasaporte, pasajero.pasaporte) && Objects.equals(billetes, pasajero.billetes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido1, apellido2, dniNIE, pasaporte, billetes);
    }
}
