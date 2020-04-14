package daoImpl;

import dao.TicketDao;
import model.Comment;
import model.Priority;
import model.Status;
import model.Ticket;
import utilities.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl extends LoginDaoImpl implements TicketDao{

    private static final String SQL_TICKETS_INSERT = "INSERT INTO tickets (number, name,description,requester_id, assigned_id, created_at) VALUES(?,?,?,?,?,?)";
    private static final String SQL_FETCH_TICKETS_BY_USERID = "SELECT *  FROM tickets INNER JOIN users ON tickets.requester_id=users.id where users.id =? ORDER BY tickets.id DESC";
    private static final String SQL_DELETE_TICKET = "DELETE FROM tickets where requester_id=? AND id=?";
    private static final String SQL_SELECT_ALL_TICKETS = "SELECT *  FROM tickets INNER JOIN users ON tickets.requester_id=users.id ORDER BY tickets.id DESC";
    private static final String SQL_SELECT_TICKET_BY_NUM = "SELECT *  FROM tickets INNER JOIN users ON tickets.requester_id=users.id WHERE tickets.number=?";
    private static final String SQL_ASSIGN_TICKET_TO = "UPDATE tickets SET assigned_id = ? WHERE number=?";
    private static final String SQL_ASSIGN_PRIORITY = "UPDATE priorities SET priority = ? WHERE ticket_number=?";
    private static final String SQL_GET_PRIORITIES = "SELECT * FROM priorities WHERE ticket_number = ?";
    private static final String SQL_INSERT_PRIORITIES = "INSERT INTO priorities (ticket_number, priority) VALUES(?,?)";
    private static final String SQL_ALL_PRIORITIES = "SELECT * FROM priorities";
    private static final String SQL_ADD_COMMENT = "INSERT INTO comments (comment, user_id, ticket_number, created_at) VALUES(?,?,?,?)";
    private static final String SQL_GET_COMMENTS = "SELECT *  FROM comments INNER JOIN users ON comments.user_id=users.id WHERE comments.ticket_number=? ORDER BY comments.id DESC";
    private static final String SQL_INSERT_STATUS = "INSERT INTO status (user_id,ticket_number, progress) VALUES(?,?,?)";
    private static final String SQL_GET_STATUS = "SELECT * FROM status WHERE ticket_number = ?";
    private static final String SQL_ASSIGN_STATUS = "UPDATE status SET progress = ? WHERE ticket_number=?";
    private static final String SQL_SELECT_TICKET_BY_ASSIGNED_TO = "SELECT *  FROM tickets INNER JOIN users ON tickets.requester_id=users.id WHERE tickets.assigned_id=?";

    @Override
    public void createTicket(Ticket ticket) {
        System.out.println(SQL_TICKETS_INSERT);
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_TICKETS_INSERT)) {
            preparedStatement.setInt(1, ticket.getNumber());
            preparedStatement.setString(2, ticket.getName());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setInt(4, ticket.getRequesterID());
            preparedStatement.setInt(5, ticket.getAssignedTo());
            preparedStatement.setString(6, ticket.getCreatedAt());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
    }

    @Override
    public List<Ticket> listOfTickets(long userID) {

        List<Ticket> ticketList = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FETCH_TICKETS_BY_USERID)) {
            preparedStatement.setLong(1, userID);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                int number = rs.getInt("number");
                String equipmentName = rs.getString("name");
                String description = rs.getString("description");
                String requesterName = rs.getString("users.name");
                String createdAt = rs.getString("created_at");
                int assignedTo = rs.getInt("assigned_id");
                ticketList.add(new Ticket(id, number, equipmentName, description, requesterName, assignedTo, createdAt));
                System.out.println(ticketList);
            }
        } catch (SQLException exception) {

            assert exception instanceof SQLException;
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return ticketList;
    }

    @Override
    public boolean deleteTicket(long userID, int ticketID) {
        boolean rowDeleted = false;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TICKET);) {
            statement.setLong(1, userID);
            statement.setInt(2, ticketID);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public List<Ticket> listOfAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TICKETS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int number = rs.getInt("number");
                String equipmentName = rs.getString("tickets.name");
                String description = rs.getString("description");
                String requesterName = rs.getString("users.name");
                int assignedTo = rs.getInt("assigned_id");
                String createdAt = rs.getString("created_at");
                tickets.add(new Ticket(id, number, equipmentName, description, requesterName, assignedTo, createdAt));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return tickets;
    }

    @Override
    public Ticket selectTicketByNum(int number) {

        Ticket ticket = null;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TICKET_BY_NUM);) {
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String requesterName = rs.getString("users.name");
                int assignedTo = rs.getInt("assigned_id");
                String createdAt = rs.getString("created_at");

                ticket = new Ticket(id, number, name, description, requesterName, assignedTo, createdAt);
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return ticket;
    }

    @Override
    public boolean assignTicketTo(Ticket ticket) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ASSIGN_TICKET_TO)) {
            statement.setLong(1, ticket.getAssignedTo());
            statement.setInt(2, ticket.getNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }

        return rowUpdated;
    }

    @Override
    public boolean assignPriority(Priority priority) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ASSIGN_PRIORITY)) {
            statement.setString(1, priority.getPriority());
            statement.setInt(2, priority.getTicketNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }

        return rowUpdated;
    }

    @Override
    public boolean isPrioritySet(int ticketNumber) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_GET_PRIORITIES)) {
            preparedStatement.setInt(1, ticketNumber);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return status;
    }

    @Override
    public void updateOrCreatePriority(Priority priority) throws SQLException, ClassNotFoundException {
        if(isPrioritySet(priority.getTicketNumber()) == false){
            createNewPriority(priority);
        }else{
            assignPriority(priority);
        }
    }

    @Override
    public List<Priority> listOfPriorities() {
        List<Priority> priorities = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ALL_PRIORITIES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int ticketNumber = rs.getInt("ticket_number");
                String priority = rs.getString("priority");
                priorities.add(new Priority(ticketNumber,priority));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return priorities;
    }

    @Override
    public void addComment(Comment comment) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COMMENT)) {
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setLong(2, comment.getUserID());
            preparedStatement.setInt(3, comment.getTicketNumber());
            preparedStatement.setString(4, comment.getCreatedAt());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
    }

    @Override
    public boolean canComment(String email) {
        int roleId = 2;
        if(fetchUser(email).getRoleID() == roleId){
            return true;
        }else{
            return false;

        }
    }

    @Override
    public List<Comment> listOfComment(int number) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COMMENTS)) {
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String comment = rs.getString("comment");
                String name = rs.getString("users.name");
                String createdAt = rs.getString("created_at");
                comments.add(new Comment(name, comment, number, createdAt));
            }
        } catch (SQLException exception) {
            assert exception instanceof SQLException;
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return comments;
    }

    @Override
    public void addStatus(Status status) throws SQLException {

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STATUS)) {
            preparedStatement.setLong(1, status.getUserId());
            preparedStatement.setInt(2, status.getTicketNumber());
            preparedStatement.setString(3, status.getProgress());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
    }

    @Override
    public List<Status> listOfStatus(int number) {
        List<Status> status = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_STATUS)) {
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long userId = rs.getLong("user_id");
                int ticketNumber = rs.getInt("ticket_number");
                String progress = rs.getString("progress");
                status.add(new Status(userId,ticketNumber,progress));
            }
        } catch (SQLException exception) {

            assert exception instanceof SQLException;
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return status;
    }

    @Override
    public void updateOrCreateStatus(Status status) throws SQLException, ClassNotFoundException {
        if(isStatusSet(status.getTicketNumber()) == false){
            System.out.println("adding status ......");
            addStatus(status);
        }else{
            System.out.println("updating status ......");
            updateStatus(status);
        }
    }

    @Override
    public boolean isStatusSet(int number) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_GET_STATUS)) {
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return status;
    }

    @Override
    public boolean updateStatus(Status status) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ASSIGN_STATUS)) {
            statement.setString(1,status.getProgress());
            statement.setInt(2,status.getTicketNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }

        return rowUpdated;
    }

    @Override
    public boolean canCommentTicket(int number) {
        String progress = "Closed";
        if(fetchStatus(number).getProgress().equals(progress)){
            return false;
        }else{
            return true;

        }
    }

    @Override
    public List<Ticket> ticketsByAssign(long userId) {

        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TICKET_BY_ASSIGNED_TO);) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int number = rs.getInt("number");
                String equipmentName = rs.getString("tickets.name");
                String description = rs.getString("description");
                String requesterName = rs.getString("users.name");
                int assignedTo = rs.getInt("assigned_id");
                String createdAt = rs.getString("created_at");
                tickets.add(new Ticket(id, number, equipmentName, description, requesterName, assignedTo, createdAt));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return tickets;
    }

    private void createNewPriority(Priority priority) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PRIORITIES)) {
            preparedStatement.setInt(1, priority.getTicketNumber());
            preparedStatement.setString(2, priority.getPriority());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
    }

    private Status fetchStatus(int number) {

        Status singleStatus= null;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_STATUS);) {
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                Long userID = rs.getLong("user_id");
                String progress = rs.getString("progress");
                int ticketNum = rs.getInt("ticket_number");
                singleStatus = new Status(userID, ticketNum, progress);
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException((SQLException) exception);
        }
        return singleStatus;
    }

}
