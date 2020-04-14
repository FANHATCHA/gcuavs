package daoImpl;

import dao.LoginDao;
import enums.Roles;
import model.LoginBean;
import model.User;
import utilities.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static enums.Roles.*;

public class LoginDaoImpl implements LoginDao {

    private static final String FETCH_BY_UNIQUE_EMAIL = "SELECT name, id, email, password, role_id FROM users where email =?";
    private static final String SQL_USERS_INSERT = "INSERT INTO users(email, name, password, role_id) VALUES(?,?,?,?)";
    private static final String FETCH_BY_STAFF_ADMINS = "SELECT name, id, email FROM users where role_id = 1";
    private static final String SQL_DELETE_STAFF_ADMIN = "DELETE FROM users where id=?";
    private static final String SQL_FETCH_USERS = "SELECT name, id, email, role_id FROM users where role_id = 3 OR role_id = 2";


    @Override
    public User fetchUser(String userEmail) {

        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(FETCH_BY_UNIQUE_EMAIL);) {
            preparedStatement.setString(1, userEmail);
            System.out.println("1st st"+ preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int roleID = rs.getInt("role_id");
                user = new User(id, email, name, password, roleID);
            }
        } catch (SQLException exception) {
           JDBCUtils.printSQLException((SQLException) exception);
        }
        return user;
    }

    @Override
    public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where email = ?")) {
            preparedStatement.setString(1, loginBean.getEmail());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return status;
    }

    @Override
    public Enum<Roles> fetchUserRole(String email) {

        int roleId = fetchUser(email).getRoleID();
        System.out.println("User role id " + roleId);
        System.out.println("User email address entered" + email);
        System.out.println("User email address received: " + fetchUser(email).getEmail());
        switch(roleId) {
            case 0:
                return DEVELOPER;
            case 1:
                return STAFF_ADMIN;
            case 2:
                return GCU_AVS_STAFF;
            case 3:
                return ACADEMIC_STAFF;
            default:
                return NOT_SPECIFIED;
        }

    }

    @Override
    public int registerUser(User user) throws ClassNotFoundException {
        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_USERS_INSERT)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRoleID());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return result;
    }

    @Override
    public boolean isUserExists(String email) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where email = ?")) {
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return status;
    }

    @Override
    public List<User> listOfAdminStaffs() {
        List<User> users = new ArrayList<>();

        try (Connection connection = JDBCUtils.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(FETCH_BY_STAFF_ADMINS)) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                users.add(new User(id,email,name));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return users;
    }

    @Override
    public boolean deleteStaffAdmin(int userID) {
        boolean rowDeleted = false;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STAFF_ADMIN);) {
            statement.setInt(1, userID);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public List<User> listOfUsers() {
        List<User> listOfUsers = new ArrayList<>();

        try (Connection connection = JDBCUtils.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FETCH_USERS)) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                int roleId = rs.getInt("role_id");
                listOfUsers.add(new User(id,email,name, roleId));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return listOfUsers;
    }

    @Override
    public boolean isGranted(String email) {
        int roleId = 1;
        if(fetchUser(email).getRoleID() == roleId){
            return true;
        }else{
            return false;

        }
    }
}
