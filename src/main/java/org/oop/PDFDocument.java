package org.oop;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class PDFDocument {
    public PDFDocument() {

    }

    public void startDrawingPDF(String[] receiptParts, int id) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Configuration.getInstance().getFILE_PATH() + "receipt#" + id + ".pdf"));

        document.open();
        Font font = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL, BaseColor.BLACK);

        for (String part : receiptParts) {
            Paragraph paragraph = new Paragraph(part, font);
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
        }
        document.close();
    }
}

