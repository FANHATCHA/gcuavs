package web;

import dao.LoginDao;
import dao.TicketDao;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import enums.Roles;
import model.Priority;
import model.Ticket;
import model.User;
import sanitization.InputControlAndSanitization;
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
import java.util.Calendar;
import java.util.List;

import static enums.Roles.ACADEMIC_STAFF;
import static enums.Roles.STAFF_ADMIN;

@WebServlet(name = "TicketCtrl")
public class TicketCtrl extends HttpServlet {

    private TicketDao ticketDao;
    private LoginDao loginDao;

    public void init() {
        ticketDao = new TicketDaoImpl();
        loginDao = new LoginDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeOfSubmission = request.getParameter("type-of-submission");

        switch (typeOfSubmission) {
            case "createNewTicket":
                try {
                    createNewTicket(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteTicket":
                try {
                    deleteTicket(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "assignTicketTo":
                try {
                    assignTicketTo(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "assignPriority":
                try {
                    assignPriority(request, response);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    private void createNewTicket(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException, ServletException, ClassNotFoundException {

        HttpSession session = request.getSession();
        //System.out.println("User session: " + session);
        String storedToken = (String)session.getAttribute("csrfToken");
        //System.out.println("csrf token stored: " + storedToken);
        String token = request.getParameter("token");
        //System.out.println("CSRF token: " + token);

        //Auth user email
        String authUserEmail = (String) session.getAttribute("userSessionEmail");

        if (storedToken.equals(token)) {
            String equipmentName = request.getParameter("equipment-name");
            String description = request.getParameter("description");
            int authUserID = Integer.parseInt(request.getParameter("auth-user-id"));


            //Input control and validation
            InputControlAndSanitization inputControl = new InputControlAndSanitization();
            List<String> errors = inputControl.validateTicketInputs(equipmentName, description);

            List<Ticket> listOfTickets = ticketDao.listOfTickets(authUserID);
            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);

            if(errors.get(0).equals("cleaned")){

                    Ticket newTicket = new Ticket(Helper.generateTicketNumber(), equipmentName, description, authUserID,Helper.getCurrentTimestamp());
                    ticketDao.createTicket(newTicket);
                    request.setAttribute("userRole", ACADEMIC_STAFF);
                    request.setAttribute("authUser", existingUser);
                    request.setAttribute("listOfTickets", listOfTickets);
                    request.setAttribute("NOTIFICATION", "Ticket created successfully !");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/academicStaff.jsp");
                    dispatcher.forward(request, response);

            }else{
                request.setAttribute("userRole", ACADEMIC_STAFF);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/academicStaff.jsp");
                request.setAttribute("listOfTickets", listOfTickets);
                request.setAttribute("errors", errors);
                request.setAttribute("authUser", existingUser);
                dispatcher.forward(request, response);
            }


        } else {
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
    private void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        String storedToken = (String)session.getAttribute("csrfToken");

        String token = request.getParameter("token");

        long userID = Integer.parseInt(request.getParameter("auth-user-id"));
        int ticketID = Integer.parseInt(request.getParameter("ticket-id"));

        List<Ticket> listOfTickets = ticketDao.listOfTickets(userID);

        if (storedToken.equals(token)) {
            //Auth user email
            String authUserEmail = (String) session.getAttribute("userSessionEmail");

            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);

            ticketDao.deleteTicket(userID, ticketID);
            request.setAttribute("userRole", ACADEMIC_STAFF);
            request.setAttribute("listOfTickets", listOfTickets);
            request.setAttribute("authUser", existingUser);
            request.setAttribute("ERROR_NOTE", "Ticket deleted successfully !");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/academicStaff.jsp");
            dispatcher.forward(request, response);
        }else{
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void assignTicketTo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        String storedToken = (String)session.getAttribute("csrfToken");

        String token = request.getParameter("token");

        //List<Ticket> listOfTickets = ticketDao.listOfTickets(userID);

        List<String> errors = new ArrayList<>();
        String email = (String) session.getAttribute("userSessionEmail");

        if (storedToken.equals(token)) {

            if(loginDao.isGranted(email) == true){
                //long userID = Integer.parseInt(request.getParameter("user-id"));
                int ticketNumber = Integer.parseInt(request.getParameter("ticket-number"));
                int assignedTo = Integer.parseInt(request.getParameter("assigned-to-id"));
                System.out.println("Assigned To" + assignedTo);
                Ticket existingTicket = ticketDao.selectTicketByNum(ticketNumber);
                System.out.println("Requester name" + existingTicket.getRequesterName());
                List<Priority> listOfPriorities = ticketDao.listOfPriorities();
                System.out.println("Process above done !");
                Ticket updatedTicket = new Ticket(assignedTo, ticketNumber);
                ticketDao.assignTicketTo(updatedTicket);
                request.setAttribute("NOTIFICATION", "Ticket assigned successfully !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/openTicket.jsp");
                request.setAttribute("ticket", existingTicket);
                request.setAttribute("userRole", STAFF_ADMIN);
                request.setAttribute("listOfPriorities", listOfPriorities);

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void assignPriority(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {

        HttpSession session = request.getSession();
        String storedToken = (String)session.getAttribute("csrfToken");

        String token = request.getParameter("token");

        List<String> errors = new ArrayList<>();
        String email = (String) session.getAttribute("userSessionEmail");

        if (storedToken.equals(token)) {

            if(loginDao.isGranted(email) == true){

                int ticketNumber = Integer.parseInt(request.getParameter("ticket-number"));
                String priorityAssigned = request.getParameter("priority-assigned");


                Ticket existingTicket = ticketDao.selectTicketByNum(ticketNumber);
                System.out.println("Requester name" + existingTicket.getRequesterName());
                List<User> listOfUsers = loginDao.listOfUsers();
                List<Priority> listOfPriorities = ticketDao.listOfPriorities();
                System.out.println("Process above done !");
                Priority setPriority = new Priority(ticketNumber, priorityAssigned) ;
                ticketDao.updateOrCreatePriority(setPriority);
                request.setAttribute("NOTIFICATION", "Priority assigned successfully to ticket !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/openTicket.jsp");
                request.setAttribute("ticket", existingTicket);
                request.setAttribute("userRole", STAFF_ADMIN);
                request.setAttribute("listOfUsers", listOfUsers);
                request.setAttribute("listOfPriorities", listOfPriorities);
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
