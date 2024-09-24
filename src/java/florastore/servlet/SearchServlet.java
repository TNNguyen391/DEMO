/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.servlet;

import florastore.userProfile.ProductDAO;
import florastore.userProfile.ProductDTO;
import florastore.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstants.SearchFeature.INVALID_PAGE);

        String searchValue = request.getParameter("txtSearchValue");
        String pageNumber = request.getParameter("pageNo");
        String navbar = request.getParameter("navbarShop");
        String goBack = request.getParameter("pageBack");
        String goForward = request.getParameter("pageForward");
        boolean showAll = false;
        
        System.out.println(searchValue);
        
        HttpSession session = request.getSession();
        try {
            ProductDAO dao = new ProductDAO();
            String lastSearchValue = (String) session.getAttribute("LASTSEARCH");
            double list = 0;
            if (pageNumber == null && goBack != null) {
                pageNumber = goBack;
            } else if (pageNumber == null && goForward != null) {
                pageNumber = goForward;
            }
            if (navbar != null && pageNumber == null) {                         //nhấn shop → show 9 sản phẩm đầu tiên trong danh sách toàn bộ sản phẩm
                session.removeAttribute("LASTSEARCH");                          //xóa attribute để không bị nhảy sai
                list = dao.searchTotalProduct(searchValue, true);               //get total product founded
                showAll = true;               
            } else if (lastSearchValue == null && pageNumber != null) {         //trước đó nhấn shop mà chuyển trang 2, 3, ...
                showAll = true;                                                 //→ show 9 sản phẩm tiếp theo
                list = dao.searchTotalProduct(searchValue, true);
            } else if (searchValue != null && pageNumber == null) {             //user search → show 9 sản phẩm đầu tiên được tìm thấy
                session.removeAttribute("LASTSEARCH");                          //xóa attribute ghi lại cái mới (ghi nhiều lần mà ko xóa sẽ gây quá tải)
                session.setAttribute("LASTSEARCH", searchValue);                //ghi attribute lastSearch với chuỗi lấy được từ search bar
                list = dao.searchTotalProduct(searchValue, false);
            } else if (lastSearchValue != null && pageNumber != null) {         //trước đó search mà chuyển trang 2, 3, ...
                searchValue = lastSearchValue;                                  //→ show 9 sản phẩm tiếp theo
                list = dao.searchTotalProduct(searchValue, false);              //searchValue lấy từ value được ghi vào lastSearch
            }
            //thực hiện phân bổ 9 sản phẩm 1 trang
            double pageSize = Math.ceil(list / 9);                              //lấy size list chia 9, làm tròn lên tuyệt đối rồi ép kiểu thành int
            int pageSizeConvert = (int) pageSize;                               //cái này để làm thanh chuyển trang << 1 2 3 4 >>

            //show 9 sản phẩm đầu tiên
            int page = 0;
            int[] range = null;
            if (pageNumber == null) {                                           //sau khi user search/ấn shop thì pageNumber luôn là null
                page = 1;                                                   //lúc này pageNumber mặc định luôn là 1
            } else {                                                            //nếu chuyển qua trang 2, 3, ... thì pageNumber đã ko còn là null
                if (goBack != null) {
                    page = Integer.parseInt(pageNumber);
                    page--;
                } else if (goForward != null) {
                    page = Integer.parseInt(pageNumber);
                    page++;
                } else {
                    page = Integer.parseInt(pageNumber);
                }
                request.removeAttribute("RESULT_LIST");
                session.removeAttribute("PAGE_SIZE");
            }
            range = dao.getPageRange(page);                                     //lấy phạm vi sản phẩm để show
                                                                                //nếu page = 1 thì lấy sản phẩm từ  1 →  9 (nếu có)
                                                                                //nếu page = 2 thì lấy sản phẩm từ 10 → 18 (nếu có)
                                                                                //nếu page = 3 thì lấy sản phẩm từ 19 → 27 (nếu có)
            if (showAll) {
                dao.showProductInPages(searchValue, range[0], range[1], true);    //lấy 9 sản phẩm trong phạm vi    (tính năng show toàn bộ)
            } else {
                dao.showProductInPages(searchValue, range[0], range[1], false);    //lấy 9 sản phẩm trong phạm vi   (tính năng show search result)
            }
            List<ProductDTO> productList = dao.getProductSplit();               //đã lấy được 9 sản phẩm
            session.removeAttribute("CURRENTPAFE");
            if (pageNumber == null) {
                session.setAttribute("CURRENTPAGE", "1");                   //sau khi search hoặc nhấn Shop thì button trang 1 sẽ sáng
            } else {
                session.setAttribute("CURRENTPAGE", page);        //trường hợp chuyển từ trang 1 sang trang khác thì button sáng theo số được nhấn
            }
            session.setAttribute("PAGE_SIZE", pageSizeConvert);                 //gán size để làm button trang 1 → 9
            request.setAttribute("RESULT_LIST", productList);                   //9 sản phẩm đã vào attribute result chuẩn bị được show
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
