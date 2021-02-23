package AccountSystem.controller;

import AccountSystem.MainApp;
import AccountSystem.bean.Session;
import AccountSystem.bean.User;
import AccountSystem.dao.UserDao;
import AccountSystem.tools.SimpleTools;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 登录控制器
 *
 * @author lck100
 */
public class LogupFrameController {
    private Stage logupStage;

    public Stage getLogupStage() {
        return logupStage;
    }

    public void setLogupStage(Stage logupStage) {
        this.logupStage = logupStage;
    }

    @FXML
    private SimpleTools simpleTools = new SimpleTools();

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField nameTextField;

    /**
     * “注册”按钮事件监听器
     */
    @FXML
    void loginButtonEvent() {
        // 判断用户是否输入用户名和密码
        if (nameTextField.getText().equals("") || passwordTextField.getText().equals("")) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "请按照文本框内容提示正确填写内容！");
        } else {
            // 实例化UserDao对象
            UserDao userDao = new UserDao();
            // 封装用户输入的数据到User实体类
            User user = new User(nameTextField.getText(), SimpleTools.MD5(passwordTextField.getText()), "src\\AccountSystem\\images\\panda.png");
            // 注册用户，并返回注册结果
            boolean isLoginSuccess = userDao.register(user);
            // 对注册结果进行反馈
            if (isLoginSuccess) {
                SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "恭喜您，注册成功，欢迎使用本系统！");
            } else {
                SimpleTools.informationDialog(Alert.AlertType.ERROR, "错误", "错误", "抱歉，您注册失败了，请重新尝试！");
            }
        }
    }

    /**
     * “登录”按钮事件监听器
     */
    @FXML
    void logupButtonEvent() {
        // 判断用户是否输入用户名和密码
        if (nameTextField.getText().equals("") || passwordTextField.getText().equals("")) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "请按照文本框内容提示正确填写内容！");
        } else {
            // 实例化UserDao对象
            UserDao userDao = new UserDao();
            // 登录用户
            User loginUser = userDao.login(nameTextField.getText(), SimpleTools.MD5(passwordTextField.getText()));
            // 对是否登录成功进行判断
            if (loginUser.getUserName() != null && loginUser.getUserPassword() != null) {
                // 设置通信对象，建立登录成功通信
                Session.setUser(loginUser);
                // 在弹出的提示框种获取用户反馈
                boolean b = SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "恭喜" + Session.getUser().getUserName() + "，登录成功，欢迎使用本系统！");
                // 如果用户确定登录，则跳转到主界面
                if (b) {
                    // 关闭登录界面
                    logupStage.close();
                    // 打开主窗口
                    new MainApp().initMainFrame();
                }
            } else {
                SimpleTools.informationDialog(Alert.AlertType.ERROR, "错误", "错误", "用户名或密码错误！");
            }
        }
    }

}

