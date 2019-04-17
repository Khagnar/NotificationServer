package server;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Task implements Runnable {

    private Notification notification;
    private Date date;

    private static final String USERNAME = "Example@gmail.com";
    private static final String PASSWORD = "Example";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public Task(Notification notification, Date date) {
        this.notification = notification;
        this.date = date;
    }

    @Override
    public void run() {
        while (true) {
            Date currentDate = new Date();
            if (currentDate.after(date)) {
                createAndSendMessage();
                break;
            }
        }
    }

    private Session createEmailSession() {
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", SMTP_HOST);
        prop.setProperty("mail.smtp.user", USERNAME);
        prop.setProperty("mail.smtp.password", PASSWORD);
        prop.setProperty("mail.smtp.port", SMTP_PORT);
        prop.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
    }

    private void createAndSendMessage() {
        MimeMessage message = new MimeMessage(createEmailSession());
        try {
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getEmail()));
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());

            Transport transport = createEmailSession().getTransport("smtp");
            transport.connect(SMTP_HOST, USERNAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());

        } catch (AddressException e) {
            System.err.println("Invalid address");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.err.println("Can't create or send message");
            e.printStackTrace();
            }
    }
}
