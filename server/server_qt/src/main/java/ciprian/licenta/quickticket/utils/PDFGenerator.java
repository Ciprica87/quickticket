package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;
import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDFGenerator {

    public static byte[] createEventTickets(Event event) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            document.addTitle("Tickets for " + event.getName());

            PdfContentByte canvas = writer.getDirectContent();
            int ticketsPerPage = 6;
            int ticketCounter = 0;

            for (TicketTier tier : event.getTicketTiers()) {
                for (Ticket ticket : tier.getTickets()) {
                    if (ticketCounter > 0 && ticketCounter % ticketsPerPage == 0) {
                        document.newPage();
                    }

                    float yPos = 780 - (ticketCounter % ticketsPerPage) * 115;
                    addTicketToPDF(document, canvas, ticket, tier, yPos, event);
                    ticketCounter++;
                }
            }

            document.close();
        } catch (DocumentException | IOException | WriterException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    private static void addTicketToPDF(Document document, PdfContentByte canvas, Ticket ticket, TicketTier tier, float yPos, Event event) throws IOException, DocumentException, WriterException {
        byte[] qrCode = QRCodeGenerator.generateQRCodeImage(ticket.getId().toString(), 150, 150);

        Rectangle rect = new Rectangle(36, yPos - 110, 559, yPos);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        canvas.rectangle(rect);

        Image qrImage = Image.getInstance(qrCode);
        qrImage.setAbsolutePosition(50, yPos - 100);
        qrImage.scaleAbsolute(100, 100); // Resize QR code
        document.add(qrImage);

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(new Rectangle(160, yPos - 140, 550, yPos));

        Paragraph eventInfo = new Paragraph();
        eventInfo.add(new Phrase("Event: " + event.getName() + "\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        eventInfo.add(new Phrase("Location: " + event.getLocation() + "\n", new Font(Font.FontFamily.HELVETICA, 10)));
        eventInfo.add(new Phrase("Date: " + event.getDate().toString() + "\n", new Font(Font.FontFamily.HELVETICA, 10)));
        ct.addElement(eventInfo);

        ct.addElement(new Chunk(new LineSeparator()));

        Paragraph ticketInfo = new Paragraph();
        ticketInfo.add(new Phrase("Ticket ID: " + ticket.getId().toString() + "\n", new Font(Font.FontFamily.HELVETICA, 10)));
        ticketInfo.add(new Phrase("Tier: " + tier.getName(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        ticketInfo.add(new Phrase("   Description: " + tier.getDescription() + "\n", new Font(Font.FontFamily.HELVETICA, 10)));

        ct.addElement(ticketInfo);
        ct.go();
    }
}
