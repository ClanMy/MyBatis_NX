package generation;

/*
* 只有一个查询数据库所有表的方法,
* 就用JDBC写了,
* 懒得用框架
* */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectTable {

    public List<String> select(DatabaseInformation datainf) {
        //拼装数据库地址
        String url = "jdbc:mysql://" + datainf.getLibraryURL() + "/" + datainf.getLibraryName() + "?useUnicode=true&characterEncoding=utf8";

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<String> list = new ArrayList<>();

        try {
            //加载驱动
            Class.forName(datainf.getDriverClass());
            //连接数据库
            connection = DriverManager.getConnection(url, datainf.getUserId(), datainf.getPassword());
            //创建执行语句对象
            statement = connection.createStatement();
            // 编写sql语句
            String sql = "show tables";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // 循环输出 判断是否有下一行
            while (resultSet.next()) {
                //以列显示
                String tabalename = resultSet.getString(1);
                list.add(tabalename);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}

