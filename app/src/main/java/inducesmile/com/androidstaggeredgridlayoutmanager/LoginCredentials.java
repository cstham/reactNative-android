package inducesmile.com.androidstaggeredgridlayoutmanager;

public class LoginCredentials {
    private String username, password, reqDateTime, securityToken;
    //private int photo;

    public LoginCredentials(String username, String password, String reqDateTime, String securityToken) {
        this.username = username;
        this.password = password;
        this.reqDateTime = reqDateTime;
        this.securityToken = securityToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //----------------------------------------------------------------------------------------------
    public String getReqDateTime() {
        return reqDateTime;
    }

    public void setReqDateTime(String reqDateTime) {
        this.reqDateTime = reqDateTime;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }




}