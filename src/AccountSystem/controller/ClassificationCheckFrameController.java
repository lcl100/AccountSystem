package AccountSystem.controller;

import AccountSystem.bean.Classification;
import AccountSystem.bean.Session;
import AccountSystem.bean.TableData;
import AccountSystem.dao.ClassificationDao;
import AccountSystem.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * 按分类条件查询界面控制器
 *
 * @author lck100
 */
public class ClassificationCheckFrameController {
    private PublicTools publicTools = new PublicTools();

    @FXML
    private TableView<TableData> input_classification_tableView;

    @FXML
    private TableColumn<TableData, String> classification_idColumn;

    @FXML
    private TableColumn<TableData, String> classification_memoColumn2;

    @FXML
    private TableColumn<TableData, String> classification_dateColumn2;

    @FXML
    private ComboBox<?> outputClassificationComboBox;

    @FXML
    private TableColumn<TableData, String> classification_typeColumn2;

    @FXML
    private TableView<TableData> output_classification_tableView;

    @FXML
    private TableColumn<TableData, String> classification_idColumn2;

    @FXML
    private TableColumn<TableData, String> classification_typeColumn;

    @FXML
    private TableColumn<TableData, String> classification_classificationColumn;

    @FXML
    private TableColumn<TableData, String> classification_memoColumn;

    @FXML
    private ComboBox<?> inputClassificationComboBox;

    @FXML
    private TableColumn<TableData, String> classification_moneyColumn2;

    @FXML
    private TableColumn<TableData, String> classification_classificationColumn2;

    @FXML
    private TableColumn<TableData, String> classification_moneyColumn;

    @FXML
    private TableColumn<TableData, String> classification_dateColumn;

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
        // 将所有的收入分类添加到下拉列表框中
        publicTools.public_addComboBoxItems(inputClassificationComboBox, inputClassificationNames);
        // 拼接要查询的SQL语句
        String inputSql = "select * from tb_records where rType='收入' and  uId=" + Session.getUser().getUserId() + ";";
        // 填充数据到表格控件中
        publicTools.public_initTableViewData(input_classification_tableView
                , publicTools.public_getTableViewData(inputSql)
                , classification_idColumn2
                , classification_typeColumn2
                , classification_moneyColumn2
                , classification_classificationColumn2
                , classification_memoColumn2
                , classification_dateColumn2);

        // 获取支出分类的所有信息
        List<Classification> outputClassificationList = new ClassificationDao().selectByType("支出");
        // 实例化一个一维数组
        String[] outputClassificationNames = new String[outputClassificationList.size()];
        // 将查询得到的分类名称装到一维数组中
        for (int i = 0; i < outputClassificationList.size(); i++) {
            outputClassificationNames[i] = outputClassificationList.get(i).getcName();
        }
        // 将所有的支出分类名称填充到下拉列表框中
        publicTools.public_addComboBoxItems(outputClassificationComboBox, outputClassificationNames);
        // 拼接要查询的SQL语句
        String outputSql =  "select * from tb_records where rType='支出' and uId=" + Session.getUser().getUserId() + ";";
        // 填充数据到表格控件中
        publicTools.public_initTableViewData(output_classification_tableView
                , publicTools.public_getTableViewData(outputSql)
                , classification_idColumn
                , classification_typeColumn
                , classification_moneyColumn
                , classification_classificationColumn
                , classification_memoColumn
                , classification_dateColumn);
    }

    /**
     * ”支出“选项卡中的下拉菜单的事件方法
     *
     * @param event 事件
     */
    public void outputClassificationComboBoxEvent(ActionEvent event) {
        // 获取支出下拉列表框选中项
        String selectedCoboboxItem = (String) outputClassificationComboBox.getSelectionModel().selectedItemProperty().getValue();
        // 拼接要查询的SQL语句
        String sql = "select * from tb_records where rClassification='" + selectedCoboboxItem + "' and uId=" + Session.getUser().getUserId() + ";";
        // 填充数据到表格控件中
        publicTools.public_initTableViewData(output_classification_tableView
                , publicTools.public_getTableViewData(sql)
                , classification_idColumn
                , classification_typeColumn
                , classification_moneyColumn
                , classification_classificationColumn
                , classification_memoColumn
                , classification_dateColumn);
    }

    /**
     * ”收入“选项卡中的下拉菜单的事件方法
     *
     * @param event 事件
     */
    public void inputClassificationComboBoxEvent(ActionEvent event) {
        // 获取收入下拉列表框选中项
        String selectedCoboboxItem = (String) inputClassificationComboBox.getSelectionModel().selectedItemProperty().getValue();
        // 拼接SQL语句
        String sql = "select * from tb_records where rClassification='" + selectedCoboboxItem + "' and uId=" + Session.getUser().getUserId() + ";";
        // 填充数据到表格控件中
        publicTools.public_initTableViewData(input_classification_tableView
                , publicTools.public_getTableViewData(sql)
                , classification_idColumn2
                , classification_typeColumn2
                , classification_moneyColumn2
                , classification_classificationColumn2
                , classification_memoColumn2
                , classification_dateColumn2);
    }

}
