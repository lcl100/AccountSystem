package AccountSystem.dao;

import AccountSystem.bean.Classification;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassificationDao {

    /**
     * 通过收入或支出类型获取所有的分类信息
     *
     * @param classificationType 收入或支出分类
     * @return 返回得到的分类信息
     */
    public List<Classification> selectByType(String classificationType) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Classification> classificationList = new ArrayList<Classification>();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "select * from tb_classification where cType='" + classificationType + "';";
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Classification classification = new Classification();
                classification.setcId(rs.getInt(1));
                classification.setcName(rs.getString(2));
                classification.setcType(rs.getString(3));
                classificationList.add(classification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return classificationList;
    }

    /**
     * 添加分类
     *
     * @param classification 分类对象
     * @return 如果添加成功则返回true，否则返回false
     */
    public boolean addClassification(Classification classification) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "insert into tb_classification(cName,cType) values('" + classification.getcName() + "','" + classification.getcType() + "');";
            // 发送SQL语句，返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }

    /**
     * 删除分类
     *
     * @param classification 分类对象
     * @return 如果删除成功则返回true，否则返回false
     */
    public boolean deleteClassification(Classification classification) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "delete from tb_classification where cName='" + classification.getcName() + "';";
            // 发送SQL语句，返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }

    /**
     * 更新分类
     *
     * @param newName 新的分类名称
     * @param oldName 原来的分类名称
     * @return 如果更新成功则返回true，否则返回false
     */
    public boolean updateClassification(String newName, String oldName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "update tb_classification set cName='" + newName + "' where cName='" + oldName + "';";
            // 发送SQL语句，返回受影响行数
            num = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return num > 0;
    }
}
