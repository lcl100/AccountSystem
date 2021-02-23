package AccountSystem.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleTools {
    /**
     * 操作结果：MD5加密
     *
     * @param str 需要加密的字符串
     * @return String 返回加密成功的字符串
     */
    public static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte b[] = md5.digest();

            StringBuffer sb = new StringBuffer("");
            for (int n = 0; n < b.length; n++) {
                int i = b[n];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            //32位加密
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 操作结果：自定义一个JavaFX的对话框
     *
     * @param alterType 对话框类型
     * @param title     对话框标题
     * @param header    对话框头信息
     * @param message   对话框显示内容
     * @return boolean 如果点击了”确定“那么就返回true，否则返回false
     */
    public static boolean informationDialog(Alert.AlertType alterType, String title, String header, String message) {
        // 按钮部分可以使用预设的也可以像这样自己 new 一个
        Alert alert = new Alert(alterType, message, new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        // 设置对话框的标题
        alert.setTitle(title);
        alert.setHeaderText(header);
        // showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> buttonType = alert.showAndWait();
        // 根据点击结果返回, 如果点击了“确定”就返回true
        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);
    }

    /**
     * 操作结果：读取配置属性文件
     *
     * @param propertiesFilePath 配置属性文件路径
     * @return String 根据键获取的内容值
     */
    static List readPropertiesFile(String propertiesFilePath) {
        Properties properties = new Properties();
        String returnValue;
        List list = new ArrayList();
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(inputStream);
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            while (iterator.hasNext()) {
                returnValue = properties.getProperty(iterator.next());
                list.add(returnValue);
            }
            inputStream.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 操作结果：操作结果：生成属性文件
     *
     * @param fileName 文件路径
     * @param maps     数据集合
     */
    public static void dataWriteProperties(String fileName, Map<String, String> maps) {
        Properties properties = new Properties();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 写入文件
            for (String key : maps.keySet()) {
                properties.setProperty(key, String.valueOf(maps.get(key)));
            }
            FileOutputStream fos = new FileOutputStream(file);
            // store(...)指定的流仍保持打开状态
            properties.store(fos, null);
            fos.flush();
            fos.close();// 关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 操作结果：将一段字符串按某种分隔符号分割成数组
     *
     * @param str  字符串
     * @param mark 分割标志，如逗号
     * @return String[] 字符串数组
     */
    public static String[] convertStrToArray(String str, String mark) {
        String[] array = null;
        array = str.split(mark);
        return array;
    }

    /**
     * Object类型转换为String类型，高阶类型向低阶转会提示错误
     *
     * @param obj
     * @return
     */
    public static String[][] objectTwoArrayToTwoArrayString(Object[][] obj) {
        String[][] str = new String[obj.length][obj[0].length];
        for (int i = 0; i < obj.length; i++) {
            for (int j = 0; j < obj[i].length; j++) {
                str[i][j] = obj[i][j].toString();
            }
        }
        return str;
    }

    /**
     * 操作结果：数组转换成List集合
     *
     * @param strings 数组
     * @return List 集合
     */
    public List arrayToList(String[] strings) {
        List list = new ArrayList();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    /**
     * 操作结果：将一维Object数组转换成一维String数组
     *
     * @param obj 一维Object数组
     * @return String[] 一维String数组
     */
    public static String[] objectOneArrayToOneArrayString(Object[] obj) {
        String[] array = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            array[i] = obj[i].toString();
        }
        return array;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        //判断单元格的类型
        switch (cell.getCellType()) {
            //字符串类型数据
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            //number类型数据
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date d = cell.getDateCellValue();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    strCell = format.format(d);
                } else {
                    strCell = String.valueOf(cell.getNumericCellValue());
                }
                break;
            //Boolean类型数据
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            //无数据内容
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            //除上述数据类型以外，均为以下内容
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("")) {
            return "";
        }
        return strCell;
    }

    /**
     * 读取excel数据内容（不包括标题行）
     *
     * @param is 文件输入流
     * @return String[][] 包含单元格数据内容的二维数组
     */
    public static String[][] readExcelContentArray(InputStream is) {
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCellStyle style = null;
        HSSFCell cell = null;
        String str = null;
        String[][] content = null;
        try {
            //实例化一个工作表对象
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到第一张表格
        sheet = wb.getSheetAt(0);
        //得到该表格的总行数（不包括标题行）
        int rowNum = sheet.getLastRowNum();
        //获取该工作表的第一行（下标是从0开始的）
        row = sheet.getRow(0);
        //获取第一行的列数
        int colNum = row.getPhysicalNumberOfCells();
        //正文内容应该从第二行开始，第一行为表头的标题
        //实例化一个数组来保存非标题行下的内容
        content = new String[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                //将具体行列的单元格内容写入str
                str = getStringCellValue(sheet.getRow(i + 1).getCell((short) j)).trim();
                content[i][j] = str;
            }
        }
        return content;
    }


    /**
     * 导出数据内容到excel中
     *
     * @param title  String[] 表头数据
     * @param values String[][] 表数据（非表头的）
     */
    public static String exportExcel(String[] title, String[][] values, String exportPath) {
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCellStyle style = null;
        HSSFCell cell = null;
        //第一步，创建一个HSSFWorkbook对象，对应一个excel文件
        wb = new HSSFWorkbook();
        //第二步，在wb添加一个sheet，对应excel文件中的sheet
        sheet = wb.createSheet("Sheet1");
        //第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        row = sheet.createRow(0);
        //第四步，创建单元格，并设置值表头 设置表头居中
        style = wb.createCellStyle();
        //创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //第五步，添加数据到表头
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //第六步，写入实体数据，实际应用中这些数据从数据库获得
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(values[i][j]);
            }
        }
        //第七步，输出excel文件
        Date date = new Date();
        //定义日期格式
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        //拼接文件名
        String name = exportPath + format.format(date) + ".xls";
        //创建一个文件字节输入流
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(name);
            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

}
