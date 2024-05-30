package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

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

    @JoinColumn(name = "ASIENTO")
    @ManyToOne
    private Asiento asiento;

    @OneToMany(mappedBy = "billete")
    private List<Maleta> maletas;

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

    public int getNumeroMaletas() {
        return maletas.size();
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public List<Maleta> getMaletas() {
        return maletas;
    }

    public void setMaletas(List<Maleta> maletas) {
        this.maletas = maletas;
    }

    public BigDecimal getPrecioTotal() {
        if (vuelo == null) {
            throw new IllegalArgumentException("No se puede obtener el precio total de un billete sin tener un vuelo asociado");
        }

        ArrayList<BigDecimal> prices = vuelo.getPrecios();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;

        if (vuelo.getAvion().getMapaAsientos().getDistribucion().equals("Economy")) {
            if (prices.isEmpty()) {
                throw new IllegalArgumentException("Numero de precios < 1 en economy");
            }
            basePrice = prices.get(0);
        }

        if (vuelo.getAvion().getMapaAsientos().getDistribucion().equals("Normal")) {
            if (prices.size() < 2) {
                throw new IllegalArgumentException("Numero de precios < 2 en normal");
            }
            basePrice = prices.get(1);
        }

        if (vuelo.getAvion().getMapaAsientos().getDistribucion().equals("Premium")) {
            if (prices.size() < 3) {
                throw new IllegalArgumentException("Numero de precios < 3 en premium");
            }
            basePrice = prices.get(2);
        }

        BigDecimal bagsPrice = getPrecioTotalMaletas();

        totalPrice = totalPrice.add(basePrice);
        totalPrice = totalPrice.add(bagsPrice);

        return totalPrice;
    }

    public HashMap<Maleta,BigDecimal> getPrecioMaletas() {
        if (vuelo == null) {
            throw new IllegalArgumentException("No se puede obtener precios de maletas si no hay un vuelo asociado");
        }

        HashMap<Maleta,BigDecimal> bags = new HashMap<>();

        BigDecimal kgCost = vuelo.getPrecioMaleta();
        BigDecimal totalBagsCost = new BigDecimal(0);
        for (Maleta maleta : maletas) {
            bags.put(maleta, BigDecimal.valueOf(maleta.getPesoKg()).multiply(kgCost));
        }

        return bags;
    }

    public BigDecimal getPrecioTotalMaletas() {
        if (vuelo == null) {
            throw new IllegalArgumentException("No se puede obtener precios de maletas si no hay un vuelo asociado");
        }

        BigDecimal totalBagsCost = BigDecimal.ZERO;

        if (maletas != null) {
            BigDecimal kgCost = vuelo.getPrecioMaleta();
            for (Maleta maleta : maletas) {
                totalBagsCost = totalBagsCost.add(BigDecimal.valueOf(maleta.getPesoKg()).multiply(kgCost));
            }
        }

        return totalBagsCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billete billete = (Billete) o;
        return id == billete.id && Objects.equals(fechaCompra, billete.fechaCompra) && Objects.equals(usuario, billete.usuario) && Objects.equals(precio, billete.precio) && Objects.equals(vuelo, billete.vuelo) && Objects.equals(pasajero, billete.pasajero) && Objects.equals(asiento, billete.asiento) && Objects.equals(maletas, billete.maletas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaCompra, usuario, precio, vuelo, pasajero, asiento, maletas);
    }
}
