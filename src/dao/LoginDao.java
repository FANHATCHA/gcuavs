package dao;

import enums.Roles;
import model.LoginBean;
import model.User;

import java.util.List;

public interface LoginDao {

    User fetchUser(String userEmail);
    boolean validate(LoginBean loginBean) throws ClassNotFoundException;
    Enum<Roles> fetchUserRole(String email);
    int registerUser(User user) throws ClassNotFoundException;
    boolean isUserExists(String email) throws ClassNotFoundException;
    List<User> listOfAdminStaffs();
    boolean deleteStaffAdmin(int userID);
    List<User> listOfUsers();
    boolean isGranted(String email);
}
