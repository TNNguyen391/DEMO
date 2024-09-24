/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.userProfile;

import florastore.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author ADMIN
 */
public class UserProfileDAO implements Serializable {

    public UserProfileDTO getAccountByUsernameAndPassword(String username, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UserProfileDTO dto = null;
        try {
            //1. connect database
            con = DBHelper.getConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "SELECT FullName, Gender, Role, Email, Phone, Street, City "
                        + "FROM Account "
                        + "WHERE Username = ? "
                        + "AND Password = ?";
                //3.Create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4.Query Data 
                rs = stm.executeQuery();
                //5.Process Data
                if (rs.next()) {
                    String fullName = rs.getString("FullName");
                    String gender = rs.getString("Gender");
                    String role = rs.getString("Role");
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Street");
                    String city = rs.getString("City");
                    dto = new UserProfileDTO(username, password, fullName, gender, role, email, phone, address, city);
                }//username and password are verified
            }//connection has been available
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
        return dto;
    }

    public boolean updateAccount(String email, String gender, String phone, String street, String city)
            throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {//gender, phone, street, city
            //1. kết nối DB
            con = DBHelper.getConnection();
            if (con != null) { //nếu kết nối DB được
                //2. khởi tạo lệnh SQL
                String sql = "UPDATE Account "
                        + "SET Gender = ?, Phone = ?, Street = ?, City = ? "
                        + "WHERE Email = ?";

                //3. khởi tạo statement obj
                stm = con.prepareStatement(sql);
                stm.setString(1, gender);
                stm.setString(2, phone);
                stm.setString(3, street);
                stm.setString(4, city);
                stm.setString(5, email);
                //4. Execute querry
                int affect = stm.executeUpdate();

                //5. process result
                if (affect > 0) {
                    result = true; //nếu số dóng ảnh hưởng > 0 thì update được
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean searchPhoneExist(String phone) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            //2. CREATE SQL String
            if (con != null) {
                String sql = "Select * "
                        + "From Account "
                        + "Where Phone Like ?";
                //3. Create Statement Obj
                stm = con.prepareStatement(sql);
                stm.setString(1, phone);
                //4. Execute Querry

                rs = stm.executeQuery();
                //5. process result
                while (rs.next()) {
                    result = true;
                }
            }//connection has been available
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
        return result;
    }
}
