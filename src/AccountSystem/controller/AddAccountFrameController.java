package AccountSystem.controller;

import AccountSystem.bean.Classification;
import AccountSystem.bean.Record;
import AccountSystem.bean.Session;
import AccountSystem.dao.ClassificationDao;
import AccountSystem.dao.RecordDao;
import AccountSystem.tools.PublicTools;
import AccountSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

/**
 * 添加账目界面控制器
 *
 * @author lck100
 */
public class AddAccountFrameController {
    private PublicTools publicTools = new PublicTools();

    @FXML
    private String selectedRadioButton = null;

    @FXML
    private String selectedCoboboxItem = null;

    @FXML
    private Label descriptionLabel;

    @FXML
    private RadioButton outputRadioButton;

    @FXML
    private DatePicker datePickerTextField;

    @FXML
    private TextField moneyTextField;

    @FXML
    private RadioButton inputRadioButton;

    @FXML
    private TextArea memoTextArea;

    @FXML
    private ComboBox<?> classificationComboBox;

    /**
     * “添加”按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void addButtonEvent(ActionEvent actionEvent) {
        // 类型
        String type = selectedRadioButton;
        // 金额，把从文本框得到的string类型数据转换为float类型
        float money = Float.parseFloat(moneyTextField.getText());
        // 分类
        String classification = selectedCoboboxItem;
        // 备注
        String memo = memoTextArea.getText();
        // 日期
        String date = datePickerTextField.getValue().toString();
        // 将用户输入的数据封装到Record实体类中
        Record record = new Record(Session.getUser().getUserId(), type, money, classification, memo, date);
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 添加数据到数据库
        boolean b = recordDao.addRecord(record);
        // 对添加操作的结果进行判断处理
        if (b) {
            SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "添加账目成功！");
            // 清空用户选择
            outputRadioButton.setSelected(false);
            inputRadioButton.setSelected(false);
            moneyTextField.setText("");
            classificationComboBox.getItems().clear();
            memoTextArea.setText("");
            datePickerTextField.getEditor().setText("");
        } else {
            SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "添加账目失败！");
        }
    }

    /**
     * “支出”单选按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    public void outputRadioButtonEvent(ActionEvent actionEvent) {
        // 获取支出分类的所有信息
        List<Classification> classificationList = new ClassificationDao().selectByType("支出");
        // 实例化一个一维数组
        String[] classificationNames = new String[classificationList.size()];
        // 将查询得到的分类名称装到一维数组中
        for (int i = 0; i < classificationList.size(); i++) {
            classificationNames[i] = classificationList.get(i).getcName();
        }
        // 给下拉列表框添加选项
        publicTools.public_addComboBoxItems(classificationComboBox, classificationNames);
        // 获取单选按钮项
        selectedRadioButton = outputRadioButton.getText();
        // 设置descriptionLabel文本内容
        descriptionLabel.setText("添加" + outputRadioButton.getText());
    }

    /**
     * “收入”单选按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    public void inputRadioButtonEvent(ActionEvent actionEvent) {
        // 获取收入分类的所有信息
        List<Classification> classificationList = new ClassificationDao().selectByType("收入");
        // 实例化一个一维数组
        String[] classificationNames = new String[classificationList.size()];
        // 将查询得到的分类名称装到一维数组中
        for (int i = 0; i < classificationList.size(); i++) {
            classificationNames[i] = classificationList.get(i).getcName();
        }
        // 给下拉列表框添加选项
        publicTools.public_addComboBoxItems(classificationComboBox, classificationNames);
        // 获取单选按钮项
        selectedRadioButton = inputRadioButton.getText();
        //设置descriptionLabel文本内容
        descriptionLabel.setText("添加" + inputRadioButton.getText());
    }

    /**
     * ”分类“下拉列表框的事件监听器
     *
     * @param actionEvent 事件
     */
    public void classificationComboBoxEvent(ActionEvent actionEvent) {
        //只处理选中的状态
        selectedCoboboxItem = (String) classificationComboBox.getSelectionModel().selectedItemProperty().getValue();
    }
}
