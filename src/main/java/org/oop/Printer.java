package org.oop;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.print.PrintService;

public class Printer implements Printable {
    private String path;

    public Printer(String path) {
        this.path = path;
    }

    public void startPrinting() {
        try {
            PrintService[] services = PrinterJob.lookupPrintServices();
            if (services.length > 0) {
                System.out.println("Selected printer: " + services[0]);
                PrinterJob pjob = PrinterJob.getPrinterJob();
                pjob.setPrintService(services[0]);
                pjob.setPrintable(new Printer(path), new PageFormat());
                pjob.print();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setFont(new Font("Serif", Font.PLAIN, 12));

        var y = (int) pageFormat.getImageableY();
        var lineHeight = g2d.getFontMetrics().getHeight();
        var pageHeight = (int) pageFormat.getImageableHeight();
        var linesPerPage = pageHeight / lineHeight;

        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            String line;
            var lineNumber = 0;

            while ((line = br.readLine()) != null) {
                if (lineNumber >= pageIndex * linesPerPage && lineNumber < (pageIndex + 1) * linesPerPage) {
                    g2d.drawString(line, (int) pageFormat.getImageableX(), y);
                    y += lineHeight;
                }
                lineNumber++;
            }

            if (lineNumber > pageIndex * linesPerPage) {
                return Printable.PAGE_EXISTS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Printable.NO_SUCH_PAGE;
    }
}
