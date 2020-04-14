package model;

public class Priority {

    private long id;
    private int ticketNumber;
    private String priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Priority(int ticketNumber, String priority){
       this.ticketNumber = ticketNumber;
       this.priority = priority;
    }

}
