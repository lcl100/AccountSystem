package AccountSystem.controller;

import AccountSystem.bean.Classification;
import AccountSystem.bean.Record;
import AccountSystem.bean.Session;
import AccountSystem.bean.TableData;
import AccountSystem.dao.ClassificationDao;
import AccountSystem.dao.RecordDao;
import AccountSystem.tools.PublicTools;
import AccountSystem.tools.SimpleTools;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 更改账目界面控制器
 *
 * @author lck100
 */
public class AlterAccountFrameController {
    private PublicTools publicTools = new PublicTools();

    @FXML
    private String selectedCoboboxItem = null;

    @FXML
    private String selectedRadioButton = null;

    @FXML
    private TextField idTextField;

    @FXML
    private RadioButton outputRadioButton;

    @FXML
    private Button alterButton;

    @FXML
    private TextField moneyTextField;

    @FXML
    private DatePicker datePickerText;

    @FXML
    private RadioButton inputRadioButton;

    @FXML
    private TextArea memoTextArea;

    @FXML
    private ComboBox<?> classificationComboBox;

    public void initialize() {
        idTextField.setText("");
        inputRadioButton.setSelected(false);
        inputRadioButton.setDisable(true);
        outputRadioButton.setSelected(false);
        outputRadioButton.setDisable(true);
        moneyTextField.setText("");
        moneyTextField.setDisable(true);
        classificationComboBox.getItems().clear();
        classificationComboBox.setDisable(true);
        memoTextArea.setText("");
        memoTextArea.setDisable(true);
        datePickerText.getEditor().setText("");
        datePickerText.setDisable(true);
        alterButton.setDisable(true);
    }

