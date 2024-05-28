package modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name="Vuelos")
public class Vuelo implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "DESTINO")
    private String destino;

    @Column(name = "SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    @Column(name = "LLEGADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date llegada;

    @Column(name = "GASTO_COMBUSTIBLE_KG")
    private double gastoCombustibleKg;

    @JoinColumn(name = "AVION")
    @ManyToOne
    private Avion avion;

    @Column(name = "PRECIO_MALETA")
    private BigDecimal precioMaleta;

    /**
     * IMPORTANTE: Solo se puede llamar a este metodo una vez creado el vuelo en la DB
     * Obtiene los precios del vuelo
     * Si el avion tiene solo clase turista len = 1
     * Si el avion tiene clase turista + normal len = 2
     * Si el avion tiene clase turista + normal + premium len = 3
     * En caso de error len = 0
     * @return ArrayList con los precios
     */
    public ArrayList<BigDecimal> getPrecios() {
        ArrayList<BigDecimal> prices = new ArrayList<>();
        if (avion == null) return prices;
        MapaAsientos seatMap = avion.getMapaAsientos();
        if (seatMap == null) return prices;

        BigDecimal averageKgCost = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.75, 1.31));
        BigDecimal costPrice = averageKgCost.multiply(BigDecimal.valueOf(gastoCombustibleKg));

        // TODO number of seats
        int numberOfSeats = 100;

        BigDecimal costPricePerPax = costPrice.divide(BigDecimal.valueOf(numberOfSeats));

        BigDecimal minCost = costPricePerPax.multiply(BigDecimal.valueOf(0.7));

        double economyIncrease = ThreadLocalRandom.current().nextDouble(1, 1.31);
        double normalIncrease = ThreadLocalRandom.current().nextDouble(1.32, 1.91);
        double premiumIncrease = ThreadLocalRandom.current().nextDouble(2, 4.01);

        BigDecimal economyCost = minCost.multiply(BigDecimal.valueOf(economyIncrease));
        BigDecimal normalCost = minCost.multiply(BigDecimal.valueOf(normalIncrease));
        BigDecimal premiumCost = minCost.multiply(BigDecimal.valueOf(premiumIncrease));

        if (seatMap.getDistribucion().equals("Economy")) {
            prices.add(economyCost);
        }

        if (seatMap.getDistribucion().equals("Normal")) {
            prices.add(economyCost);
            prices.add(normalCost);
        }

        if (seatMap.getDistribucion().equals("Premium")) {
            prices.add(economyCost);
            prices.add(normalCost);
            prices.add(premiumCost);
        }

        return prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Date getLlegada() {
        return llegada;
    }

    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }

    public double getGastoCombustibleKg() {
        return gastoCombustibleKg;
    }

    public void setGastoCombustibleKg(double gastoCombustibleKg) {
        this.gastoCombustibleKg = gastoCombustibleKg;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public BigDecimal getPrecioMaleta() {
        return precioMaleta;
    }

    public void setPrecioMaleta(BigDecimal precioMaleta) {
        this.precioMaleta = precioMaleta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return id == vuelo.id && Double.compare(gastoCombustibleKg, vuelo.gastoCombustibleKg) == 0 && Objects.equals(numero, vuelo.numero) && Objects.equals(origen, vuelo.origen) && Objects.equals(destino, vuelo.destino) && Objects.equals(salida, vuelo.salida) && Objects.equals(llegada, vuelo.llegada) && Objects.equals(avion, vuelo.avion) && Objects.equals(precioMaleta, vuelo.precioMaleta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, origen, destino, salida, llegada, gastoCombustibleKg, avion, precioMaleta);
    }
}
