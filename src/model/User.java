package model;

public class User {
    private static final long serialVersionUID = 1L;
    private long id;
    private int roleID;
    private String name;
    private String email;
    private String password;

    public User(long id, String email, String name, String password, int roleID) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roleID = roleID;
    }

    public User(String email, String name, String password, int roleID) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roleID = roleID;
    }

    public User(long id, String email, String name){
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public User(long id, String email, String name, int roleID){
        this.id = id;
        this.email = email;
        this.name = name;
        this.roleID = roleID;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
