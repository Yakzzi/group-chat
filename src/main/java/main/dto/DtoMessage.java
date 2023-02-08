package main.dto;

public class DtoMessage {

    private String text;

    private String dateTime;

    private String username;

    public DtoMessage() {
    }

    public DtoMessage(String text, String dateTime, String username) {
        this.text = text;
        this.dateTime = dateTime;
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
