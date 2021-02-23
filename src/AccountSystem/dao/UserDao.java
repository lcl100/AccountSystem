package AccountSystem.dao;

import AccountSystem.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDao {
    private Connection connection = null;

    /**
     * 操作结果：实现按用户名与密码查询用户的方法
     *
     * @param userName 用户名
     * @param password 用户密码
     * @return Users Users对象
     */
    public User login(String userName, String password) {
        User user = new User();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from tb_users where uName=? and uPassword=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            // 执行查询
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserPassword(resultSet.getString(3));
                user.setUserImagePath(resultSet.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet, preparedStatement, connection);
        }
        return user;
    }

    /**
     * 实现用户注册
     *
     * @param user 用户
     * @return 用户注册成功返回true，否则返回false
     */
    public boolean register(User user) {
        PreparedStatement preparedStatement = null;
        int num = 0;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into tb_users(uName,uPassword,uImagePath) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserPassword());
            preparedStatement.setString(3, user.getUserImagePath());
            // 执行插入，返回受影响行数
            num = preparedStatement.executeUpdate();
            // 判断是否注册成功
            return num > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(preparedStatement, connection);
        }
        return false;
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户id
     * @return 返回查询到的用户信息
     */
    public User selectUserById(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        User user = new User();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼装SQL语句
            String sql = "select * from tb_users where uId=" + userId;
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            // 循环添加数据
            while (rs.next()) {
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setUserPassword(rs.getString(3));
                user.setUserImagePath(rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return user;
    }

    /**
     * 更新用户数据
     *
     * @param user 要更新的用户数据
     * @return 如果更新成功则返回true，否则返回false
     */
    public boolean updateUser(User user) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "update tb_users set uName='" + user.getUserName() + "',uPassword='" + user.getUserPassword() + "',uImagePath='" + user.getUserImagePath() + "' where uId=" + user.getUserId() + ";";
            //发送SQL语句，获取受影响行数
            int num = stmt.executeUpdate(sql);
            //判断是否更改成功
            return num > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
        return false;
    }
}
