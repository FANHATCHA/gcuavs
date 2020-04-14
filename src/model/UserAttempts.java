package model;

import java.util.Calendar;

public class UserAttempts {
    private int id;
    private String email;
    private int attempts;
    private String ipAddress;
    private Calendar lastModified;

    public UserAttempts(String email, int attempts, String ipAddress, Calendar lastModified){
       this.email = email;
       this.attempts = attempts;
       this.ipAddress = ipAddress;
       this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAddress = ipAdress;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public void setLastModified(Calendar lastModified) {
        this.lastModified = lastModified;
    }



}
