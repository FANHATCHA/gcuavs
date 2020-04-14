package dao;

import model.Comment;
import model.Priority;
import model.Status;
import model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketDao {

    void createTicket(Ticket ticket);
    List<Ticket> listOfTickets(long userID);
    boolean deleteTicket(long userID, int ticketID);
    List<Ticket> listOfAllTickets();
    Ticket selectTicketByNum(int number);
    boolean assignTicketTo(Ticket ticket) throws SQLException;
    boolean assignPriority(Priority priority) throws SQLException;
    boolean isPrioritySet(int ticketNumber) throws ClassNotFoundException;
    void updateOrCreatePriority(Priority priority) throws SQLException, ClassNotFoundException;
    List<Priority> listOfPriorities();
    void addComment(Comment comment) throws SQLException;
    boolean canComment(String email);
    List<Comment> listOfComment(int number);
    void addStatus(Status status) throws SQLException;
    List<Status> listOfStatus(int number);
    void updateOrCreateStatus(Status status) throws SQLException, ClassNotFoundException;
    boolean isStatusSet(int number) throws ClassNotFoundException;
    boolean updateStatus(Status status) throws SQLException;
    boolean canCommentTicket(int number);
    List<Ticket> ticketsByAssign(long userId);

}
