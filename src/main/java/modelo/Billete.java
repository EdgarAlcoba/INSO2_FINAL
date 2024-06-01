package modelo;

import org.apache.pdfbox.pdmodel.PDDocument;
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
        return "Boarding_Pass_" + String.valueOf(id);
    }

    public String generatePDF() {
        String airlineName = "SkySurfers";
        String pdfPath = getPdfName();

        try(PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(100, 710);
                contentStream.showText(airlineName);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 690);
                contentStream.showText("Identificador del billete: " + id);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 670);
                contentStream.showText("Número de vuelo: " + vuelo.getNumero());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Origin: " + vuelo.getOrigen());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 630);
                contentStream.showText("Destination: " + vuelo.getDestino());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Passenger: " + pasajero.getNombre() + " " + pasajero.getApellido1() + " " + pasajero.getApellido2());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 590);
                contentStream.showText("Seat: " + asiento.getNumero());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 570);
                contentStream.showText("Class: " + asiento.getSeccion().getClase());
                contentStream.endText();

                BufferedImage qrCode = generateQRCodeImage(String.valueOf(id), 100, 100);
                PDImageXObject qrCodeImage = PDImageXObject.createFromByteArray(document, imageToByteArray(qrCode), "qrcode");
                contentStream.drawImage(qrCodeImage, 100, 450);
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
