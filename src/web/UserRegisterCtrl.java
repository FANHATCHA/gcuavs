package web;

import dao.LoginDao;
import daoImpl.LoginDaoImpl;
import model.User;
import sanitization.InputControlAndSanitization;
import utilities.BCrypt;

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

import static enums.Roles.DEVELOPER;
import static enums.Roles.STAFF_ADMIN;

@WebServlet(name = "UserRegisterCtrl")
public class UserRegisterCtrl extends HttpServlet {

    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDaoImpl();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeOfSubmission = request.getParameter("type-of-submission");

        switch (typeOfSubmission) {
            case "createNewAdminStaff":
                try {
                    createNewAdminStaff(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteStaffAdmin":
                try {
                    deleteStaffAdmin(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "createNewUser":
                try {
                    createNewUser(request, response);
                } catch (SQLException | ParseException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteUser":
                try {
                    deleteUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }
    private void createNewAdminStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException, ServletException, ClassNotFoundException {

        HttpSession session = request.getSession();
        //System.out.println("User session: " + session);
        String storedToken = (String)session.getAttribute("csrfToken");
        //System.out.println("csrf token stored: " + storedToken);
        String token = request.getParameter("token");
        //System.out.println("CSRF token: " + token);

        List<User> adminStaffs = loginDao.listOfAdminStaffs();

        if (storedToken.equals(token)) {
            String name = request.getParameter("name");
            System.out.println("Name inputed: " + name);
            String email = request.getParameter("email");
            System.out.println("Email inputed :" + email);
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");


            //Input control and validation
            InputControlAndSanitization inputControl = new InputControlAndSanitization();
            List<String> errors = inputControl.validateRegInputs(email,name, password, confirmPassword);

            //Auth user email
            String authUserEmail = (String) session.getAttribute("userSessionEmail");

            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);

            //check if user already exists !
            boolean doesUserExists = loginDao.isUserExists(email);

            if(errors.get(0).equals("cleaned")){
                 if (doesUserExists == false){
                     //Set roleId
                     int roleId = 1;
                     String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
                     User newUser = new User(email, name, generatedSecuredPasswordHash, roleId);
                     loginDao.registerUser(newUser);
                     request.setAttribute("userRole", DEVELOPER);
                     request.setAttribute("authUser", existingUser);
                     request.setAttribute("admins", adminStaffs);
                     request.setAttribute("NOTIFICATION", "User created successfully !");
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/developer.jsp");
                     dispatcher.forward(request, response);
                 }else{
                     request.setAttribute("userRole", DEVELOPER);
                     request.setAttribute("admins", adminStaffs);
                     request.setAttribute("NOTIFICATION", "User already exists !");
                     request.setAttribute("authUser", existingUser);
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/developer.jsp");
                     dispatcher.forward(request, response);
                 }

            }else{
                request.setAttribute("userRole", DEVELOPER);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/developer.jsp");
                request.setAttribute("admins", adminStaffs);
                request.setAttribute("errors", errors);
                request.setAttribute("authUser", existingUser);
                dispatcher.forward(request, response);
            }


        } else {
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }
    }
    private void deleteStaffAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        //System.out.println("User session: " + session);
        String storedToken = (String)session.getAttribute("csrfToken");
        //System.out.println("csrf token stored: " + storedToken);
        String token = request.getParameter("token");
        //System.out.println("CSRF token: " + token);
        int userID = Integer.parseInt(request.getParameter("auth-id"));

        List<User> adminStaffs = loginDao.listOfAdminStaffs();

        if (storedToken.equals(token)) {
            //Auth user email
            String authUserEmail = (String) session.getAttribute("userSessionEmail");

            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);
            loginDao.deleteStaffAdmin(userID);
            request.setAttribute("userRole", DEVELOPER);
            request.setAttribute("authUser", existingUser);
            request.setAttribute("admins", adminStaffs);
            request.setAttribute("NOTIFICATION", "Staff admin deleted successfully !");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/developer.jsp");
            dispatcher.forward(request, response);
        }else{
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }





    }
    private void createNewUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException, ServletException, ClassNotFoundException {

        HttpSession session = request.getSession();
        //System.out.println("User session: " + session);
        String storedToken = (String)session.getAttribute("csrfToken");
        //System.out.println("csrf token stored: " + storedToken);
        String token = request.getParameter("token");
        //System.out.println("CSRF token: " + token);

        List<User> lisOfUsers = loginDao.listOfUsers();

        if (storedToken.equals(token)) {
            String name = request.getParameter("name");
            System.out.println("Name inputed: " + name);
            String email = request.getParameter("email");
            System.out.println("Email inputed :" + email);
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");
            String role = request.getParameter("role-of-user");
            System.out.println("User role is: " + role);


            //Input control and validation
            InputControlAndSanitization inputControl = new InputControlAndSanitization();
            List<String> errors = inputControl.validateRegInputs(email,name, password, confirmPassword);

            //Auth user email
            String authUserEmail = (String) session.getAttribute("userSessionEmail");

            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);

            //check if user already exists !
            boolean doesUserExists = loginDao.isUserExists(email);

            if(errors.get(0).equals("cleaned")){
                if (doesUserExists == false){
                    //Set roleId
                    int validRole = inputControl.validateRole(role);
                    System.out.println("User role is: " + validRole);
                    String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
                    User newUser = new User(email, name, generatedSecuredPasswordHash, validRole);
                    int result = loginDao.registerUser(newUser);
                    if(result == 1){
                        request.setAttribute("userRole", STAFF_ADMIN);
                        request.setAttribute("authUser", existingUser);
                        request.setAttribute("users", lisOfUsers);
                        request.setAttribute("NOTIFICATION", "User created successfully !");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/adminStaff.jsp");
                        dispatcher.forward(request, response);
                    }else{
                        request.setAttribute("userRole", STAFF_ADMIN);
                        request.setAttribute("authUser", existingUser);
                        request.setAttribute("users", lisOfUsers);
                        request.setAttribute("NOTIFICATION", "Could not create a new user !");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/adminStaff.jsp");
                        dispatcher.forward(request, response);
                    }

                }else{
                    request.setAttribute("userRole", STAFF_ADMIN);
                    request.setAttribute("users", lisOfUsers);
                    request.setAttribute("NOTIFICATION", "User already exists !");
                    request.setAttribute("authUser", existingUser);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/adminStaff.jsp");
                    dispatcher.forward(request, response);
                }

            }else{
                request.setAttribute("userRole", STAFF_ADMIN);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/adminStaff.jsp");
                request.setAttribute("users", lisOfUsers);
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
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        //System.out.println("User session: " + session);
        String storedToken = (String)session.getAttribute("csrfToken");
        //System.out.println("csrf token stored: " + storedToken);
        String token = request.getParameter("token");
        //System.out.println("CSRF token: " + token);
        int userID = Integer.parseInt(request.getParameter("auth-id"));

        List<User> lisOfUsers = loginDao.listOfUsers();

        if (storedToken.equals(token)) {
            //Auth user email
            String authUserEmail = (String) session.getAttribute("userSessionEmail");

            //Fetch users
            User existingUser = loginDao.fetchUser(authUserEmail);

            HttpSession backSession = request.getSession();
            backSession.setAttribute("userSessionEmail", authUserEmail);
            loginDao.deleteStaffAdmin(userID);
            request.setAttribute("userRole", STAFF_ADMIN);
            request.setAttribute("authUser", existingUser);
            request.setAttribute("users", lisOfUsers);
            request.setAttribute("NOTIFICATION", "User deleted successfully !");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/public/loggedInAs/adminStaff.jsp");
            dispatcher.forward(request, response);
        }else{
            //DO NOT PROCESS ... this is to be considered a CSRF attack - handle appropriately
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }





    }
}
