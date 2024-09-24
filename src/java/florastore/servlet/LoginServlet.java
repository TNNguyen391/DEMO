/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.servlet;

import florastore.userProfile.UserLoginError;
import florastore.userProfile.UserProfileDAO;
import florastore.userProfile.UserProfileDTO;
import florastore.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstants.LoginFeatures.INVALID_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        UserLoginError error = new UserLoginError();
        boolean foundErr = false;
        try {
            UserProfileDAO dao = new UserProfileDAO();
            UserProfileDTO validUser
                    = dao.getAccountByUsernameAndPassword(username, password);
            if (validUser != null) {
                url = (String) siteMap.get(MyAppConstants.LoginFeatures.SEARCH_PAGE);
                HttpSession session = request.getSession(true);
                session.setAttribute("USER", validUser);

//                //dò xem @ ở vị trí thứ mấy
//                int index = validUser.getEmail().indexOf('@'); 
//                //lấy 3 kí tự đầu trong email + *** + chuỗi từ sau @ trở đi
//                session.setAttribute("EMAIL", validUser.getEmail().substring(0, 3) + "***" + validUser.getEmail().substring(index));
//                
//                //dò xem sdt có bao nhiêu số
//                index = validUser.getPhone().length();
//                //chuyển sdt sang 0*******12
//                session.setAttribute("PHONE", "0" + "*******" + validUser.getPhone().substring(index - 2, index));
                session.setAttribute("USERNAME", username);
                //session.setAttribute("PASSWORD", password);
                session.setAttribute("GENDER", validUser.getGender());
                session.setAttribute("EMAIL", validUser.getEmail());
                session.setAttribute("PHONE", validUser.getPhone());
                session.setAttribute("STREET", validUser.getStreet());
                session.setAttribute("CITY", validUser.getCity());
            }
            if (validUser == null) {
                foundErr = true;
                error.setLoginErr("Incorrect username or password");
            }
            if (foundErr) {
                request.setAttribute("LOGIN_ERROR", error);
            }

        } catch (SQLException ex) {
            log("LoginServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet _ Naming _ " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
