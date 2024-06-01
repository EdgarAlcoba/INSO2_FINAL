package modelo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

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

    @OneToMany(mappedBy = "billete", cascade = CascadeType.PERSIST)
    private List<Maleta> maletas;

    public Billete() {
        super();
        maletas = new ArrayList<>();
    }

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

    public void addMaleta(Maleta maleta) {
        this.maletas.add(maleta);
    }

    public void addMaletas(ArrayList<Maleta> maletas, Billete billete) {
        for (Maleta maleta : maletas) {
            maleta.setBillete(billete);
            this.maletas.add(maleta);
        }
    }

    public String getPdfName() {
        return "Boarding_Pass_" + id;
    }

    public String generatePDF() {
        String airlineName = "SkySurfers";
        String pdfPath = getPdfName();

        try(PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                int y = 710;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText(airlineName);
                contentStream.endText();

                y -= 30;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Precio: " + precio.setScale(2, RoundingMode.HALF_UP).toString() + "€");
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Número de vuelo: " + vuelo.getNumero());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Origen: " + vuelo.getOrigen());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Destino: " + vuelo.getDestino());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Pasajero: " + pasajero.getNombre() + " " + pasajero.getApellido1() + " " + pasajero.getApellido2());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Modelo de avión: " + vuelo.getAvion().getModelo());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Asiento: " + asiento.getNumero());
                contentStream.endText();

                y -= 20;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Class: " + asiento.getSeccion().getClase());
                contentStream.endText();

                if (maletas != null) {
                    if (!maletas.isEmpty()) {
                        int maletas12 = 0;
                        int maletas20 = 0;
                        int maletas25 = 0;

                        for (Maleta maleta: maletas) {
                            if (maleta.getPesoKg() <= 12) {
                                maletas12++;
                                continue;
                            }
                            if (maleta.getPesoKg() <= 20) {
                                maletas20++;
                                continue;
                            }
                            if (maleta.getPesoKg() <= 25) {
                               maletas25++;
                            }
                        }

                        y -= 20;
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(100, y);
                        contentStream.showText("Maleta facturada (12kg): " + maletas12 + " unidades");
                        contentStream.endText();

                        y -= 20;
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(100, y);
                        contentStream.showText("Maleta mediana (20kg): " + maletas20 + " unidades");
                        contentStream.endText();

                        y -= 20;
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(100, y);
                        contentStream.showText("Maleta facturada (25kg): " + maletas25 + " unidades");
                        contentStream.endText();
                    }
                }


                y -= 220;

                BufferedImage qrCode = generateQRCodeImage(String.valueOf(id), 200, 200);
                PDImageXObject qrCodeImage = PDImageXObject.createFromByteArray(document, imageToByteArray(qrCode), "qrcode");
                contentStream.drawImage(qrCodeImage, 100, y);
            }

            document.save(pdfPath);

            return pdfPath;
        } catch (Exception e) {
            System.out.println("Could not generate PDF for billete " + id + ": " + e.getMessage());
            return "";
        }
    }

    private BufferedImage generateQRCodeImage(String text, int width, int height) throws IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            throw new IOException("Could not generate QR code", e);
        }
    }

    private byte[] imageToByteArray(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billete billete = (Billete) o;
        return id == billete.id && Objects.equals(fechaCompra, billete.fechaCompra) && Objects.equals(usuario, billete.usuario) && Objects.equals(precio, billete.precio) && Objects.equals(vuelo, billete.vuelo) && Objects.equals(pasajero, billete.pasajero) && Objects.equals(asiento, billete.asiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaCompra, usuario, precio, vuelo, pasajero, asiento);
    }
}
