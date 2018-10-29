package server;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.net.URI;
import java.util.Date;
import java.util.Properties;

public class Task implements Runnable {

    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    private static final String SUBJECT = "Notification";

    private Notification notification;
    private Date date;

    public Task(Notification notification, Date date) {
        this.notification = notification;
        this.date = date;
    }

    @Override
    public void run() {
        while (true) {
            Date currentTime = new Date();
            if (currentTime.after(date)) {
                if (notification.getNotification_type().equals("http")) {
                    openURL(notification.getExtra_params());
                    System.out.println(notification.getMessage());
                }
                if (notification.getNotification_type().equals("mail")) {
                    sendMessage(notification.getExtra_params());
                    System.out.println(notification.getMessage());
                }
                break;
            }
        }
    }

    private void sendMessage(String email) {
        Session session = createEmailSession();
        String body = notification.toString();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(SUBJECT);
            message.setText(body);
            Transport.send(message);
        } catch (AddressException e){
            System.err.println("Can't set address of sender: " + USER_NAME + " Stack trace " + e.getMessage());
        } catch (MessagingException e){
            System.err.println("Can't create message with params: " + USER_NAME + " " + SUBJECT + " " + body);
        }

    }

    private Session createEmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
    }

    private void openURL(String URL) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(URL));
        }
        catch (Exception e) {
            System.err.println("Can't open URL: " + e.getMessage());
        }
    }
}
