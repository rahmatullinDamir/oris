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
        Scanner scanner = new Scanner(System.in);

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

        String sql = "INSERT INTO driver(name, surname, age) VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        for (int i = 0; i < 6; i++) {
            String firstName = scanner.nextLine();
            String secondName = scanner.nextLine();
            int age = Integer.parseInt(scanner.nextLine());

            pstmt.setString(1 + i*3, firstName);
            pstmt.setString(2 + i*3, secondName);
            pstmt.setInt(3 + i*3, age);
        }

        pstmt.executeUpdate();
        pstmt.close();

        connection.close();
    }
}