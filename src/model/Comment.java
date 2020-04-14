package model;

public class Comment {

    private long id;
    private long userID;
    private String comment;
    private int ticketNumber;
    private String createdAt;
    private String name;

    public Comment(long userID, String comment, int ticketNumber, String createdAt){
        this.userID = userID;
        this.comment = comment;
        this.ticketNumber = ticketNumber;
        this.createdAt = createdAt;

    }

    public Comment(String name, String comment, int ticketNumber, String createdAt){
        this.name = name;
        this.comment = comment;
        this.ticketNumber = ticketNumber;
        this.createdAt = createdAt;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
