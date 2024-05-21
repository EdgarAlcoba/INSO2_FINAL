package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Billetes")
public class Billete implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FECHA_COMPRA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompra;

    @Column(name = "PRECIO")
    private String apellidos;

    @JoinColumn(name = "USUARIO")
    @ManyToOne
    private Usuario usuario;

    @JoinColumn(name = "VUELO")
    @ManyToOne
    private Vuelo vuelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billete billete = (Billete) o;
        return id == billete.id && Objects.equals(fechaCompra, billete.fechaCompra) && Objects.equals(apellidos, billete.apellidos) && Objects.equals(usuario, billete.usuario) && Objects.equals(vuelo, billete.vuelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaCompra, apellidos, usuario, vuelo);
    }
}
