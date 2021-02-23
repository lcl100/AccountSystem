package AccountSystem.bean;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userImagePath;

    public User() {

    }

    public User(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String userName, String userPassword, String userImagePath) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userImagePath = userImagePath;
    }

    public User(int userId, String userName, String userPassword, String userImagePath) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userImagePath = userImagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

}
