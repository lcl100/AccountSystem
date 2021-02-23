package AccountSystem.tools;

import AccountSystem.bean.Classification;
import AccountSystem.bean.Record;
import AccountSystem.bean.TableData;
import AccountSystem.dao.RecordDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class PublicTools {

    /**
     * 操作结果：向下拉列表框中添加列表项
     *
     * @param comboBox 下拉列表框
     * @param items    列表项
     */
    public void public_addComboBoxItems(ComboBox comboBox, Object[] items) {
        // 清除下列列表框中的所有选项
        comboBox.getItems().clear();
        ObservableList options = FXCollections.observableArrayList(items);
        // 添加下拉列表项
        comboBox.setItems(options);
    }


    /**
     * 操作结果：通过SQL语句从数据库得到表格视图的数据
     *
     * @param sql SQL语句
     * @return ObservableList
     */
    public ObservableList<TableData> public_getTableViewData(String sql) {
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 通过SQL语句从数据库查询账目记录信息
        List<Record> recordList = recordDao.getRecordBySql(sql);
        // 创建ObservableList<TableData>
        ObservableList<TableData> data = FXCollections.observableArrayList();
        // 循环遍历
        for (Record r : recordList) {
            // 封装数据到TableData实体类种
            TableData td = new TableData(String.valueOf(r.getRecordId()), r.getRecordType(),
                    String.valueOf(r.getRecordMoney()), r.getRecordClassification(), r.getRecordMemo(),
                    r.getRecordDate());
            // 将封装完成的实体类对象添加到集合种
            data.add(td);
        }
        // 返回添加数据完成的集合
        return data;
    }

    /**
     * 操作结果：设置表格视图的数据
     *
     * @param tableView            表格视图
     * @param data                 数据
     * @param idColumn             编号列
     * @param typeColumn           类型列
     * @param moneyColumn          金额列
     * @param classificationColumn 分类列
     * @param memoColumn           备注列
     * @param dateColumn           日期列
     */
    public void public_initTableViewData(TableView tableView,
                                         ObservableList<TableData> data,
                                         TableColumn<TableData, String> idColumn,
                                         TableColumn<TableData, String> typeColumn,
                                         TableColumn<TableData, String> moneyColumn,
                                         TableColumn<TableData, String> classificationColumn,
                                         TableColumn<TableData, String> memoColumn,
                                         TableColumn<TableData, String> dateColumn) {
        // 设置各列的数据
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        moneyColumn.setCellValueFactory(cellData -> cellData.getValue().moneyProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationProperty());
        memoColumn.setCellValueFactory(cellData -> cellData.getValue().memoProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        tableView.setItems(data);
    }
}
