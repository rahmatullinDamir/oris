package org.example;

import java.sql.*;
import java.util.Scanner;

public class RahmatullinDamir {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from driver");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name") + " "
                    + result.getInt("age"));
        }

        ResultSet resultWithOptions = statement.executeQuery("select * from driver WHERE age > 10");
        System.out.println("result with option age > 10");

        while (resultWithOptions.next()) {
            System.out.println(resultWithOptions.getInt("id") + " " + resultWithOptions.getString("name") + " " +
                    resultWithOptions.getInt("age"));
        }

        String sqlInsertUser = "insert into driver(name, surname, age) values (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sqlInsertUser);

        for (int i = 0; i < 6; i++) {
            pstmt.setString(1, "User" + i);
            pstmt.setString(2, "Surname" + i);
            pstmt.setInt(3, 30 + i);
            pstmt.addBatch();
        }

        try {
            int[] affectedRows = pstmt.executeBatch();
            System.out.println("Добавлено " + affectedRows.length + " строк");
        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            pstmt.close();
        }

        connection.close();
    }
}