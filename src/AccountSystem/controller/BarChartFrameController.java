package AccountSystem.controller;

import AccountSystem.tools.ChartTools;
import AccountSystem.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;

import java.util.Calendar;

/**
 * 条形图界面控制器
 *
 * @author lck100
 */
public class BarChartFrameController {
    private ChartTools chartTools = new ChartTools();
    private PublicTools publicTools = new PublicTools();

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private ComboBox<?> barChart_comboBox;

    @FXML
    private NumberAxis numberAxis;

    /**
     * 初始化界面
     */
    public void initialize() {
        // 初始化填充下拉列表框选项
        String[] items = new String[]{"今天", "昨天", "最近3天", "最近7天", "最近30天", "最近1年（12月）", "最近1年（4季度）"};
        publicTools.public_addComboBoxItems(barChart_comboBox, items);
    }

    /**
     * 下拉列表框事件处理
     *
     * @param event 事件
     */
    public void barChart_comboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中项
        String selectedCoboboxItem = (String) barChart_comboBox.getSelectionModel().selectedItemProperty().getValue();
        // 对下拉列表框选中项进行处理
        switch (selectedCoboboxItem) {
            case "今天":
                chartTools.public_setBarChartData(1, barChart, categoryAxis, numberAxis);
                break;
            case "昨天":
                chartTools.public_setBarChartData(2, barChart, categoryAxis, numberAxis);
                break;
            case "最近3天":
                chartTools.public_setBarChartData(3, barChart, categoryAxis, numberAxis);
                break;
            case "最近7天":
                chartTools.public_setBarChartData(7, barChart, categoryAxis, numberAxis);
                break;
            case "最近30天":
                chartTools.public_setBarChartData(30, barChart, categoryAxis, numberAxis);
                break;
            case "最近1年（12月）":
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONDAY) + 1;
                chartTools.public_setMonthBarChartData(month, barChart, categoryAxis, numberAxis);
                break;
            case "最近1年（4季度）":
                chartTools.public_setSeasonBarChartData(barChart, categoryAxis, numberAxis);
                break;
            default:
                break;
        }
    }
}

