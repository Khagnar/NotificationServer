package server;

import java.util.Date;

public class Notification {
    private String subject;
    private String message;
    private String email;
    private Date date;

    public Notification(String subject, String message, String email, Date date) {
        this.subject = subject;
        this.message = message;
        this.email = email;
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
