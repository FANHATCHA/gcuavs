package daoImpl;

import dao.AttemptsDao;
import model.UserAttempts;
import utilities.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AttemptsDaoImpl implements AttemptsDao {

    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM user_attempts WHERE email = ?";
    private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (email, attempts,ip_address,last_modified) VALUES(?,?,?,?)";
    private static final String SQL_USER_ATTEMPTS_EXISTS = "SELECT * from user_attempts where email = ?";
    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts SET attempts = attempts + 1, last_modified = ? WHERE email = ?";

    @Override
    public UserAttempts getUserAttempts(String email) throws ClassNotFoundException {
        if(isUserExists(email) == true){
            UserAttempts userAttempts = null;
            // Step 1: Establishing a Connection
            try (Connection connection = JDBCUtils.getConnection();
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_ATTEMPTS_GET);) {
                preparedStatement.setString(1, email);
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String ipAddress = rs.getString("ip_address");
                    int attempts = rs.getInt("attempts");
                    Calendar lastModified = JDBCUtils.getDateCalendar(rs.getString("last_modified"));
                    userAttempts = new UserAttempts(email, attempts, ipAddress,lastModified);
                }
            } catch (SQLException | ParseException exception) {
                JDBCUtils.printSQLException((SQLException) exception);
            }
            return userAttempts;

        }else{
            return new UserAttempts(null,0, null, null);
        }

    }

    @Override
    public boolean updateFailAttempts(String email) throws SQLException {
        boolean rowUpdated;
        Calendar lastModified = Calendar.getInstance();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS)) {
            statement.setString(2, email);
            statement.setString(1, JDBCUtils.getDateString(lastModified));
            rowUpdated = statement.executeUpdate() > 0;
        }

        return rowUpdated;
    }

    @Override
    public void resetFailAttempts(String email) {

    }

    @Override
    public void addFailAttempts(UserAttempts userAttempts) throws ClassNotFoundException, SQLException {
      if(isUserExists(userAttempts.getEmail()) == true){
          updateFailAttempts(userAttempts.getEmail());

      }else{
          System.out.println(SQL_USER_ATTEMPTS_INSERT);
          try (Connection connection = JDBCUtils.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_ATTEMPTS_INSERT)) {
              preparedStatement.setString(1, userAttempts.getEmail());
              preparedStatement.setInt(2, userAttempts.getAttempts());
              preparedStatement.setString(3, userAttempts.getIpAddress());
              preparedStatement.setString(4, JDBCUtils.getDateString(userAttempts.getLastModified()));
              System.out.println(preparedStatement);
              preparedStatement.executeUpdate();
          } catch (SQLException exception) {
              JDBCUtils.printSQLException(exception);
          }
      }

    }

    @Override
    public boolean isAccountBlocked(String email) throws ClassNotFoundException {
        if(isUserExists(email) == true){
            UserAttempts userAttempts = null;
            // Step 1: Establishing a Connection
            try (Connection connection = JDBCUtils.getConnection();
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_ATTEMPTS_GET);) {
                preparedStatement.setString(1, email);
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String ipAddress = rs.getString("ip_address");
                    int attempts = rs.getInt("attempts");
                    Calendar lastModified = JDBCUtils.getDateCalendar(rs.getString("last_modified"));
                    userAttempts = new UserAttempts(email, attempts, ipAddress,lastModified);
                }
            } catch (SQLException | ParseException exception) {
                JDBCUtils.printSQLException((SQLException) exception);
            }
            if(userAttempts.getAttempts()<=3){
                return false;
            }
            return true;

        }else{
            return false;
        }


    }

    private boolean isUserExists(String email) throws ClassNotFoundException {
        boolean result = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_USER_ATTEMPTS_EXISTS)) {
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return result;
    }
}
