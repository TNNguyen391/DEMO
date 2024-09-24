/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.servlet;

import florastore.userProfile.UpdateCreateError;
import florastore.userProfile.UserProfileDAO;
import florastore.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author ASUS
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstants.UpdateFeature.INVALID_PAGE);

//        String fullname = request.getParameter("txtFullname");                //fullname được trùng
        String email = request.getParameter("txtEmail");                        //email là unique, dùng để dò tài khoản

        String gender = request.getParameter("txtGender");                      //gender sẽ trả về male/female/hidden tùy theo cái được chọn 
        String phone = request.getParameter("txtPhone");
        String street = request.getParameter("txtStreet");                      //street và city được quyền trùng, vì có khả năng 2 người dùng ở chung 1 nơi
        String city = request.getParameter("txtCity");
//        String password = request.getParameter("txtPassword");
//        String confirmPassword = request.getParameter("txtPassword2");

        boolean foundError = false;                                             //cờ dò lỗi
        UpdateCreateError erroS = new UpdateCreateError();
        UserProfileDAO dao = new UserProfileDAO();                              //chiêu hồn sớm để dò lỗi trùng sdt
        try {
            HttpSession session = request.getSession();
            //kiểm tra tồn tại cờ confirm
            if (session.getAttribute("CONFIRM_FLAG") == null) {
//                int index = email.indexOf('@');
//                if (!email.trim().substring(0, index - 1).matches("[a-zA-Z0-9]+") || index <= 1) {          //email format
//                    foundError = true;
//                    erroS.setEmailFormat("Wrong email format!");
//                }
                //phone, street, city empty
                if (phone.trim().isEmpty()) {                                                                 //phone
                    foundError = true;
                    erroS.setPhoneEmpty("Phone number cannot be empty!");
                } else if (!phone.trim().matches("^0[0-9]{9}$")) {
                    foundError = true;
                    erroS.setPhonelength("Phone number must start with 0 and contain 10 number");             
                } else if (dao.searchPhoneExist(phone) == true) {
                    foundError = true;
                    erroS.setPhoneExisted("This phone number already exists!");
                }
                
                if (street.trim().isEmpty()) {                                                                //street
                    foundError = true;
                    erroS.setStreetEmpty("Street cannot be empty!");
                } else if (!street.trim().matches("[a-zA-Z0-9,/ ]+")) {
                    foundError = true;
                    erroS.setStreetFormat("Wrong street format!");
                }

                if (city.trim().isEmpty()) {                                                                  //city
                    foundError = true;
                    erroS.setCityEmpty("City cannot be empty!");
                } else if (!city.trim().matches("[a-zA-Z ]+")) {
                    foundError = true;
                    erroS.setCityFormat("Wrong city format!");
                }

//                if (password.trim().length() < 8) {                                                         //passwordLength
//                    foundError = true;
//                    erroS.setPasswordLength("Password length must be higher or equal to 8");
//                } else if (!password.trim().matches("[a-zA-Z0-9!@#$%^&*_-]+")) {                            //password format
//                    foundError = true;
//                    erroS.setPasswordFormat("Password cannot contain spaces or any non-alphabet characters");
//                } else if (!confirmPassword.trim().equals(password.trim())) {                               //password unmatched
//                    foundError = true;
//                    erroS.setConfirmNotMatched("Password not matched!");
//                }
                if (foundError) {                                               //tìm ra lỗi
                    request.setAttribute("CREATE_ERROR", erroS);
                    if (session.getAttribute("CONFIRM_FLAG") == null) {
                        session.removeAttribute("CONFIRM_FLAG");                 //nếu tìm ra lỗi trong lần nhập 2 thì xóa cờ confirm
                    }                                                           //để nếu lần confirm có thay đổi thông tin thì phải xác nhận lại lần nữa
                } else {                                                        //ko thấy lỗi thì bật cờ confirm nếu trước đó chưa xuất hiện confirm
                    //cờ ko tồn tại (làn đầu nhấn confirm)
                    session.setAttribute("CONFIRM_FLAG", "Click Save again to confirm change");
                }
            } else {
                //cờ tồn tại (lần nhấn thứ 2)
                session.removeAttribute("CONFIRM_FLAG");                         //xóa cờ confirm

                session.removeAttribute("GENDER");
                session.removeAttribute("PHONE");                               //xóa attribute, ghi lại cái mới để hiển thị thay đổi trên web
                session.removeAttribute("STREET");
                session.removeAttribute("CITY");

                session.setAttribute("GENDER", gender);                         //ghi lại attribute mới, dữ liệu lấy từ lần update mới nhất
                session.setAttribute("PHONE", phone);
                session.setAttribute("STREET", street);
                session.setAttribute("CITY", city);

//                boolean result = true;
                boolean result = dao.updateAccount(email, gender, phone, street, city);
                if (result) {
                    url = (String) siteMap.get(MyAppConstants.UpdateFeature.SUCCESS);
                } else {
                    System.out.println("Update database FAILED!");
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
