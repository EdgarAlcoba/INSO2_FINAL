package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @JoinColumn(name = "USUARIO")
    @ManyToOne
    private Usuario usuario;

    @Column(name = "PRECIO")
    private BigDecimal precio;

    @JoinColumn(name = "VUELO")
    @ManyToOne
    private Vuelo vuelo;

    @JoinColumn(name = "PASAJERO")
    @ManyToOne
    private Pasajero pasajero;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billete billete = (Billete) o;
        return id == billete.id && Objects.equals(fechaCompra, billete.fechaCompra) && Objects.equals(usuario, billete.usuario) && Objects.equals(precio, billete.precio) && Objects.equals(vuelo, billete.vuelo) && Objects.equals(pasajero, billete.pasajero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaCompra, usuario, precio, vuelo, pasajero);
    }
}
