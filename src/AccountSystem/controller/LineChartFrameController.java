package AccountSystem.controller;

import AccountSystem.tools.ChartTools;
import AccountSystem.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;

import java.util.Calendar;

/**
 * 折线图界面控制器
 *
 * @author lck100
 */
public class LineChartFrameController {
    private PublicTools publicTools = new PublicTools();
    private ChartTools chartTools = new ChartTools();

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private ComboBox<?> lineChart_comboBox;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private NumberAxis numberAxis;

    /**
     * 初始化界面
     */
    @FXML
    public void initialize() {
        // 初始化填充下拉列表框选项
        String[] items = new String[]{"最近3天", "最近7天", "最近30天", "最近1年（12月）", "最近1年（4季度）"};
        publicTools.public_addComboBoxItems(lineChart_comboBox, items);
    }

    /**
     * 折线图界面下拉列表框事件方法
     *
     * @param event 事件
     */
    public void lineChart_comboBoxEvent(ActionEvent event) {
        // 获取下拉列表框选中项
        String selectedCoboboxItem = (String) lineChart_comboBox.getSelectionModel().selectedItemProperty().getValue();
        // 对选中项结果进行判断处理
        switch (selectedCoboboxItem) {
            case "最近3天":
                chartTools.public_setDayLineChartData(3, lineChart, categoryAxis, numberAxis);
                break;
            case "最近7天":
                chartTools.public_setDayLineChartData(7, lineChart, categoryAxis, numberAxis);
                break;
            case "最近30天":
                chartTools.public_setDayLineChartData(30, lineChart, categoryAxis, numberAxis);
                break;
            case "最近1年（12月）":
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONDAY) + 1;
                chartTools.public_setMonthLineChartData(month, lineChart, categoryAxis, numberAxis);
                break;
            case "最近1年（4季度）":
                chartTools.public_setSeasonLineChartData(lineChart, categoryAxis, numberAxis);
                break;
            default:
                break;
        }
    }

}
