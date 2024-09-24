/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.userProfile;

import florastore.utils.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ASUS
 */
public class ProductDAO {

    private List<ProductDTO> productSplit;
    
    public List<ProductDTO> getProductSplit() {
        return productSplit;
    }

    public double searchTotalProduct(String searchValue, boolean searchAll) throws SQLException, NamingException {
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                //String sql = "SELECT * FROM FlowerProducts WHERE ProductName LIKE ?";
                String sql = null;
                if (searchAll == true) {
                    sql = "SELECT ProductId, StoreId, ProductName, ProductType, ProductCondition, ProductDetail, "
                            + "ProductPrice, ProductQuantity, ImageURL "
                            + "FROM FlowerProducts";
                    stm = con.prepareStatement(sql);
                } else {
                    sql = "SELECT ProductId, StoreId, ProductName, ProductType, ProductCondition, ProductDetail, "
                            + "ProductPrice, ProductQuantity, ImageURL "
                            + "FROM FlowerProducts WHERE ProductName LIKE ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    count++;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    public void showProductInPages(String searchValue, int position, int limitSize, boolean searchAll) throws SQLException, NamingException {
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        int counter = 0;                                                        //cờ dò vị trí để add

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                //String sql = "SELECT * FROM FlowerProducts WHERE ProductName LIKE ?";
                String sql = null;
                if (searchAll == true) {
                    sql = "SELECT ProductId, StoreId, ProductName, ProductType, ProductCondition, ProductDetail, "
                            + "ProductPrice, ProductQuantity, ImageURL "
                            + "FROM FlowerProducts";
                    stm = con.prepareStatement(sql);
                } else {
                    sql = "SELECT ProductId, StoreId, ProductName, ProductType, ProductCondition, ProductDetail, "
                            + "ProductPrice, ProductQuantity, ImageURL "
                            + "FROM FlowerProducts WHERE ProductName LIKE ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    //get data from result set
                    int ProductId = rs.getInt("ProductId");
                    int StoreId = rs.getInt("StoreId");
                    String ProductName = rs.getString("ProductName");
                    String ProductType = rs.getString("ProductType");
                    String ProductCondition = rs.getString("ProductCondition");
                    String ProductDetail = rs.getString("ProductDetail");
                    int ProductPrice = rs.getInt("ProductPrice");
                    int ProductQuantity = rs.getInt("ProductQuantity");
                    String ImageURL = rs.getString("ImageURL");
                    //set data to DTO properties
                    ProductDTO dto = new ProductDTO(ProductId, StoreId, ProductName, ProductType,
                            ProductCondition, ProductDetail, ProductPrice, ProductQuantity, ImageURL);
                    if (this.productSplit == null) {                                //add total
                        this.productSplit = new ArrayList<>();
                    }   //account unavailable
                    //position: vị trí cần xuất ra đầu tiên
                    //limitSize: vị trí sản phẩm cuối cùng
                    //nếu show sản phẩm thứ 10 đến 18 thì position = 10, limitSize = 18

                    if (position - 1 <= counter && counter <= limitSize - 1) {  //dò theo vị trí thực tế trong mảng thì - 1
                        this.productSplit.add(dto);
                        //this.productName2.add(dto.getProductName());
                    }
                    counter++;                                                  //nếu chưa đến vị trí cần add thì tăng tự thân 
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public int[] getPageRange(int page) {
        // Số sản phẩm trên mỗi trang
        int itemsPerPage = 9;

        // Tìm 2 đầu vị trí xuất sản phẩm
        int start = (page - 1) * itemsPerPage + 1;
        int end = page * itemsPerPage;

        return new int[]{start, end};
    }

}
