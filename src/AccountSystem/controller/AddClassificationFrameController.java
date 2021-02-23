package AccountSystem.controller;

import AccountSystem.bean.Classification;
import AccountSystem.dao.ClassificationDao;
import AccountSystem.tools.SimpleTools;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

import java.util.List;

/**
 * 添加分类界面控制器
 *
 * @author lck100
 */
public class AddClassificationFrameController {
    private ClassificationDao classificationDao = new ClassificationDao();

    private Stage dialogStage;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private TextField inputClassificationNameTextField;

    @FXML
    private ListView<String> inputClassificationListView;

    @FXML
    private TextField outputClassificationNameTextField;

    @FXML
    private ListView<String> outputClassificationListView;

    /**
     * 初始化界面
     */
    public void initialize() {
        // 获取收入分类的所有信息
        List<Classification> inputClassificationList = new ClassificationDao().selectByType("收入");
        // 实例化一个一维数组
        String[] inputClassificationNames = new String[inputClassificationList.size()];
        // 将查询得到的分类名称装到一维数组中
        for (int i = 0; i < inputClassificationList.size(); i++) {
            inputClassificationNames[i] = inputClassificationList.get(i).getcName();
        }
        inputClassificationListView.setItems(FXCollections.observableArrayList(inputClassificationNames));

        // 获取支出分类的所有信息
        List<Classification> outputClassificationList = new ClassificationDao().selectByType("支出");
        // 实例化一个一维数组
        String[] outputClassificationNames = new String[outputClassificationList.size()];
        // 将查询得到的分类名称装到一维数组中
        for (int i = 0; i < outputClassificationList.size(); i++) {
            outputClassificationNames[i] = outputClassificationList.get(i).getcName();
        }
        outputClassificationListView.setItems(FXCollections.observableArrayList(outputClassificationNames));
    }

    /**
     * ”添加支出“按钮的事件监听器
     *
     * @param event 事件
     */
    public void addOutputButtonEvent(ActionEvent event) {
        // 获取用户输入的支出分类名称
        String output = outputClassificationNameTextField.getText();
        // 判断用户是否输入为空
        if (null == output || "".equals(output)) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "文本框内容不能为空！");
        } else {
            // 列表视图添加用户输入的支出分类重名
            outputClassificationListView.getItems().add(output);
            // 封装要添加的分类实体类数据
            Classification classification = new Classification(output, "支出");
            // 进行添加操作
            boolean b = classificationDao.addClassification(classification);
            outputClassificationNameTextField.setText("");
        }

    }

    /**
     * “支出”右键菜单“编辑”的事件监听器
     *
     * @param event 事件
     */
    public void output_editContextMenuEvent(ActionEvent event) {
        outputClassificationListView.setCellFactory(TextFieldListCell.forListView());
        outputClassificationListView.setEditable(true);
        outputClassificationListView.setFocusTraversable(true);
    }

    /**
     * “支出”右键菜单“删除”的事件监听器
     *
     * @param event 事件
     */
    public void output_deleteMenuItemEvent(ActionEvent event) {
        // 获取用户选中的支出分类
        String outputItem = (String) outputClassificationListView.getSelectionModel().getSelectedItem();
        // 确认用户是否要删除
        boolean b = SimpleTools.informationDialog(Alert.AlertType.CONFIRMATION, "确认", "确认", "请问是否确认删除名为" + outputItem + "的支出分类？");
        if (b) {
            // 从列表视图中移除该分类
            outputClassificationListView.getItems().remove(outputItem);
            // 从数据库中删除该分类
            classificationDao.deleteClassification(new Classification(outputItem, "支出"));
        }
    }

    /**
     * 支出分类界面”返回“按钮的事件监听器
     *
     * @param event 事件
     */
    public void outputClassificationReturnButtonEvent(ActionEvent event) {
        // 关闭该界面
        dialogStage.close();
    }

    /**
     * ”添加收入“按钮的事件监听器
     *
     * @param event 事件
     */
    public void addInputButtonEvent(ActionEvent event) {
        // 获取用户输入的收入分类
        String input = inputClassificationNameTextField.getText();
        // 判断用户是否输入为空
        if (null == input || "".equals(input)) {
            SimpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "文本框内容不能为空！");
        } else {
            // 向列表视图添加收入分类
            inputClassificationListView.getItems().add(input);
            // 向数据库中添加收入分类
            classificationDao.addClassification(new Classification(input, "收入"));
            // 清空用户输入
            inputClassificationNameTextField.setText("");
        }
    }

    /**
     * “收入”右键菜单“编辑”的事件监听器
     *
     * @param event 事件
     */
    public void input_editContextMenuEvent(ActionEvent event) {
        inputClassificationListView.setCellFactory(TextFieldListCell.forListView());
        inputClassificationListView.setEditable(true);
        inputClassificationListView.setFocusTraversable(true);
    }

    /**
     * “收入”右键菜单“删除”的事件监听器
     *
     * @param event 事件
     */
    public void input_deleteContextMenuEvent(ActionEvent event) {
        // 获取用户输入的收入分类
        String inputItem = (String) inputClassificationListView.getSelectionModel().getSelectedItem();
        boolean b = SimpleTools.informationDialog(Alert.AlertType.CONFIRMATION, "确认", "确认", "请问是否确认删除名为" + inputItem + "的收入分类？");
        if (b) {
            // 向列表视图中移除该收入分类
            inputClassificationListView.getItems().remove(inputItem);
            // 向数据库中删除该收入分类
            classificationDao.deleteClassification(new Classification(inputItem, "收入"));
        }
    }

    /**
     * 收入分类界面”返回“按钮的事件监听器
     *
     * @param event 事件
     */
    public void inputClassificationReturnButtonEvent(ActionEvent event) {
        dialogStage.close();
    }

    /**
     * “支出”分类列表视图编辑完成的事件监听器
     *
     * @param stringEditEvent 事件
     */
    public void outputClassificationListViewCommitEvent(ListView.EditEvent<String> stringEditEvent) {
        // 获取列表视图选中的原值
        String sourceValue = stringEditEvent.getSource().getSelectionModel().getSelectedItem();
        // 获取列表视图编辑后的新值
        String newValue = stringEditEvent.getNewValue();
        // 向列表视图移除原值
        outputClassificationListView.getItems().remove(sourceValue);
        // 向列表视图添加新值
        outputClassificationListView.getItems().add(newValue);
        // 向数据库更新值
        classificationDao.updateClassification(newValue, sourceValue);
    }

    /**
     * 收入分类列表视图编辑完成的事件监听器，按回车键完成对事件的响应
     *
     * @param stringEditEvent 事件
     */
    public void inputClassificationListViewCommitEvent(ListView.EditEvent<String> stringEditEvent) {
        // 获取列表视图选中的原值
        String sourceValue = stringEditEvent.getSource().getSelectionModel().getSelectedItem();
        // 获取列表视图编辑后的新值
        String newValue = stringEditEvent.getNewValue();
        // 向列表视图移除原值
        inputClassificationListView.getItems().remove(sourceValue);
        // 向列表视图添加新值
        inputClassificationListView.getItems().add(newValue);
        // 向数据库更新值
        classificationDao.updateClassification(newValue, sourceValue);
    }

}
