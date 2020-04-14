package web;

import dao.LoginDao;
import daoImpl.LoginDaoImpl;
import model.LoginBean;
import model.User;
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

@WebServlet(name = "UserDashboardCtrl")
public class UserDashboardCtrl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    /**
     *
     */
    public void init() {
        loginDao = new LoginDaoImpl();
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
        } catch (SQLException e) {
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

    protected void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("User email" + email);
        System.out.println("User password" + password);
        /*
        Input control and sanitization
         */

        User existingUser = loginDao.fetchUser(email);

        System.out.println("Existing user: " + existingUser );

        boolean matched = BCrypt.checkpw(password, existingUser.getPassword());
        if(matched == true){
            System.out.println("Matching status " + matched);
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(email);
            loginBean.setPassword(password);

            try {
                if (loginDao.validate(loginBean)) {

                    request.setAttribute("authUser", existingUser);

                    HttpSession session = request.getSession();
                    session.setAttribute("userSessionEmail", email);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("index.jsp");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Matching status: " + matched);
            response.sendRedirect("index.jsp");
        }

    }

}
