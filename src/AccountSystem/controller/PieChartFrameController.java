package AccountSystem.controller;

import AccountSystem.bean.Session;
import AccountSystem.tools.ChartTools;
import AccountSystem.tools.DateTools;
import AccountSystem.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

import java.util.Calendar;
import java.util.Date;

/**
 * 饼图界面控制器
 *
 * @author lck100
 */
public class PieChartFrameController {
    private ChartTools chartTools = new ChartTools();
    private PublicTools publicTools = new PublicTools();
    private DateTools dateTools = new DateTools();

    @FXML
    private ComboBox<?> pieChart_comboBox;

    @FXML
    private PieChart pieChart;

    /**
     * 初始化界面
     */
    public void initialize() {
        // 初始化填充下拉列表框选项
        String[] items = new String[]{"今天", "昨天", "最近1周收入", "最近1周支出", "最近1年支出（按月份）", "最近1年收入（按月份）", "最近1年支出（按季度）", "最近1年收入（按季度）"};
        publicTools.public_addComboBoxItems(pieChart_comboBox, items);
    }

    /**
     * 饼图界面的下拉列表框的监听器方法
     *
     * @param event 事件
     */
    @FXML
    public void pieChart_comboBoxEvent(ActionEvent event) {
        //只处理选中的状态
        String selectedCoboboxItem = (String) pieChart_comboBox.getSelectionModel().selectedItemProperty().getValue();
        switch (selectedCoboboxItem) {
            case "今天":
                chartTools.public_setDayPieChartData(Session.getUser(), pieChart, new Date(), new Date());
                break;
            case "昨天":
                chartTools.public_setDayPieChartData(Session.getUser(), pieChart, dateTools.getYesterdayDate(),
                        dateTools.getYesterdayDate());
                break;
            case "最近1周支出":
                chartTools.public_setWeekPieChartData(Session.getUser(), 7, pieChart, "支出");
                break;
            case "最近1周收入":
                chartTools.public_setWeekPieChartData(Session.getUser(), 7, pieChart, "收入");
                break;
            case "最近1年支出（按月份）": {
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONDAY) + 1;
                chartTools.public_setMonthPieChartData(Session.getUser(), month, pieChart, "支出");
                break;
            }
            case "最近1年收入（按月份）": {
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONDAY) + 1;
                chartTools.public_setMonthPieChartData(Session.getUser(), month, pieChart, "收入");
                break;
            }
            case "最近1年支出（按季度）":
                chartTools.public_setSeasonPieChartData(Session.getUser(), pieChart, "支出");
                break;
            case "最近1年收入（按季度）":
                chartTools.public_setSeasonPieChartData(Session.getUser(), pieChart, "收入");
                break;
            default:
                break;
        }

    }
}
