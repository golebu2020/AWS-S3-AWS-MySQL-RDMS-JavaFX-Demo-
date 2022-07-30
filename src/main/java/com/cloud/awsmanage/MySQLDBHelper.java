package com.cloud.awsmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDBHelper {
//    private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    private static final String CONNSTRING = "jdbc:mysql://" + "gtisma-database.cryku7jd8a0a.us-east-1.rds.amazonaws.com";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Nedu1234";


    public static void getConnection() throws ClassNotFoundException {
       // Class.forName(DRIVERNAME);
        // Open a connection
        try(Connection conn = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE STUDENTS";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectDB(String dbName){
        System.out.println("Connecting to a selected database...");
        try(Connection conn = DriverManager.getConnection(CONNSTRING+"/"+dbName, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            System.out.println("Connected Database Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String dbName){
        // Open a connection
        try(Connection conn = DriverManager.getConnection(CONNSTRING+"/"+dbName, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE TABLE REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertRecord(String dbName){
        try(Connection conn = DriverManager.getConnection(CONNSTRING+"/"+dbName, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            // Execute a query
            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO REGISTRATION VALUES (100, 'Zara', 'Ali')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO REGISTRATION VALUES (101, 'Mahnaz', 'Fatma')";
            stmt.executeUpdate(sql);

            System.out.println("Inserted records into the table...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
