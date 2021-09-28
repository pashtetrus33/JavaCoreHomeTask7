package lesson8;

import java.sql.*;

public class SqliteExamples {
    public static void main(String[] args) {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:geekbrains.db");
                Statement statement = connection.createStatement();
                statement.executeUpdate("update faculty set name = 'Test1' where id =1");
                 ResultSet resultSet= statement.executeQuery("select * from students");/*
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " " +
                    resultSet.getString("name") + " " +
                            resultSet.getInt("score") + " " +
                            resultSet.getInt("faculty_id"));
                }
                long time = System.currentTimeMillis();
                connection.setAutoCommit(false);
                for (int i=0; i<1000; i++) {
                    statement.executeUpdate(
                    String.format("insert into students (name,score,faculty_id) values ('%s', %d, %d)", "student" + i, i, i));
                }
                connection.commit();
                System.out.println("Время выполнения с автокомитом: " + (System.currentTimeMillis() -time));
                connection.close();*/
                PreparedStatement preparedStatement = connection.prepareStatement("insert into students (name, score, faculty_id) values (?,?,?)");
                connection.setAutoCommit(false);
                preparedStatement.setString(1,"Anton");
                preparedStatement.setInt(2, 3);
                preparedStatement.setInt(3,3);
                preparedStatement.addBatch();
                preparedStatement.setString(1,"Anton");
                preparedStatement.setInt(2, 3);
                preparedStatement.setInt(3,3);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
