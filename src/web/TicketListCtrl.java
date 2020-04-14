package web;

import dao.LoginDao;
import dao.TicketDao;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import model.Ticket;
import model.User;
import utilities.Helper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static enums.Roles.STAFF_ADMIN;

@WebServlet(name = "TicketListCtrl")
public class TicketListCtrl extends HttpServlet {
    private TicketDao ticketDao;
    private LoginDao loginDao;

    public void init() {
        ticketDao = new TicketDaoImpl();
        loginDao = new LoginDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userSessionEmail");

        User existingUser = loginDao.fetchUser(email);

        List<Ticket> listOfTickets = ticketDao.listOfAllTickets();
        List<User> lisOfUsers = loginDao.listOfUsers();


        request.setAttribute("listOfTickets", listOfTickets);
        request.setAttribute("authUser", existingUser);

        request.setAttribute("userRole", STAFF_ADMIN);
        request.setAttribute("users", lisOfUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/tickets.jsp");
        dispatcher.forward(request, response);
    }
}
