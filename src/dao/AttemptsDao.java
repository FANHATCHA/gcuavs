package dao;

import model.UserAttempts;

import java.sql.SQLException;

public interface AttemptsDao {
    UserAttempts getUserAttempts(String email) throws ClassNotFoundException;
    boolean updateFailAttempts(String email) throws SQLException;
    void resetFailAttempts(String email);
    void addFailAttempts(UserAttempts userAttempts) throws ClassNotFoundException, SQLException;
    boolean isAccountBlocked(String email) throws ClassNotFoundException;

}
