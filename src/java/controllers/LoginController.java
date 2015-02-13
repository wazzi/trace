package controllers;

import domain.Login;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * JSF managed bean for login functionality
 *
 * @author kelli
 */
@SessionScoped
@Named(value = "loginController")
public class LoginController implements Serializable {

    private String userName;
    private String password;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean validatePassword(String password){
        boolean isValid = Login.validatePassword(password);
        return isValid;
    }

}
