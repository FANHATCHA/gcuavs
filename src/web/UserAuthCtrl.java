package web;

import dao.AttemptsDao;
import dao.LoginDao;
import dao.TicketDao;
import daoImpl.AttemptsDaoImpl;
import daoImpl.LoginDaoImpl;
import daoImpl.TicketDaoImpl;
import model.*;
import sanitization.InputControlAndSanitization;
import utilities.BCrypt;
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
import java.util.Calendar;
import java.util.List;

import static enums.Roles.*;

@WebServlet(name = "UserAuthCtrl")
public class UserAuthCtrl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;
    private AttemptsDao attemptsDao;
    private TicketDao ticketDao;

    /**
     *
     */
    public void init() {
        loginDao = new LoginDaoImpl();
        attemptsDao = new AttemptsDaoImpl();
        ticketDao = new TicketDaoImpl();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            authenticate(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */

    protected void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("Password: " + password);
        int totalAttempts= attemptsDao.getUserAttempts(email).getAttempts();
        System.out.println("Total number of attempts are: " + " " + totalAttempts);

        //Input control and sanitization
        InputControlAndSanitization inputControl = new InputControlAndSanitization();
        List<String> errors = inputControl.validation(email, password);


        System.out.println("List of errors: " + errors.get(0));

        User existingUser = loginDao.fetchUser(email);
        System.out.println("is Account blocked: " + attemptsDao.isAccountBlocked(email));

        if (attemptsDao.isAccountBlocked(email) == false) {
            if(errors.get(0).equals("cleaned")){
                if (existingUser != null){
                    System.out.println("Existing user ID:  " + existingUser.getId());

                    boolean matched = BCrypt.checkpw(password, existingUser.getPassword());
                    if(matched == true){
                        LoginBean loginBean = new LoginBean();
                        loginBean.setEmail(email);
                        loginBean.setPassword(password);

                        try {
                            if (loginDao.validate(loginBean)) {

                                request.setAttribute("authUser", existingUser);

                                HttpSession session = request.getSession();
                                session.setAttribute("userSessionEmail", email);

                                session.setAttribute("csrfToken", Helper.generateCSRFToken());
                                System.out.println("Random token value: " + Helper.generateCSRFToken());

                                if(loginDao.fetchUserRole(email).equals(DEVELOPER)){
                                    List<User> adminStaffs = loginDao.listOfAdminStaffs();
                                    request.setAttribute("userRole", DEVELOPER);
                                    request.setAttribute("admins", adminStaffs);
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/developer.jsp");
                                    dispatcher.forward(request, response);
                                }else if(loginDao.fetchUserRole(email).equals(ACADEMIC_STAFF)){
                                    List<Ticket> ticketList = ticketDao.listOfTickets(existingUser.getId());
                                    request.setAttribute("listOfTickets", ticketList);
                                    request.setAttribute("userRole", ACADEMIC_STAFF);
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/academicStaff.jsp");
                                    dispatcher.forward(request, response);
                                }else if(loginDao.fetchUserRole(email).equals(STAFF_ADMIN)){
                                    List<User> lisOfUsers = loginDao.listOfUsers();
                                    request.setAttribute("userRole", STAFF_ADMIN);
                                    request.setAttribute("users", lisOfUsers);
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/adminStaff.jsp");
                                    dispatcher.forward(request, response);
                                }else if(loginDao.fetchUserRole(email).equals(GCU_AVS_STAFF)){
                                   long userId = loginDao.fetchUser(email).getId();

                                    List<Ticket> listOfTickets = ticketDao.ticketsByAssign(userId);
                                    request.setAttribute("userRole", GCU_AVS_STAFF);
                                    request.setAttribute("listOfTickets",listOfTickets );
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/public/loggedInAs/gcuAVSstaff.jsp");
                                    dispatcher.forward(request, response);
                                }else{
                                    response.sendRedirect("index.jsp");
                                }


                            } else {
                                response.sendRedirect("index.jsp");
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Calendar lastModified = Calendar.getInstance();
                        UserAttempts userAttempts  = new UserAttempts(email, 1, request.getRemoteAddr(), lastModified);
                        attemptsDao.addFailAttempts(userAttempts);
                        request.setAttribute("ERROR_NOTE", "These credentials does not match our records.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{
                    request.setAttribute("ERROR_NOTE", "These credentials does not match our records.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                }

            }else{
                request.setAttribute("errors", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);

            }


        }else{
            request.setAttribute("ERROR_NOTE", "Maximum number of attempts exceeded. Account blocked, contact admin !");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }


    }

}
