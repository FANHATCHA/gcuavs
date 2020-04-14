package web;

import dao.LoginDao;
import dao.TicketDao;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import model.*;
import utilities.Helper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static enums.Roles.GCU_AVS_STAFF;
import static enums.Roles.STAFF_ADMIN;

@WebServlet(name = "CommentCtrl")
public class CommentCtrl extends HttpServlet {

    private LoginDao loginDao;
    private TicketDao ticketDao;

    public void init() {
        loginDao = new LoginDaoImpl();
        ticketDao = new TicketDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String typeOfSubmission = request.getParameter("type-of-submission");

        switch (typeOfSubmission) {
            case "add-comment":
                try {
                    addComment(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "add-status":
                try {
                    addStatus(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
                dispatcher.forward(request, response);
                break;
        }

    }
    private void addComment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException, ServletException, ClassNotFoundException {
        HttpSession session = request.getSession();
        String storedToken = (String)session.getAttribute("csrfToken");

        String token = request.getParameter("token");

        List<String> errors = new ArrayList<>();
        String email = (String) session.getAttribute("userSessionEmail");

        User authUser = loginDao.fetchUser(email);

        if (storedToken.equals(token)) {

            if(ticketDao.canComment(email) == true){

                int ticketNumber = Integer.parseInt(request.getParameter("ticket-number"));
                long userID = authUser.getId();
                String comment = request.getParameter("comment");

                if(ticketDao.canCommentTicket(ticketNumber) == true){

                    List<Comment> listOfComments = ticketDao.listOfComment(ticketNumber);
                    Ticket existingTicket = ticketDao.selectTicketByNum(ticketNumber);

                    List<User> listOfUsers = loginDao.listOfUsers();
                    List<Priority> listOfPriorities = ticketDao.listOfPriorities();
                    List<Status> listOfStatus = ticketDao.listOfStatus(ticketNumber);

                    Comment newComment = new Comment(userID,comment,ticketNumber, Helper.getCurrentTimestamp()) ;
                    try {
                        ticketDao.addComment(newComment);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    request.setAttribute("NOTIFICATION", "Comment assigned successfully to ticket !");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/manageTicket.jsp");
                    request.setAttribute("ticket", existingTicket);
                    request.setAttribute("userRole", GCU_AVS_STAFF);
                    request.setAttribute("listOfUsers", listOfUsers);
                    request.setAttribute("listOfPriorities", listOfPriorities);
                    request.setAttribute("authUser", authUser);
                    request.setAttribute("listOfComments", listOfComments);
                    request.setAttribute("listOfStatus", listOfStatus);
                    dispatcher.forward(request, response);

                }else{

                    List<Comment> listOfComments = ticketDao.listOfComment(ticketNumber);
                    Ticket existingTicket = ticketDao.selectTicketByNum(ticketNumber);

                    List<User> listOfUsers = loginDao.listOfUsers();
                    List<Priority> listOfPriorities = ticketDao.listOfPriorities();
                    List<Status> listOfStatus = ticketDao.listOfStatus(ticketNumber);

                    System.out.println("Cannot comment");
                    request.setAttribute("ERROR_NOTE", "Ticket closed cannot be commented !");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/manageTicket.jsp");
                    request.setAttribute("ticket", existingTicket);
                    request.setAttribute("userRole", GCU_AVS_STAFF);
                    request.setAttribute("listOfUsers", listOfUsers);
                    request.setAttribute("listOfPriorities", listOfPriorities);
                    request.setAttribute("authUser", authUser);
                    request.setAttribute("listOfComments", listOfComments);
                    request.setAttribute("listOfStatus", listOfStatus);
                    dispatcher.forward(request, response);

                }


            }else{
                System.out.println("Not allowed");
                errors.add("Not allowed !");
                request.setAttribute("errors", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }

        }else{
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }

    }

    private void addStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException, ServletException, ClassNotFoundException {
        HttpSession session = request.getSession();
        String storedToken = (String)session.getAttribute("csrfToken");

        String token = request.getParameter("token");

        List<String> errors = new ArrayList<>();
        String email = (String) session.getAttribute("userSessionEmail");

        User authUser = loginDao.fetchUser(email);

        if (storedToken.equals(token)) {

            if(ticketDao.canComment(email) == true){

                int ticketNumber = Integer.parseInt(request.getParameter("ticket-number"));
                long userID = authUser.getId();
                String progress = request.getParameter("progress");

                List<Comment> listOfComments = ticketDao.listOfComment(ticketNumber);
                Ticket existingTicket = ticketDao.selectTicketByNum(ticketNumber);

                List<User> listOfUsers = loginDao.listOfUsers();
                List<Priority> listOfPriorities = ticketDao.listOfPriorities();
                List<Status> listOfStatus = ticketDao.listOfStatus(ticketNumber);

                Status newStatus = new Status(userID, ticketNumber, progress);
                try {
                    ticketDao.updateOrCreateStatus(newStatus);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                request.setAttribute("NOTIFICATION", "Status set successfully!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/manageTicket.jsp");
                request.setAttribute("ticket", existingTicket);
                request.setAttribute("userRole", GCU_AVS_STAFF);
                request.setAttribute("listOfUsers", listOfUsers);
                request.setAttribute("listOfPriorities", listOfPriorities);
                request.setAttribute("authUser", authUser);
                request.setAttribute("listOfComments", listOfComments);
                request.setAttribute("listOfStatus", listOfStatus);
                dispatcher.forward(request, response);

            }else{
                System.out.println("Not allowed");
                errors.add("Not allowed !");
                request.setAttribute("errors", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }

        }else{
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.js");
            dispatcher.forward(request, response);
        }

    }
}
