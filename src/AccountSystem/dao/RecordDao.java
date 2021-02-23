package AccountSystem.dao;

import AccountSystem.bean.Record;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    /**
     * 添加账目记录
     *
     * @param record 账目记录
     * @return 如果添加成功则返回true，否则返回false
     */
    public boolean addRecord(Record record) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            // 获得数据的连接
            conn = JDBCUtils.getConnection();
            // 获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "insert into tb_records (uId,rType,rMoney,rClassification,rMemo,rDate) values(" +
                    record.getUserId() + ",'" +
                    record.getRecordType() + "'," +
                    record.getRecordMoney() + ",'" +
                    record.getRecordClassification() + "','" +
                    record.getRecordMemo() + "','" +
                    record.getRecordDate() + "');";
            // 执行插入语句，返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }

    /**
     * 查询指定用户的所有账目记录
     *
     * @param userId 指定的用户id
     * @return 返回查询结果集
     */
    public List<Record> selectByUserId(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Record> recordList = new ArrayList<>();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼装SQL语句
            String sql = "select * from tb_records where uId=" + userId;
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            // 循环添加数据
            while (rs.next()) {
                Record record = new Record();
                record.setRecordId(rs.getInt(1));
                record.setUserId(rs.getInt(2));
                record.setRecordType(rs.getString(3));
                record.setRecordMoney(rs.getFloat(4));
                record.setRecordClassification(rs.getString(5));
                record.setRecordMemo(rs.getString(6));
                record.setRecordDate(rs.getString(7));
                recordList.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return recordList;
    }

    /**
     * 查询账目记录通过账目记录id和用户id
     *
     * @param recordId 账目记录id
     * @param userId   用户id
     * @return 返回查询到的账目记录
     */
    public Record selectRecordByIdAndUserId(int recordId, int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Record record = new Record();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            String sql = "select * from tb_records where rId=" + recordId + " and  uId=" + userId + ";";
            // 执行查询
            rs = stmt.executeQuery(sql);
            // 循环结果
            while (rs.next()) {
                record.setRecordId(rs.getInt(1));
                record.setUserId(rs.getInt(2));
                record.setRecordType(rs.getString(3));
                record.setRecordMoney(rs.getFloat(4));
                record.setRecordClassification(rs.getString(5));
                record.setRecordMemo(rs.getString(6));
                record.setRecordDate(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return record;
    }

    /**
     * 删除账目记录
     *
     * @param record 账目记录
     * @return 如果删除成功则返回true，否则返回false
     */
    public boolean deleteRecord(Record record) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            String sql = "delete from tb_records where rId=" + record.getRecordId() + ";";
            // 返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }

    /**
     * 更新账目记录
     *
     * @param record 账目记录
     * @return 如果更新成功则返回true，否则返回false
     */
    public boolean updateRecord(Record record) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            String sql = "update tb_records set rType='" + record.getRecordType() + "',rMoney=" + record.getRecordMoney() + ",rClassification='" + record.getRecordClassification() + "',rMemo='" + record.getRecordMemo() + "',rDate='" + record.getRecordDate() + "' where rId=" + record.getRecordId() + ";";
            // 执行更新，返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }

    /**
     * 获取指定用户支出或收入的总额
     *
     * @param recordType 记录类型，支出或收入
     * @param userId     用户id
     * @return 返回用户支出或收入的总额
     */
    public float getTotalAccount(String recordType, int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        float total = 0f;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "select SUM(rMoney) from tb_records where rType='" + recordType + "' and  uId=" + userId + ";";
            // 发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return total;
    }

    /**
     * 根据SQL条件语句获取唯一结果值
     *
     * @param sql SQL语句
     * @return 返回结果
     */
    public float getResultValueBySql(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        float resultValue = 0f;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                resultValue = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return resultValue;
    }

    /**
     * 根据SQL条件语句获取账目记录
     *
     * @param sql SQL语句
     * @return 返回查询结果集
     */
    public List<Record> getRecordBySql(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Record> recordList = new ArrayList<Record>();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Record record = new Record();
                record.setRecordId(rs.getInt(1));
                record.setUserId(rs.getInt(2));
                record.setRecordType(rs.getString(3));
                record.setRecordMoney(rs.getFloat(4));
                record.setRecordClassification(rs.getString(5));
                record.setRecordMemo(rs.getString(6));
                record.setRecordDate(rs.getString(7));
                recordList.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return recordList;
    }

    /**
     * 操作结果：在删除数据后，刷新主键编号
     */
    public void refreshPrimaryKey() {
        String refreshSQL1 = "alter table tb_records drop rId;";
        String refreshSQL2 = "alter table tb_records add rId int not null FIRST;";
        String refreshSQL3 = "alter table tb_records modify column rId int not null AUTO_INCREMENT,add primary key" +
                "(rId);";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            stmt.executeUpdate(refreshSQL1);
            stmt.executeUpdate(refreshSQL2);
            stmt.executeUpdate(refreshSQL3);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(stmt, conn);
        }

    }

}
