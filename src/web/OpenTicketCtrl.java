package web;

import dao.LoginDao;
import dao.TicketDao;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import model.Priority;
import model.Status;
import model.Ticket;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static enums.Roles.STAFF_ADMIN;

@WebServlet(name = "OpenTicketCtrl")
public class OpenTicketCtrl extends HttpServlet {

    private LoginDao loginDao;
    private TicketDao ticketDao;

    public void init() {
        loginDao = new LoginDaoImpl();
        ticketDao = new TicketDaoImpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            openTicket(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openTicket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<>();
        String email = (String) session.getAttribute("userSessionEmail");
        System.out.println("Process started");
        System.out.println("---------------------------------------");
        System.out.println(request.getParameter("id"));
        if(loginDao.isGranted(email) == true){
            int number = Integer.parseInt(request.getParameter("id"));
            System.out.println("Number" + number);
            Ticket existingTicket = ticketDao.selectTicketByNum(number);
            System.out.println("Requester name" + existingTicket.getRequesterName());
            List<User> listOfUsers = loginDao.listOfUsers();
            List<Status> listOfStatus = ticketDao.listOfStatus(number);
            System.out.println("Process above done !");
            List<Priority> listOfPriorities = ticketDao.listOfPriorities();
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/openTicket.jsp");
            request.setAttribute("ticket", existingTicket);
            request.setAttribute("userRole", STAFF_ADMIN);
            request.setAttribute("listOfUsers", listOfUsers);
            request.setAttribute("listOfPriorities", listOfPriorities);
            request.setAttribute("listOfStatus", listOfStatus);
            dispatcher.forward(request, response);

        }else{
            System.out.println("Not allowed");
            errors.add("Not allowed !");
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
        System.out.println("Process ended");
        System.out.println("---------------------------------------");
    }

}
