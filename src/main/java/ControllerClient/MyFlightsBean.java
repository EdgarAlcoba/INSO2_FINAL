/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerClient;

import EJB.BilleteFacadeLocal;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import modelo.Billete;
import modelo.Maleta;
import org.primefaces.PrimeFaces;

/**
 *
 * @author extre
 */
@ManagedBean
@ViewScoped
public class MyFlightsBean {

    private ArrayList<Billete> foundTickets;

    private Date dateTimeFrom;
    private Date dateTimeTo;

    private Billete selectedTicket;

    @EJB
    private BilleteFacadeLocal BFL;

    public Date getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Date dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Date getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(Date dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public ArrayList<Billete> getFoundTickets() {
        return foundTickets;
    }

    public void setFoundTickets(ArrayList<Billete> foundTickets) {
        this.foundTickets = foundTickets;
    }

    public Billete getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Billete selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public void searchBtn() {
        this.foundTickets = BFL.searchBetween(dateTimeFrom, dateTimeTo);
        update();
    }

    public int getNumMaletas(Float peso) {
        int num = 0;
        for (Maleta maleta : this.selectedTicket.getMaletas()) {
            if (maleta.getPesoKg() == peso) {
                num++;
            }
        }
        return num;
    }

    public void update() {
        PrimeFaces.current().executeScript("updateTable()");
    }

    public void viewFlightDialog() {

    }

    public void downloadPDF() throws Exception {
        try {
            Billete ticket = this.selectedTicket;

            String pdfPath = ticket.generatePDF();
            String pdfName = ticket.getPdfName();

            if (pdfPath.isEmpty()) {
                System.out.println("No se puede generar el pdf");
                return;
            }
            
            File pdfFile = new File(pdfPath);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + pdfName + ".pdf");
            response.setContentLength((int) pdfFile.length());
            response.setContentType("application/pdf");

            try (FileInputStream fileInputStream = new FileInputStream(pdfFile);
                    OutputStream responseOutputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    responseOutputStream.write(buffer, 0, bytesRead);
                }
                responseOutputStream.flush();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                facesContext.responseComplete();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
