/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package florastore.userProfile;

/**
 *
 * @author ASUS
 */
public class UpdateCreateError {

    private String emailExisted;                                                //Useless
    private String emailFormat;                                                 //Useless

    private String phoneExisted;
    private String phonelength;                                //
    private String phoneEmpty;
    
    private String streetFormat;                               //
    private String streetEmpty;
    
    private String cityFormat;                                 //
    private String cityEmpty;
    
    private String passwordLength;      //Iced
    private String passwordFormat;      //Iced
    private String confirmNotMatched;   //Iced with password
    
    public String getPhoneEmpty() {
        return phoneEmpty;
    }

    public void setPhoneEmpty(String phoneEmpty) {
        this.phoneEmpty = phoneEmpty;
    }

    public String getStreetEmpty() {
        return streetEmpty;
    }

    public void setStreetEmpty(String streetEmpty) {
        this.streetEmpty = streetEmpty;
    }

    public String getCityEmpty() {
        return cityEmpty;
    }

    public void setCityEmpty(String cityEmpty) {
        this.cityEmpty = cityEmpty;
    }
    
    

    public UpdateCreateError() {
    }

    public String getEmailFormat() {
        return emailFormat;
    }

    public String getStreetFormat() {
        return streetFormat;
    }

    public void setStreetFormat(String streetFormat) {
        this.streetFormat = streetFormat;
    }

    public String getCityFormat() {
        return cityFormat;
    }

    public void setCityFormat(String cityFormat) {
        this.cityFormat = cityFormat;
    }

    public void setEmailFormat(String emailFormat) {
        this.emailFormat = emailFormat;
    }

    public String getEmailExisted() {
        return emailExisted;
    }

    public void setEmailExisted(String emailExisted) {
        this.emailExisted = emailExisted;
    }

    public String getPhoneExisted() {
        return phoneExisted;
    }

    public void setPhoneExisted(String phoneExisted) {
        this.phoneExisted = phoneExisted;
    }

    public String getPhonelength() {
        return phonelength;
    }

    public void setPhonelength(String phonelength) {
        this.phonelength = phonelength;
    }

    public String getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(String passwordLength) {
        this.passwordLength = passwordLength;
    }

    public String getPasswordFormat() {
        return passwordFormat;
    }

    public void setPasswordFormat(String passwordFormat) {
        this.passwordFormat = passwordFormat;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

}
