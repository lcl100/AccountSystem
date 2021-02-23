package AccountSystem.tools;

import AccountSystem.bean.Session;
import AccountSystem.bean.User;
import AccountSystem.dao.RecordDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartTools {
    private DateTools dateTools = new DateTools();

    /**
     * 操作结果：设置条形图的数据根据给定的月数
     *
     * @param month        给定的月数
     * @param barChart     条形图
     * @param categoryAxis X轴
     * @param numberAxis   Y轴
     */
    public void public_setMonthBarChartData(int month,
                                            BarChart<?, ?> barChart,
                                            CategoryAxis categoryAxis,
                                            NumberAxis numberAxis) {
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 设置条形图水平轴的标签名称
        categoryAxis.setLabel("日期");
        // 设置条形图垂直轴的标签名称
        numberAxis.setLabel("金额");
        // 表示“收入”类型
        XYChart.Series monthInputSeries = new XYChart.Series();
        // 并设置该类型的名称
        monthInputSeries.setName("收入");
        // 表示“支出”类型
        XYChart.Series monthOutputSeries = new XYChart.Series();
        // 并设置该类型的名称
        monthOutputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        // 循环月数，填充数据
        for (int i = 0; i < month; i++) {
            // 得到一个Calendar日历对象
            Calendar calendar = Calendar.getInstance();
            // 设置时间
            calendar.setTime(new Date());
            calendar.add(calendar.MONDAY, -i);
            Date monthDate = calendar.getTime();
            // 将monthDate进行格式化处理获取“yyyy-MM-dd”格式
            String monthStringDate = dateTools.dateFormat(monthDate, "yyyy-MM-dd");
            // 拼接SQL语句，获取当前日期的收入总额
            String monthInputsql = "select SUM(rMoney) from tb_records where rType='收入' and MONTH(rDate)= MONTH('" + monthStringDate + "') and uId=" + Session.getUser().getUserId() + ";";
            // 拼接SQL语句，获取当前日期的支出总额
            String monthOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and MONTH(rDate)= MONTH('" + monthStringDate + "') and uId=" + Session.getUser().getUserId() + ";";
            // 执行收入SQL语句查询得到收入总额
            float monthInput = recordDao.getResultValueBySql(monthInputsql);
            // 执行支出SQL语句查询得到支出总额
            float monthOutput = recordDao.getResultValueBySql(monthOutputsql);
            // 为水平轴添加时间标签
            categoryAxis.getCategories().add(monthStringDate);
            // 填充“收入”类型的数据
            monthInputSeries.getData().add(new XYChart.Data<>(dateTools.dateFormat(dateTools.stringToDate(monthStringDate, "yyyy-MM-dd"), "yyyy-MM"), monthInput));
            // 填充“支出”类型的数据
            monthOutputSeries.getData().add(new XYChart.Data<>(dateTools.dateFormat(dateTools.stringToDate(monthStringDate, "yyyy-MM-dd"), "yyyy-MM"), monthOutput));
        }

        // 清除条形图上的所有数据
        barChart.getData().clear();
        // 重新填充条形图上的数据
        barChart.getData().addAll(monthInputSeries, monthOutputSeries);
    }

    /**
     * 操作结果：设置条形图的数据根据一年（四季度）
     *
     * @param barChart     条形图
     * @param categoryAxis X轴
     * @param numberAxis   Y轴
     */
    public void public_setSeasonBarChartData(BarChart<?, ?> barChart,
                                             CategoryAxis categoryAxis,
                                             NumberAxis numberAxis) {
        // 设置条形图水平轴的标签
        categoryAxis.setLabel("日期");
        // 设置条形图垂直轴的标签
        numberAxis.setLabel("金额");
        // 设置条形图“收入”类型
        XYChart.Series seasonInputSeries = new XYChart.Series();
        // 设置条形图“收入”类型的名称
        seasonInputSeries.setName("收入");
        // 设置条形图“支出”类型
        XYChart.Series seasonOutputSeries = new XYChart.Series();
        // 设置条形图“支出”类型的名称
        seasonOutputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        // 获取一年四季度的所有开始日期和结束日期
        List<Date[]> list = dateTools.getYearQuarterList(new Date());
        // 循环遍历日期
        for (int i = 0; i < list.size(); i++) {
            // 将开始日期和结束日期格式化处理并拼接标签
            String strDate = dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "-" + dateTools.dateFormat(list.get(i)[1], "yyyy" + "-MM-dd");
            // 为水平轴的每条数据添加标签
            categoryAxis.getCategories().add(strDate);
            // 获取一季度的收入总额
            String seasonInputSql = "select SUM(rMoney) from tb_records where rType='收入' and rDate between '" + dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "' and '" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd") + "' and uId=" + Session.getUser().getUserId();
            // 获取一季度的支出总额
            String seasonOutpuSql = "select SUM(rMoney) from tb_records where rType='支出' and rDate between '" + dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "' and '" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd") + "' and uId=" + Session.getUser().getUserId();
            // 执行一季度的收入总额的SQL语句获取收入总额
            float seasonInput = new RecordDao().getResultValueBySql(seasonInputSql);
            // 执行一季度的支出总额的SQL语句获取支出总额
            float seasonOutput = new RecordDao().getResultValueBySql(seasonOutpuSql);
            // 将数据填充到“收入”类型的条形图中
            seasonInputSeries.getData().add(new XYChart.Data<>(strDate, seasonInput));
            // 将数据填充到“支出”类型的条形图中
            seasonOutputSeries.getData().add(new XYChart.Data<>(strDate, seasonOutput));
        }
        // 清除条形图上的数据
        barChart.getData().clear();
        // 重新为条形图填充数据
        barChart.getData().addAll(seasonInputSeries, seasonOutputSeries);
    }

    /**
     * 操作结果：设置条形图的数据根据给定的天数
     *
     * @param day          给定的天数
     * @param barChart     条形图
     * @param categoryAxis X轴
     * @param numberAxis   Y轴
     */
    public void public_setBarChartData(int day, BarChart<?, ?> barChart,
                                       CategoryAxis categoryAxis,
                                       NumberAxis numberAxis) {
        // 设置水平轴的标签
        categoryAxis.setLabel("日期");
        // 设置垂直轴的标签
        numberAxis.setLabel("金额");
        // 实例化XYChart.Series对象添加收入数据
        XYChart.Series inputSeries = new XYChart.Series();
        // 设置名称
        inputSeries.setName("收入");
        // 实例化XYChart.Series对象添加支出数据
        XYChart.Series outputSeries = new XYChart.Series();
        // 设置名称
        outputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        // 循环天数，绘制每一天的条形图
        for (int i = 0; i < day; i++) {
            // 获取日历对象
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            Date calendarTime = calendar.getTime();
            // 将日期格式化
            String date = dateTools.dateFormat(calendarTime, "yyyy-MM-dd");
            // 拼接每天的收入SQL语句
            String dateInputsql = "select SUM(rMoney) from tb_records where rType='收入' and rDate= '" + date + "' and " + " uId=" + Session.getUser().getUserId() + ";";
            // 拼接每天的支出SQL语句
            String dateOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and rDate= '" + date + "' and " + " uId=" + Session.getUser().getUserId() + ";";
            // 执行SQL语句，获取每天的收入总额
            float dateInput = new RecordDao().getResultValueBySql(dateInputsql);
            // 执行SQL语句，获取每天的支出总额
            float dateOutput = new RecordDao().getResultValueBySql(dateOutputsql);
            // 为水平轴添加每天的日期标签
            categoryAxis.getCategories().add(date);
            // 将收入数据添加到收入条形图
            inputSeries.getData().add(new XYChart.Data<>(date, dateInput));
            // 将支出数据添加到支出条形图
            outputSeries.getData().add(new XYChart.Data<>(date, dateOutput));
        }

        // 清空条形图上的数据
        barChart.getData().clear();
        // 重新填充条形图上的数据
        barChart.getData().addAll(inputSeries, outputSeries);
    }

    /**
     * 操作结果：设置折线图的数据根据给定的天数
     *
     * @param day       给定的天数
     * @param lineChart 折线图
     */
    public void public_setDayLineChartData(int day, LineChart<?, ?> lineChart,
                                           CategoryAxis categoryAxis,
                                           NumberAxis numberAxis) {
        // 设置水平轴的标签
        categoryAxis.setLabel("日期");
        // 设置垂直轴的标签
        numberAxis.setLabel("金额");

        // 实例化XYChart.Series对象添加收入数据
        XYChart.Series inputSeries = new XYChart.Series();
        // 设置收入名称
        inputSeries.setName("收入");
        // 实例化XYChart.Series对象添加支出数据
        XYChart.Series outputSeries = new XYChart.Series();
        // 设置支出名称
        outputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        // 循环天数，填充数据
        for (int i = 0; i < day; i++) {
            // 得到日历对象
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            Date calendarTime = calendar.getTime();
            // 将日期格式化
            String date = dateTools.dateFormat(calendarTime, "yyyy-MM-dd");
            // 拼接每天收入总额的SQL语句
            String dateInputsql = "select SUM(rMoney) from tb_records where rType='收入' and rDate= '" + date + "' and uId=" + Session.getUser().getUserId() + ";";
            // 拼接每天支出总额的SQL语句
            String dateOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and rDate= '" + date + "' and uId=" + Session.getUser().getUserId() + ";";
            // 执行SQL语句获取每天收入总额
            float dateInput = new RecordDao().getResultValueBySql(dateInputsql);
            // 执行SQL语句获取每天支出总额
            Object dateOutput = new RecordDao().getResultValueBySql(dateOutputsql);
            // 添加收入数据
            inputSeries.getData().add(new XYChart.Data<>(date, dateInput));
            // 添加支出数据
            outputSeries.getData().add(new XYChart.Data<>(date, dateOutput));
        }

        // 清空折线图上的数据
        lineChart.getData().clear();
        // 重新为折线图填充数据
        lineChart.getData().addAll(inputSeries, outputSeries);
    }

    /**
     * 操作结果：设置折线图的数据根据给定的月数
     *
     * @param month     给定的月数
     * @param lineChart 折线图
     */
    public void public_setMonthLineChartData(int month, LineChart lineChart, CategoryAxis categoryAxis,
                                             NumberAxis numberAxis) {
        categoryAxis.setLabel("日期");
        numberAxis.setLabel("金额");

        XYChart.Series monthInputSeries = new XYChart.Series();
        monthInputSeries.setName("收入");

        XYChart.Series monthOutputSeries = new XYChart.Series();
        monthOutputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        for (int i = 0; i < month; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(calendar.MONDAY, -i);
            Date monthDate = calendar.getTime();
            String monthStringDate = dateTools.dateFormat(monthDate, "yyyy-MM-dd");
            String monthInputsql = "select SUM(rMoney) from tb_records where rType='收入' and MONTH(rDate)= MONTH('" + monthStringDate + "') and uId=" + Session.getUser().getUserId() + ";";
            String monthOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and MONTH(rDate)= MONTH('" + monthStringDate + "') and uId=" + Session.getUser().getUserId() + ";";
            float monthInput = new RecordDao().getResultValueBySql(monthInputsql);
            float monthOutput = new RecordDao().getResultValueBySql(monthOutputsql);
            monthInputSeries.getData().add(new XYChart.Data<>(dateTools.dateFormat(monthDate, "yyyy-MM"), monthInput));
            monthOutputSeries.getData().add(new XYChart.Data<>(dateTools.dateFormat(monthDate, "yyyy-MM"), monthOutput));
        }

        lineChart.getData().clear();
        lineChart.getData().addAll(monthInputSeries, monthOutputSeries);
    }

    /**
     * 操作结果：设置折线图的数据根据给定的年（四个季度）
     *
     * @param lineChart    折线图
     * @param categoryAxis X轴
     * @param numberAxis   Y轴
     */
    public void public_setSeasonLineChartData(LineChart lineChart, CategoryAxis categoryAxis,
                                              NumberAxis numberAxis) {
        categoryAxis.setLabel("日期");
        numberAxis.setLabel("金额");
        XYChart.Series seasonInputSeries = new XYChart.Series();
        seasonInputSeries.setName("收入");

        XYChart.Series seasonOutputSeries = new XYChart.Series();
        seasonOutputSeries.setName("支出");

        // 清空集合日期标签中的内容
        categoryAxis.getCategories().clear();
        List<Date[]> list = dateTools.getYearQuarterList(new Date());
        for (int i = 0; i < list.size(); i++) {
            String strDate = dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "-" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd");
            categoryAxis.getCategories().add(strDate);
            String seasonInputSql = "select SUM(rMoney) from tb_records where rType='收入' and rDate between '" + dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "' and '" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd") + "' and uId=" + Session.getUser().getUserId();
            String seasonOutputSql = "select SUM(rMoney) from tb_records where rType='支出' and rDate between '" + dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "' and '" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd") + "' and uId=" + Session.getUser().getUserId();
            float seasonInput = new RecordDao().getResultValueBySql(seasonInputSql);
            float seasonOutput = new RecordDao().getResultValueBySql(seasonOutputSql);
            seasonInputSeries.getData().add(new XYChart.Data<>(strDate, seasonInput));
            seasonOutputSeries.getData().add(new XYChart.Data<>(strDate, seasonOutput));
        }
        lineChart.getData().clear();
        lineChart.getData().addAll(seasonInputSeries, seasonOutputSeries);
    }

    /**
     * 操作结果：设置饼图的数据根据起始日期和终止日期
     *
     * @param user      用户对象
     * @param pieChart  饼图
     * @param startDate 起止日期
     * @param endDate   终止日期
     */
    public void public_setDayPieChartData(User user, PieChart pieChart, Date startDate, Date endDate) {
        // 将开始日期格式化处理
        String thisStartDate = dateTools.dateFormat(startDate, "yyyy-MM-dd");
        // 将结束日期格式化处理
        String thisEndDate = dateTools.dateFormat(endDate, "yyyy-MM-dd");
        // 拼接收入总额SQL语句
        String thisInputsql = "select SUM(rMoney) from tb_records where rType='收入' and rDate between '" + thisStartDate + "'" + " " + "and " + "'" + thisEndDate + "' and uId=" + user.getUserId() + ";";
        // 拼接支出总额SQL语句
        String thisOutputsql = "select SUM(rMoney) from tb_records where rType='支出' and rDate between '" + thisStartDate + "'" + " " + "and " + "'" + thisEndDate + "' and uId=" + user.getUserId() + ";";
        // 执行SQL语句获取收入总额
        float thisInput = new RecordDao().getResultValueBySql(thisInputsql);
        // 执行SQL语句获取支出总额
        float thisOutput = new RecordDao().getResultValueBySql(thisOutputsql);
        // 填充数据到ObservableList集合中
        ObservableList observableList = FXCollections.observableArrayList(
                new PieChart.Data("收入", thisInput),
                new PieChart.Data("支出", thisOutput)
        );

        // 顺时针设置饼图的切片
        pieChart.setClockwise(true);
        // 方法设置标签行的长度
        pieChart.setLabelLineLength(50);
        // 将饼图的标签设置为可见
        pieChart.setLabelsVisible(true);
        // 设置饼图的起始角度
        pieChart.setStartAngle(180);

        // 清空饼图的数据
        pieChart.getData().clear();
        // 填充数据集合到饼图中
        pieChart.setData(observableList);
    }

    /**
     * 操作结果：设置饼图的数据根据给定的天数
     *
     * @param user     用户对象
     * @param day      给定的天数
     * @param pieChart 饼图
     * @param type     账目类型，收入或支出
     */
    public void public_setWeekPieChartData(User user, int day, PieChart pieChart, String type) {
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 创建ObservableList集合
        ObservableList observableList = FXCollections.observableArrayList();
        // 循环遍历天数
        for (int i = 0; i < day; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            Date calendarTime = calendar.getTime();
            String date = dateTools.dateFormat(calendarTime, "yyyy-MM-dd");
            String dateInputsql = "select SUM(rMoney) from tb_records where rType='" + type + "' and rDate= '" + date + "' and " + "uId=" + user.getUserId() + ";";
            float dateInput = recordDao.getResultValueBySql(dateInputsql);
            observableList.add(new PieChart.Data(date, dateInput));
        }

        // 设置饼图的标题
        pieChart.setTitle("饼图");
        // 顺时针设置饼图的切片
        pieChart.setClockwise(true);
        // 方法设置标签行的长度
        pieChart.setLabelLineLength(50);
        // 将饼图的标签设置为可见
        pieChart.setLabelsVisible(true);
        // 设置饼图的起始角度
        pieChart.setStartAngle(180);

        pieChart.getData().clear();
        pieChart.setData(observableList);
    }

    /**
     * 操作结果：设置饼图的数据根据给定的月数
     *
     * @param month    给定的月数
     * @param pieChart 饼图
     * @param type     指定账目类型，收入或支出
     */
    public void public_setMonthPieChartData(User user, int month, PieChart pieChart, String type) {
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 获取ObservableList集合对象
        ObservableList observableList = FXCollections.observableArrayList();
        for (int i = 0; i < month; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(calendar.MONDAY, -i);
            Date monthDate = calendar.getTime();
            String monthStringDate = dateTools.dateFormat(monthDate, "yyyy-MM");
            String monthsql = "select SUM(rMoney) from tb_records where rType='" + type + "' and MONTH(rDate)= MONTH" + "('" + dateTools.dateFormat(monthDate, "yyyy-MM-dd") + "') and uId=" + user.getUserId() + ";";
            Object monthResult = recordDao.getResultValueBySql(monthsql);
            observableList.add(new PieChart.Data(monthStringDate, (Float) monthResult));
        }

        // 设置饼图的标题
        pieChart.setTitle("饼图");
        // 顺时针设置饼图的切片
        pieChart.setClockwise(true);
        // 方法设置标签行的长度
        pieChart.setLabelLineLength(50);
        // 将饼图的标签设置为可见
        pieChart.setLabelsVisible(true);
        // 设置饼图的起始角度
        pieChart.setStartAngle(180);

        pieChart.getData().clear();
        pieChart.setData(observableList);
    }

    /**
     * 操作结果：设置饼图的数据根据季度
     *
     * @param pieChart 饼图
     * @param type     指定类型
     */
    public void public_setSeasonPieChartData(User user, PieChart pieChart, String type) {
        ObservableList observableList = FXCollections.observableArrayList();
        List<Date[]> list = dateTools.getYearQuarterList(new Date());
        for (int i = 0; i < list.size(); i++) {
            String strDate = dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "-" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd");
            String seasonSql = "select SUM(rMoney) from tb_records where rType='" + type + "' and rDate between '" + dateTools.dateFormat(list.get(i)[0], "yyyy-MM-dd") + "' and '" + dateTools.dateFormat(list.get(i)[1], "yyyy-MM-dd") + "' and uId=" + user.getUserId() + ";";
            Object season = new RecordDao().getResultValueBySql(seasonSql);
            observableList.add(new PieChart.Data(strDate, (Float) season));
        }

        // 设置饼图的标题
        pieChart.setTitle("饼图");
        // 顺时针设置饼图的切片
        pieChart.setClockwise(true);
        // 方法设置标签行的长度
        pieChart.setLabelLineLength(50);
        // 将饼图的标签设置为可见
        pieChart.setLabelsVisible(true);
        // 设置饼图的起始角度
        pieChart.setStartAngle(180);

        pieChart.getData().clear();
        pieChart.getData().addAll(observableList);
    }
}