    /**
     * “支出”单选按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
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
    }

    /**
     * “收入”单选按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
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
    }

    /**
     * ”分类“下拉列表框的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void classificationComboBoxEvent(ActionEvent actionEvent) {
        //只处理选中的状态
        selectedCoboboxItem = (String) classificationComboBox.getSelectionModel().selectedItemProperty().getValue();
    }

    /**
     * ”查询“按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void checkButtonEvent(ActionEvent actionEvent) {
        // 实例化RecordDao
        RecordDao recordDao = new RecordDao();
        // 实例化ClassificationDao
        ClassificationDao classificationDao = new ClassificationDao();

        String id = idTextField.getText();
        // 判断用户是否输入要查询的序号
        if (id == null || id.equals("")) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "警告", "警告", "要查询的ID不能为空！");
        } else {
            // 使用正则表达式判断用户输入是否是数字而不是字符串
            boolean bb = Pattern.compile("^[-\\+]?[\\d]*$").matcher(id).matches();
            if (bb) {
                Record checkedRecord = recordDao.selectRecordByIdAndUserId(Integer.parseInt(id), Session.getUser().getUserId());
                if (checkedRecord.getRecordType() == null && checkedRecord.getRecordClassification() == null) {
                    SimpleTools.informationDialog(Alert.AlertType.WARNING, "警告", "警告", "该项不存在，请重新输出序号！");
                    // 重置数据填充
                    initialize();
                } else {
                    // 使界面组件可编辑
                    inputRadioButton.setDisable(false);
                    outputRadioButton.setDisable(false);
                    moneyTextField.setDisable(false);
                    classificationComboBox.setDisable(false);
                    memoTextArea.setDisable(false);
                    datePickerText.setDisable(false);
                    alterButton.setDisable(false);

                    // 设置界面的值
                    if (checkedRecord.getRecordType().equals("支出")) {
                        //设置类型
                        outputRadioButton.setSelected(true);
                    } else {
                        inputRadioButton.setSelected(true);
                    }

                    // 如果支出单选按钮被选中
                    if (outputRadioButton.isSelected()) {
                        // 查询支出分类所在的名称
                        List<Classification> classificationList = classificationDao.selectByType("支出");
                        // 实例化一个一维数组
                        String[] classificationNames = new String[classificationList.size()];
                        // 将查询得到的分类名称装到一维数组中
                        for (int i = 0; i < classificationList.size(); i++) {
                            classificationNames[i] = classificationList.get(i).getcName();
                        }
                        // 重新为下拉列表框赋予选项
                        classificationComboBox.setItems(FXCollections.observableArrayList((List) Arrays.asList(classificationNames)));
                    }
                    // 如果收入单选按钮被选中
                    if (inputRadioButton.isSelected()) {
                        // 查询支出分类所在的名称
                        List<Classification> classificationList = classificationDao.selectByType("收入");
                        // 实例化一个一维数组
                        String[] classificationNames = new String[classificationList.size()];
                        // 将查询得到的分类名称装到一维数组中
                        for (int i = 0; i < classificationList.size(); i++) {
                            classificationNames[i] = classificationList.get(i).getcName();
                        }
                        // 重新为下拉列表框赋予选项
                        classificationComboBox.setItems(FXCollections.observableArrayList((List) Arrays.asList(classificationNames)));
                    }

                    //设置金额
                    moneyTextField.setText(String.valueOf(checkedRecord.getRecordMoney()));

                    // 设置分类
                    String str = checkedRecord.getRecordClassification();
                    int index = 0;
                    if (checkedRecord.getRecordType().equals("支出")) {
                        List outputList = FXCollections.observableArrayList((List) classificationDao.selectByType("收入"));
                        for (int i = 0; i < outputList.size(); i++) {
                            if (str.equals(outputList.get(i))) {
                                index = i;
                            }
                        }
                        classificationComboBox.getSelectionModel().select(index);
                    }
                    if (checkedRecord.getRecordType().equals("收入")) {
                        List inputList = FXCollections.observableArrayList((List) classificationDao.selectByType("收入"));
                        for (int i = 0; i < inputList.size(); i++) {
                            if (str.equals(inputList.get(i))) {
                                index = i;
                            }
                        }
                        classificationComboBox.getSelectionModel().select(index);
                    }
                    //设置备注
                    memoTextArea.setText(checkedRecord.getRecordMemo());
                    //设置日期
                    datePickerText.setValue(LocalDate.parse(checkedRecord.getRecordDate()));
                }
            } else {
                SimpleTools.informationDialog(Alert.AlertType.WARNING, "警告", "警告", "序号应该为数字而不是字符串！");
            }

        }
    }

    /**
     * ”更改“按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void alterButtonEvent(ActionEvent actionEvent) {
        // 序号
        int id = Integer.parseInt(idTextField.getText());
        //类型
        String type = "";
        if (inputRadioButton.isSelected()) {
            type = inputRadioButton.getText();
        } else if (outputRadioButton.isSelected()) {
            type = outputRadioButton.getText();
        }
        // 金额，把从文本框得到的string类型数据转换为float类型
        float money = Float.parseFloat(moneyTextField.getText());
        // 分类
        String classification = classificationComboBox.getSelectionModel().getSelectedItem().toString();
        // 备注
        String memo = memoTextArea.getText();
        // 日期
        String date = datePickerText.getValue().toString();
        // 将用户修改的数据封装到实体类中
        Record record = new Record(Session.getUser().getUserId(), id, type, money, classification, memo, date);
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 执行修改操作
        boolean b = recordDao.updateRecord(record);
        // 对修改结果进行判断
        if (b) {
            SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改账目成功！");
        } else {
            SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "修改账目失败！");
        }
    }

    /**
     * 将右键菜单所触发的修改的数据传到修改界面
     *
     * @param tableData 要修改的数据
     */
    public void setTableData(TableData tableData) {
        // 使界面组件可编辑
        inputRadioButton.setDisable(false);
        outputRadioButton.setDisable(false);
        moneyTextField.setDisable(false);
        classificationComboBox.setDisable(false);
        memoTextArea.setDisable(false);
        datePickerText.setDisable(false);
        alterButton.setDisable(false);

        // 将要修改的数据填充到各个控件中
        idTextField.setText(tableData.getId());
        if (tableData.getType().equals("支出")) {
            outputRadioButton.setSelected(true);
        } else {
            inputRadioButton.setSelected(true);
        }
        if (outputRadioButton.isSelected()) {
            List<Classification> classificationList = new ClassificationDao().selectByType("支出");
            // 实例化一个一维数组
            String[] classificationNames = new String[classificationList.size()];
            // 将查询得到的分类名称装到一维数组中
            for (int i = 0; i < classificationList.size(); i++) {
                classificationNames[i] = classificationList.get(i).getcName();
            }
            classificationComboBox.setItems(FXCollections.observableArrayList((List) Arrays.asList(classificationNames)));
        }
        if (inputRadioButton.isSelected()) {
            List<Classification> classificationList = new ClassificationDao().selectByType("收入");
            // 实例化一个一维数组
            String[] classificationNames = new String[classificationList.size()];
            // 将查询得到的分类名称装到一维数组中
            for (int i = 0; i < classificationList.size(); i++) {
                classificationNames[i] = classificationList.get(i).getcName();
            }
            classificationComboBox.setItems(FXCollections.observableArrayList((List) Arrays.asList(classificationNames)));
        }
        moneyTextField.setText(tableData.getMoney());
        // 设置分类
        String str = tableData.getClassification();
        int index = 0;
        if (tableData.getType().equals("支出")) {
            List<Classification> classificationList = new ClassificationDao().selectByType("支出");
            // 实例化一个一维数组
            String[] classificationNames = new String[classificationList.size()];
            // 将查询得到的分类名称装到一维数组中
            for (int i = 0; i < classificationList.size(); i++) {
                classificationNames[i] = classificationList.get(i).getcName();
            }
            List outputList = FXCollections.observableArrayList((List) Arrays.asList(classificationNames));
            for (int i = 0; i < outputList.size(); i++) {
                if (str.equals(outputList.get(i))) {
                    index = i;
                }
            }
            //设置选中的分类
            classificationComboBox.getSelectionModel().select(index);
        }
        if (tableData.getType().equals("收入")) {
            List<Classification> classificationList = new ClassificationDao().selectByType("收入");
            // 实例化一个一维数组
            String[] classificationNames = new String[classificationList.size()];
            // 将查询得到的分类名称装到一维数组中
            for (int i = 0; i < classificationList.size(); i++) {
                classificationNames[i] = classificationList.get(i).getcName();
            }
            List inputList = FXCollections.observableArrayList((List) Arrays.asList(classificationNames));
            for (int i = 0; i < inputList.size(); i++) {
                if (str.equals(inputList.get(i))) {
                    index = i;
                }
            }
            // 设置选中的分类
            classificationComboBox.getSelectionModel().select(index);
        }
        memoTextArea.setText(tableData.getMemo());
        datePickerText.setValue(LocalDate.parse(tableData.getDate()));
    }
}
