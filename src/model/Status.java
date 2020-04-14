package model;

public class Status {

    private long userId;
    private int ticketNumber;
    private String progress;

    public Status(long userId, int ticketNumber, String progress){
        this.userId = userId;
        this.ticketNumber = ticketNumber;
        this.progress = progress;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }


}
