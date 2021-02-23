package AccountSystem.controller;

import AccountSystem.bean.Session;
import AccountSystem.bean.User;
import AccountSystem.dao.RecordDao;
import AccountSystem.tools.DateTools;
import AccountSystem.tools.PublicTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Date;

public class ReportFrameController {
    private PublicTools publicTools = new PublicTools();
    private DateTools dateTools = new DateTools();

    @FXML
    private Label thisYearOutputShowLabel;

    @FXML
    private ComboBox<?> thisMonthComboBox;

    @FXML
    private Label thisDayInputShowLabel;

    @FXML
    private PieChart thisWeekPieChart;

    @FXML
    private ComboBox<?> thisDayComboBox;

    @FXML
    private ComboBox<?> thisYearComboBox;

    @FXML
    private Label thisMonthBalanceShowLabel;

    @FXML
    private Label thisSeasonBalanceShowLabel;

    @FXML
    private Label thisMonthOutputShowLabel;

    @FXML
    private Label thisDayBalanceShowLabel;

    @FXML
    private ComboBox<?> thisSeasonComboBox;

    @FXML
    private Label thisMonthInputShowLabel;

    @FXML
    private PieChart thisSeasonPieChart;

    @FXML
    private Label thisDayOutputShowLabel;

    @FXML
    private Label thisWeekInputShowLabel;

    @FXML
    private Label thisYearBalanceShowLabel;

    @FXML
    private PieChart thisYearPieChart;

    @FXML
    private Label thisYearInputShowLabel;

    @FXML
    private PieChart thisDayPieChart;

    @FXML
    private Label thisWeekOutputShowLabel;

    @FXML
    private Label thisWeekShowBalanceLabel;

    @FXML
    private ComboBox<?> thisWeekComboBox;

    @FXML
    private Label thisSeasonOutputShowLabel;

    @FXML
    private Label thisSeasonInputShowLabel;

    @FXML
    private PieChart thisMonthPieChart;

    /**
     * 初始化界面
     */
    public void initialize() {
        // 初始化填充下拉列表框选项
        publicTools.public_addComboBoxItems(thisDayComboBox, new String[]{"本日"});
        publicTools.public_addComboBoxItems(thisWeekComboBox, new String[]{"本周"});
        publicTools.public_addComboBoxItems(thisMonthComboBox, new String[]{"本月"});
        publicTools.public_addComboBoxItems(thisSeasonComboBox, new String[]{"本季度"});
        publicTools.public_addComboBoxItems(thisYearComboBox, new String[]{"本年"});
    }

