package com.example.demo;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/stu?" +
                "autoReconnect=true&useUnicode=true&characterEncoding=utf-8&" +
                "useSSL=false&serverTimezone=GMT%2B8";
        String userName = "Celine";
        String password = "123456";
        Connection conn;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql;
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}