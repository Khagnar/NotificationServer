package server;

import java.util.Date;

public class Notification {
    private String external_id;
    private String message;
    private Date time;
    private String notification_type;
    private String extra_params;

    public Notification (String external_id, String message, Date time, String notification_type, String extra_params) {
        this.external_id = external_id;
        this.message = message;
        this.time = time;
        this.notification_type = notification_type;
        this.extra_params = extra_params;
    }

    public Date getTime() {
        return time;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public String getExtra_params() {
        return extra_params;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return ("ID: " + external_id + "\nMessage: " + message + "\nTime: " + time + "\nNotification_type: "
                + notification_type + "\nExtraParams: " + extra_params + "\n");
    }

}
