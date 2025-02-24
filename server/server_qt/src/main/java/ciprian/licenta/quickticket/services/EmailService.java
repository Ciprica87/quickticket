package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.utils.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTickets(String recipientEmail, Event event) throws MessagingException {
        try {
            byte[] pdfContent = PDFGenerator.createEventTickets(event);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Your Tickets for " + event.getName());
            helper.setText("Please find your tickets attached.", true);

            DataSource dataSource = new ByteArrayDataSource(new ByteArrayInputStream(pdfContent), "application/pdf");

            helper.addAttachment("tickets.pdf", dataSource);

            mailSender.send(message);
        } catch (IOException e) {
            throw new RuntimeException("Failed to attach the PDF file", e);
        }
    }

    public void sendStaffCredentials(String recipientEmail, String username, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject("Welcome to the Event Team!");

        String emailContent = "<p>Dear " + username + ",</p>"
                + "<p>Welcome to the event team! Your account has been created successfully.</p>"
                + "<p><strong>Username:</strong> " + username + "</p>"
                + "<p><strong>Password:</strong> " + password + "</p>"
                + "<p>Please log in to the system using these credentials.</p>"
                + "<p>Best regards,<br/>The Event Management Team</p>";

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

}
