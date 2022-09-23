package me.origami_sensei.noughtsandcrosses.models;

import java.sql.Time;
//just the code to make a game request. not that interesting
//Trust me I'm well aware that I don't need it. I have it just in case I ever do.
public class GameRequest {
    private Time time_sent;
    private String sender;
    private String recipient;

    public GameRequest(Time time_sent, String sender, String recipient) {
        this.time_sent = time_sent;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Time getTime_sent() {
        return time_sent;
    }

    public void setTime_sent(Time time_sent) {
        this.time_sent = time_sent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
