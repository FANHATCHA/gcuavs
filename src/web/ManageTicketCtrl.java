package web;

import dao.LoginDao;
import dao.TicketDao;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static enums.Roles.GCU_AVS_STAFF;

@WebServlet(name = "ManageTicketCtrl")
public class ManageTicketCtrl extends HttpServlet {

    private TicketDao ticketDao;
    private LoginDao loginDao;

    public void init() {
        ticketDao = new TicketDaoImpl();
        loginDao = new LoginDaoImpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int number = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        Ticket existingTicket = ticketDao.selectTicketByNum(number);
        List<User> listOfUsers = loginDao.listOfUsers();
        String email = (String) session.getAttribute("userSessionEmail");

        List<Comment> listOfComments = ticketDao.listOfComment(number);
        List<Status> listOfStatus = ticketDao.listOfStatus(number);

        User authUser = loginDao.fetchUser(email);
        List<Priority> listOfPriorities = ticketDao.listOfPriorities();
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/manageTicket.jsp");
        request.setAttribute("ticket", existingTicket);
        request.setAttribute("userRole", GCU_AVS_STAFF);
        request.setAttribute("listOfUsers", listOfUsers);
        request.setAttribute("listOfPriorities", listOfPriorities);
        request.setAttribute("listOfComments", listOfComments);
        request.setAttribute("listOfStatus", listOfStatus);
        //request.setAttribute("authUser", authUser.getId());
        dispatcher.forward(request, response);
    }
}