    /**
     * ”日报告“界面的下拉列表框的事件监听器
     *
     * @param event 事件
     */
    public void thisDayComboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中的选项
        String selectedCoboboxItem = (String) thisDayComboBox.getSelectionModel().selectedItemProperty().getValue();
        // 判断下拉列表框中的选项并进行处理
        if (selectedCoboboxItem.equals("本日")) {
            setReportFrame(Session.getUser()
                    , new Date()
                    , new Date()
                    , getBalanceByUsers(Session.getUser())
                    , thisDayPieChart
                    , thisDayOutputShowLabel
                    , thisDayInputShowLabel
                    , thisDayBalanceShowLabel);
        }
    }

    /**
     * ”周报告“界面的下拉列表框的事件监听器
     *
     * @param event 事件
     */
    public void thisWeekComboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中的选项
        String selectedCoboboxItem = (String) thisWeekComboBox.getSelectionModel().selectedItemProperty().getValue();
        if (selectedCoboboxItem.equals("本周")) {
            setReportFrame(Session.getUser()
                    , dateTools.getWeekDayStartTime(new Date())
                    , dateTools.getWeekDayEndTime(new Date())
                    , getBalanceByUsers(Session.getUser())
                    , thisWeekPieChart
                    , thisWeekOutputShowLabel
                    , thisWeekInputShowLabel
                    , thisWeekShowBalanceLabel);
        }
    }

    /**
     * ”月报告“界面的下拉列表框的事件监听器
     *
     * @param event 事件
     */
    public void thisMonthComboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中的选项
        String selectedCoboboxItem = (String) thisMonthComboBox.getSelectionModel().selectedItemProperty().getValue();
        if (selectedCoboboxItem.equals("本月")) {
            setReportFrame(Session.getUser()
                    , dateTools.getMonthStartTime(new Date())
                    , dateTools.getMonthEndTime(new Date())
                    , getBalanceByUsers(Session.getUser())
                    , thisMonthPieChart
                    , thisMonthOutputShowLabel
                    , thisMonthInputShowLabel
                    , thisMonthBalanceShowLabel);
        }
    }

    /**
     * ”季度报告“界面的下拉列表框的事件监听器
     *
     * @param event 事件
     */
    public void thisSeasonComboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中的选项
        String selectedCoboboxItem = (String) thisSeasonComboBox.getSelectionModel().selectedItemProperty().getValue();
        if (selectedCoboboxItem.equals("本季度")) {
            setReportFrame(Session.getUser()
                    , dateTools.getQuarterStartTime(new Date())
                    , dateTools.getQuarterEndTime(new Date())
                    , getBalanceByUsers(Session.getUser())
                    , thisSeasonPieChart
                    , thisSeasonOutputShowLabel
                    , thisSeasonInputShowLabel
                    , thisSeasonBalanceShowLabel);
        }
    }

    /**
     * ”年报告“界面的下拉列表框的事件监听器
     *
     * @param event 事件
     */
    public void thisYearComboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中的选项
        String selectedCoboboxItem = (String) thisYearComboBox.getSelectionModel().selectedItemProperty().getValue();
        if (selectedCoboboxItem.equals("本年")) {
            setReportFrame(Session.getUser()
                    , dateTools.getYearStartTime(new Date())
                    , dateTools.getYearEndTime(new Date())
                    , getBalanceByUsers(Session.getUser())
                    , thisYearPieChart
                    , thisYearOutputShowLabel
                    , thisYearInputShowLabel
                    , thisYearBalanceShowLabel);
        }
    }

    /**
     * 操作结果：设置饼图
     *
     * @param pieChart 饼图
     * @param input    收入总金额
     * @param output   支出总金额
     */
    private void setPieChartData(PieChart pieChart, float input, float output) {
        // 创建集合ObservableList
        ObservableList observableList = FXCollections.observableArrayList(
                new PieChart.Data("收入", input),
                new PieChart.Data("支出", output)
        );

        // 顺时针设置饼图的切片
        pieChart.setClockwise(true);
        // 方法设置标签行的长度
        pieChart.setLabelLineLength(50);
        // 将饼图的标签设置为可见
        pieChart.setLabelsVisible(true);
        // 设置饼图的起始角度
        pieChart.setStartAngle(180);

        // 为饼图填充数据
        pieChart.setData(observableList);
    }

    /**
     * 操作结果：设置报告界面饼图数据
     *
     * @param user            用户对象
     * @param startDate       起始日期
     * @param endDate         终止日期
     * @param balance         余额
     * @param pieChart        饼图
     * @param outputShowLabel 支出显示标签
     * @param inputShowLabel  收入显示标签
     * @param balanceLabel    余额显示标签
     */
    private void setReportFrame(User user, Date startDate, Date endDate, float balance, PieChart pieChart, Label outputShowLabel, Label inputShowLabel, Label balanceLabel) {
        // 实例化RecordDao
        RecordDao recordDao = new RecordDao();
        // 将起始日期格式化处理
        String thisStartDate = dateTools.dateFormat(startDate, "yyyy-MM-dd");
        // 将终止日期格式化处理
        String thisEndDate = dateTools.dateFormat(endDate, "yyyy-MM-dd");
        // 拼接SQL语句查询收入
        String thisInputsql = "select SUM(rMoney) from tb_records where rType='收入' and rDate between '" + thisStartDate + "'" + " and " + "'" + thisEndDate + "' and uId=" + user.getUserId() + ";";
        // 拼接SQL语句查询支出
        String thisOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and rDate between '" + thisStartDate + "'" + " and " + "'" + thisEndDate + "' and uId=" + user.getUserId() + ";";
        // 执行SQL语句获取收入
        float thisInput = recordDao.getResultValueBySql(thisInputsql);
        // 执行SQL语句获取支出
        float thisOutput = recordDao.getResultValueBySql(thisOutputsql);
        // 设置标签名称
        outputShowLabel.setText(String.valueOf(thisOutput));
        inputShowLabel.setText(String.valueOf(thisInput));
        balanceLabel.setText(String.valueOf(balance));
        // 为饼图填充数据
        setPieChartData(pieChart, thisInput, thisOutput);
    }

    /**
     * 操作结果：根据用户计算余额
     *
     * @param user 用户对象
     * @return float 余额
     */
    private float getBalanceByUsers(User user) {
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 获取用户支出总额
        float totalOutput = recordDao.getTotalAccount("支出", user.getUserId());
        // 获取用户收入总额
        float totalInput = recordDao.getTotalAccount("收入", user.getUserId());
        // 计算并返回余额
        return totalInput - totalOutput;
    }
}
