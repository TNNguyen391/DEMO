/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.userProfile;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class UserLoginError implements Serializable{
    private String loginErr;

    public UserLoginError() {
    }

    public UserLoginError(String loginErr) {
        this.loginErr = loginErr;
    }

    /**
     * @return the loginErr
     */
    public String getLoginErr() {
        return loginErr;
    }

    /**
     * @param loginErr the loginErr to set
     */
    public void setLoginErr(String loginErr) {
        this.loginErr = loginErr;
    }
    
    
}
