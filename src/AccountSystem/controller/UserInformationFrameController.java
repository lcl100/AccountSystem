package AccountSystem.controller;

import AccountSystem.bean.Session;
import AccountSystem.bean.User;
import AccountSystem.dao.UserDao;
import AccountSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * 用户信息界面控制器
 *
 * @author lck100
 */
public class UserInformationFrameController {

    private UserDao userDao = new UserDao();
    private SimpleTools simpleTools = new SimpleTools();

    @FXML
    private TextField idTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private VBox alterPasswordVBox;

    @FXML
    private ImageView userImage;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField confirmNewPasswordTextField;

    @FXML
    private CheckBox alterPasswordCheckBox;

    /**
     * 初始化界面
     */
    public void initialize() {
        // 获取用户信息
        User user = userDao.selectUserById(Session.getUser().getUserId());
        userImage.setImage(new Image("file:/" + user.getUserImagePath()));
        nameTextField.setText(user.getUserName());
        idTextField.setText(String.valueOf(user.getUserId()));
        passwordTextField.setText(user.getUserPassword());
    }

    /**
     * 点击图片更改图片
     *
     * @param mouseEvent 事件
     */
    public void alterImageViewEvent(MouseEvent mouseEvent) {
        String importPath = null;
        //实例化文件选择器
        FileChooser fileChooser = new FileChooser();
        //设置默认文件过滤器
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("图片", "jpg", "jpeg", "png"));
        //打开文件选择框，并得到选中的文件
        File result = fileChooser.showOpenDialog(null);
        // 判断用户是否选中文件
        if (result != null) {
            importPath = result.getAbsolutePath();
            System.out.println("绝对路径："+importPath);
            // 数据库保存路径需要进行转义，否则斜杠会消失
            String s = importPath.replaceAll("\\\\", "\\\\\\\\");
            System.out.println("数据库存储："+s);
            userImage.setImage(new Image("file:/" + importPath));
            // 封装要更新的实体类对象
            User user = new User(Session.getUser().getUserId(), Session.getUser().getUserName(), Session.getUser().getUserPassword(), s);
            userDao.updateUser(user);
        }
    }

    /**
     * ”修改“按钮的事件监听器
     *
     * @param event 事件
     */
    public void alterButtonEvent(ActionEvent event) {
        if (newPasswordTextField.getText().length() == 0) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "新密码不能为空！");
        }
        if (confirmNewPasswordTextField.getText().length() == 0) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "确认密码不能为空！");
        }
        if (SimpleTools.MD5(newPasswordTextField.getText()).equals(SimpleTools.MD5(confirmNewPasswordTextField.getText()))) {
            String password = SimpleTools.MD5(confirmNewPasswordTextField.getText());
            boolean b = userDao.updateUser(new User(Session.getUser().getUserId(), Session.getUser().getUserName(), password, Session.getUser().getUserImagePath()));
            if (b) {
                boolean isSuccess = SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "密码修改成功！");
                if (isSuccess) {
                    alterPasswordVBox.setVisible(false);
                    alterPasswordCheckBox.setSelected(false);
                }
            } else {
                SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "密码修改失败！");
            }
        } else {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "新密码和确认密码必须相同！");
        }

    }

    /**
     * ”取消“按钮的监听器
     *
     * @param event 事件
     */
    public void cancelButtonEvent(ActionEvent event) {
        alterPasswordVBox.setVisible(false);
        alterPasswordCheckBox.setSelected(false);
    }

    /**
     * ”修改密码“复选框监听器
     *
     * @param event 事件
     */
    public void alterPasswordCheckBoxEvent(ActionEvent event) {
        if (alterPasswordCheckBox.isSelected()) {
            alterPasswordVBox.setVisible(true);
        } else {
            alterPasswordVBox.setVisible(false);
        }
    }
}
