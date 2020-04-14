package model;

import java.util.Date;

public class Ticket {

    private static final long serialVersionUID = 1L;
    private long id;
    private int number;
    private String name;
    private String description;
    private String requesterName;
    private int requesterID;
    private int assignedTo;
    private String createdAt;

    private byte[] encryptId;

    public Ticket(long id, int number, String name, String description, String requesterName, int assignedTo, String createdAt){
        this.id = id;
        this.number = number;
        this.name = name;
        this.description = description;
        this.requesterName = requesterName;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
    }

    public Ticket(int number, String name, String description, int requesterID,String createdAt){
        this.number = number;
        this.name = name;
        this.description = description;
        this.requesterID= requesterID;
        this.createdAt = createdAt;
    }
    public Ticket(byte[] encryptId, int number, String name, String description, String requesterName, String createdAt){
        this.encryptId = encryptId;
        this.number = number;
        this.name = name;
        this.description = description;
        this.requesterName = requesterName;
        this.createdAt = createdAt;
    }

    public Ticket() {

    }

    public Ticket(int assignedTo, int number){
        this.assignedTo = assignedTo;
        this.number = number;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public int getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(int requesterID) {
        this.requesterID = requesterID;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getEncryptId() {
        return encryptId;
    }

    public void setEncryptId(byte[] encryptId) {
        this.encryptId = encryptId;
    }



}
