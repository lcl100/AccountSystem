package AccountSystem.controller;

import AccountSystem.bean.Session;
import AccountSystem.bean.TableData;
import AccountSystem.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * 按备注查询界面控制器
 *
 * @author lck100
 */
public class MemoCheckFrameController {
    private PublicTools publicTools = new PublicTools();

    @FXML
    private TableColumn<TableData, String> memo_classificationColumn;

    @FXML
    private TableColumn<TableData, String> memo_moneyColumn;

    @FXML
    private TableColumn<TableData, String> memo_dateColumn;

    @FXML
    private TextField memo_memoTextField;

    @FXML
    private TableView<TableData> memo_tableView;

    @FXML
    private TableColumn<TableData, String> memo_idColumn;

    @FXML
    private TableColumn<TableData, String> memo_typeColumn;

    @FXML
    private TableColumn<TableData, String> memo_memoColumn;

    /**
     * ”查询“按钮的事件监听方法
     *
     * @param event 事件
     */
    @FXML
    public void memo_checkButtonEvent(ActionEvent event) {
        // 获取用户输入的备注
        String memo = memo_memoTextField.getText();
        // 拼接SQL语句
        String sql = "select* from tb_records where rMemo like '%" + memo + "%' and uId=" + Session.getUser().getUserId() + ";";
        // 填充数据到表格控件中
        publicTools.public_initTableViewData(memo_tableView
                , publicTools.public_getTableViewData(sql)
                , memo_idColumn
                , memo_typeColumn
                , memo_moneyColumn
                , memo_classificationColumn
                , memo_memoColumn
                , memo_dateColumn);
    }
}
