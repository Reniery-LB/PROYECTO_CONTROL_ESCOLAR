package PDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.Alumno;
import models.Asignatura;
import models.Docente;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PDFGenerator {

	public void generarCredencialPDF(Alumno alumno, String rutaDestino) {
	  
	    Rectangle pageSize = new Rectangle(400f, 250f);
	    Document document = new Document(pageSize, 20f, 20f, 20f, 20f); 
	    
	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
	        document.open();
	        
	       
	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
	        
	    
	        try {
	            Image logo = Image.getInstance(getClass().getResource("/img/logo.png"));
	            logo.scaleToFit(60, 60);
	            logo.setAbsolutePosition(pageSize.getWidth() - 70, pageSize.getHeight() - 70);
	            document.add(logo);
	        } catch (Exception e) {
	            System.err.println("No se pudo cargar el logo: " + e.getMessage());
	        }
	        
	       
	        Paragraph title = new Paragraph("CREDENCIAL DE ESTUDIANTE", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        title.setSpacingAfter(10f);
	        document.add(title);
	        
	      
	        try {
	            Image foto = Image.getInstance(getClass().getResource("/img/img_credencial.png"));
	            foto.scaleToFit(80, 100);
	            foto.setAbsolutePosition(30, pageSize.getHeight() - 150);
	            document.add(foto);
	        } catch (Exception e) {
	            System.err.println("No se pudo cargar la foto: " + e.getMessage());
	        }
	    
	        float infoX = 120f;
	        float infoY = pageSize.getHeight() - 80f;
	        
	        Paragraph nombre = new Paragraph("Nombre Completo:", headerFont);
	        nombre.setIndentationLeft(infoX);
	        document.add(nombre);
	        
	        Paragraph nombreVal = new Paragraph(alumno.getNombre() + " " + alumno.getPrimer_apellido() + 
	                                          (alumno.getSegundo_apellido() != null ? " " + alumno.getSegundo_apellido() : ""), 
	                                          normalFont);
	        nombreVal.setIndentationLeft(infoX);
	        document.add(nombreVal);
	        
	        Paragraph control = new Paragraph("No. Control:", headerFont);
	        control.setIndentationLeft(infoX);
	        control.setSpacingBefore(5f);
	        document.add(control);
	        
	        Paragraph controlVal = new Paragraph(String.valueOf(alumno.getNo_control()), normalFont);
	        controlVal.setIndentationLeft(infoX);
	        document.add(controlVal);
	        
	        Paragraph carrera = new Paragraph("Carrera:", headerFont);
	        carrera.setIndentationLeft(infoX);
	        carrera.setSpacingBefore(5f);
	        document.add(carrera);
	        
	        Paragraph carreraVal = new Paragraph(alumno.getCarrera(), normalFont);
	        carreraVal.setIndentationLeft(infoX);
	        document.add(carreraVal);
	        
	        Paragraph grado = new Paragraph("Grado:", headerFont);
	        grado.setIndentationLeft(infoX);
	        grado.setSpacingBefore(5f);
	        document.add(grado);
	        
	        Paragraph gradoVal = new Paragraph(alumno.getGrado_alumno() + " Semestre", normalFont);
	        gradoVal.setIndentationLeft(infoX);
	        document.add(gradoVal);
	        
	        document.close();
	    } catch (DocumentException | IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void generarCredencialPDF(Docente docente, String rutaDestino) {
	    Rectangle pageSize = new Rectangle(400f, 250f);
	    Document document = new Document(pageSize, 20f, 20f, 20f, 20f);

	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
	        document.open();

	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

	        try {
	            Image logo = Image.getInstance(getClass().getResource("/img/logo.png"));
	            logo.scaleToFit(60, 60);
	            logo.setAbsolutePosition(pageSize.getWidth() - 70, pageSize.getHeight() - 70);
	            document.add(logo);
	        } catch (Exception e) {
	            System.err.println("No se pudo cargar el logo: " + e.getMessage());
	        }

	        Paragraph title = new Paragraph("CREDENCIAL DE DOCENTE", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        title.setSpacingAfter(10f);
	        document.add(title);

	        try {
	            Image foto = Image.getInstance(getClass().getResource("/img/img_credencial.png"));
	            foto.scaleToFit(80, 100);
	            foto.setAbsolutePosition(30, pageSize.getHeight() - 150);
	            document.add(foto);
	        } catch (Exception e) {
	            System.err.println("No se pudo cargar la foto: " + e.getMessage());
	        }

	        float infoX = 120f;

	        Paragraph nombre = new Paragraph("Nombre Completo:", headerFont);
	        nombre.setIndentationLeft(infoX);
	        document.add(nombre);

	        Paragraph nombreVal = new Paragraph(
	            docente.getNombre() + " " + docente.getPrimer_apellido() +
	            (docente.getSegundo_apellido() != null ? " " + docente.getSegundo_apellido() : ""), normalFont);
	        nombreVal.setIndentationLeft(infoX);
	        document.add(nombreVal);

	        Paragraph id = new Paragraph("ID Docente:", headerFont);
	        id.setIndentationLeft(infoX);
	        id.setSpacingBefore(5f);
	        document.add(id);

	        Paragraph idVal = new Paragraph(String.valueOf(docente.getIdDocente()), normalFont);
	        idVal.setIndentationLeft(infoX);
	        document.add(idVal);

	        Paragraph materia = new Paragraph("Materia:", headerFont);
	        materia.setIndentationLeft(infoX);
	        materia.setSpacingBefore(5f);
	        document.add(materia);

	        Paragraph materiaVal = new Paragraph(docente.getMateria(), normalFont);
	        materiaVal.setIndentationLeft(infoX);
	        document.add(materiaVal);

	        Paragraph telefono = new Paragraph("Teléfono:", headerFont);
	        telefono.setIndentationLeft(infoX);
	        telefono.setSpacingBefore(5f);
	        document.add(telefono);

	        Paragraph telefonoVal = new Paragraph(String.valueOf(docente.getNo_telefono()), normalFont);
	        telefonoVal.setIndentationLeft(infoX);
	        document.add(telefonoVal);
	        
	        

	        document.close();
	    } catch (DocumentException | IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    
	}
	
    public void generarAsignaturaPDF(Asignatura asignatura, String filePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        document.open();
        
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Información de Asignatura", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        Image logo = Image.getInstance(getClass().getResource("/img/logo.png"));
        logo.scaleToFit(100, 100);
        logo.setAlignment(Image.ALIGN_CENTER);
        document.add(logo);
        
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setSpacingBefore(20);
        table.setSpacingAfter(20);
        
        table.addCell(createCell("Nombre:", headerFont));
        table.addCell(createCell(asignatura.getNombre(), dataFont));
        
        
        table.addCell(createCell("Descripción:", headerFont));
        table.addCell(createCell(asignatura.getDescripcion(), dataFont));
        
        table.addCell(createCell("ID Asignatura:", headerFont));
        table.addCell(createCell(String.valueOf(asignatura.getIdAsignatura()), dataFont));
        
        document.add(table);
        
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Paragraph footer = new Paragraph("Generado el: " + new Date(), smallFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
        
        document.close();
    }
    
    
    public void generarInformacionPDF(Alumno alumno, String rutaDestino) {
        Rectangle pageSize = new Rectangle(400f, 600f);
        Document document = new Document(pageSize, 20f, 20f, 20f, 20f); 
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            try {
                Image logo = Image.getInstance(getClass().getResource("/img/logo.png"));
                logo.scaleToFit(60, 60);
                logo.setAbsolutePosition(pageSize.getWidth() - 70, pageSize.getHeight() - 70);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo: " + e.getMessage());
            }
            
            Paragraph title = new Paragraph("INFORMACIÓN DE ESTUDIANTE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            
            try {
                Image foto = Image.getInstance(getClass().getResource("/img/img_credencial.png"));
                foto.scaleToFit(80, 100);
                foto.setAbsolutePosition(30, pageSize.getHeight() - 180); 
                document.add(foto);
            } catch (Exception e) {
                System.err.println("No se pudo cargar la foto: " + e.getMessage());
            }
            
            float infoX = 120f;
            float infoY = pageSize.getHeight() - 180f; 
            
            Paragraph id = new Paragraph("ID:", headerFont);
            id.setIndentationLeft(infoX);
            document.add(id);
            
            Paragraph id1 = new Paragraph(String.valueOf(alumno.getIdAlumno()), normalFont);
            id1.setIndentationLeft(infoX);
            document.add(id1);
            
            Paragraph nombre = new Paragraph("Nombre Completo:", headerFont);
            nombre.setIndentationLeft(infoX);
            document.add(nombre);
            
            String nombreCompleto = alumno.getNombre() + " " + alumno.getPrimer_apellido();
            if (alumno.getSegundo_apellido() != null) {
                nombreCompleto += " " + alumno.getSegundo_apellido();
            }
            
            Paragraph nombreVal = new Paragraph(nombreCompleto, normalFont);
            nombreVal.setIndentationLeft(infoX);
            document.add(nombreVal);
            
            Paragraph control = new Paragraph("No. Control:", headerFont);
            control.setIndentationLeft(infoX);
            document.add(control);
            
            Paragraph controlVal = new Paragraph(String.valueOf(alumno.getNo_control()), normalFont);
            controlVal.setIndentationLeft(infoX);
            document.add(controlVal);
            
            Paragraph carrera = new Paragraph("Carrera:", headerFont);
            carrera.setIndentationLeft(infoX);
            document.add(carrera);
            
            Paragraph carreraVal = new Paragraph(alumno.getCarrera(), normalFont);
            carreraVal.setIndentationLeft(infoX);
            document.add(carreraVal);
            
            Paragraph grado = new Paragraph("Grado:", headerFont);
            grado.setIndentationLeft(infoX);
            document.add(grado);
            
            Paragraph gradoVal = new Paragraph(alumno.getGrado_alumno() + " Semestre", normalFont);
            gradoVal.setIndentationLeft(infoX);
            document.add(gradoVal);
            
            Paragraph correo = new Paragraph("Correo:", headerFont);
            correo.setIndentationLeft(infoX);
            document.add(correo);
            
            Paragraph correoAlumno = new Paragraph(alumno.getCorreo_electronico(), normalFont);
            correoAlumno.setIndentationLeft(infoX);
            document.add(correoAlumno);
            
            Paragraph noTelefono = new Paragraph("No. Teléfono:", headerFont);
            noTelefono.setIndentationLeft(infoX);
            document.add(noTelefono);
            
            Paragraph telAlumno = new Paragraph(String.valueOf(alumno.getNo_telefono()), normalFont);
            telAlumno.setIndentationLeft(infoX);
            document.add(telAlumno);
            
            Paragraph fecha = new Paragraph("Fecha Nacimiento:", headerFont);
            fecha.setIndentationLeft(infoX);
            document.add(fecha);
            
            Paragraph fecha1 = new Paragraph(String.valueOf(alumno.getFecha_nacimiento()), normalFont);
            fecha1.setIndentationLeft(infoX);
            document.add(fecha1);
            
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void generarInformacionPDF(Docente docente, String rutaDestino) {
        Rectangle pageSize = new Rectangle(400f, 600f);
        Document document = new Document(pageSize, 20f, 20f, 20f, 20f); 
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            try {
                Image logo = Image.getInstance(getClass().getResource("/img/logo.png"));
                logo.scaleToFit(60, 60);
                logo.setAbsolutePosition(pageSize.getWidth() - 70, pageSize.getHeight() - 70);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo: " + e.getMessage());
            }
            
            Paragraph title = new Paragraph("INFORMACIÓN DE DOCENTE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            
            try {
                Image foto = Image.getInstance(getClass().getResource("/img/img_credencial.png"));
                foto.scaleToFit(80, 100);
                foto.setAbsolutePosition(30, pageSize.getHeight() - 180); 
                document.add(foto);
            } catch (Exception e) {
                System.err.println("No se pudo cargar la foto: " + e.getMessage());
            }
            
            float infoX = 120f;
            float infoY = pageSize.getHeight() - 180f; 
            
            Paragraph id = new Paragraph("ID:", headerFont);
            id.setIndentationLeft(infoX);
            document.add(id);
            
            Paragraph id1 = new Paragraph(String.valueOf(docente.getIdDocente()), normalFont);
            id1.setIndentationLeft(infoX);
            document.add(id1);
            
            Paragraph nombre = new Paragraph("Nombre Completo:", headerFont);
            nombre.setIndentationLeft(infoX);
            document.add(nombre);
            
            String nombreCompleto = docente.getNombre() + " " + docente.getPrimer_apellido();
            if (docente.getSegundo_apellido() != null) {
                nombreCompleto += " " + docente.getSegundo_apellido();
            }
            
            Paragraph nombreVal = new Paragraph(nombreCompleto, normalFont);
            nombreVal.setIndentationLeft(infoX);
            document.add(nombreVal);
            
            Paragraph carrera = new Paragraph("Materia:", headerFont);
            carrera.setIndentationLeft(infoX);
            document.add(carrera);
            
            Paragraph carreraVal = new Paragraph(docente.getMateria(), normalFont);
            carreraVal.setIndentationLeft(infoX);
            document.add(carreraVal);
            
            Paragraph correo = new Paragraph("Correo:", headerFont);
            correo.setIndentationLeft(infoX);
            document.add(correo);
            
            Paragraph correoAlumno = new Paragraph(docente.getCorreo_electronico(), normalFont);
            correoAlumno.setIndentationLeft(infoX);
            document.add(correoAlumno);
            
            Paragraph noTelefono = new Paragraph("No. Teléfono:", headerFont);
            noTelefono.setIndentationLeft(infoX);
            document.add(noTelefono);
            
            Paragraph telAlumno = new Paragraph(String.valueOf(docente.getNo_telefono()), normalFont);
            telAlumno.setIndentationLeft(infoX);
            document.add(telAlumno);
            
            Paragraph fecha = new Paragraph("Fecha Nacimiento:", headerFont);
            fecha.setIndentationLeft(infoX);
            document.add(fecha);
            
            Paragraph fecha1 = new Paragraph(String.valueOf(docente.getFecha_nacimiento()), normalFont);
            fecha1.setIndentationLeft(infoX);
            document.add(fecha1);
            
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
    
    


}
