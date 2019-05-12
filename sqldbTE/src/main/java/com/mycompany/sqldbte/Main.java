/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sqldbTE;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.MultipartConfigElement;
import spark.Request;
import static spark.Spark.*;
import java.sql.*;

/**
 *
 * @author tespelien
 */
public class Main {

    public static void main(String[] args) {
        staticFiles.location("static/");
        before("*", (req, res) -> {
            //System.out.println("request coming in: " + req.requestMethod() + ":" + req.url());
        });
        get("/hello", (req, res) -> "hello world");
        get("/dump", "application/json", (req, res) -> dumpTableFromInput(req, res), new JSONRT());

        connect();
    }

    static Connection conn = null;

    public static void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:chinook.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //need to test this method to make sure it reads the database correctly
    //read chatserver.java for notes on using JSON
    public static String[][] dumpTableFromInput(spark.Request req, spark.Response res) {
        System.out.println("1");
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
        req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        String tableName = req.queryParams("text");
        System.out.println(tableName);
        System.out.println("2");
        System.out.println("trying to access " + tableName + ".");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tableName); // select everything in the table
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            String[][] tableArr = new String[numberOfColumns][50];
            int counter = 0;
            while (rs.next() && counter < 50) { // prints the id and first two columns of all rows
                for (int i = 0; i < numberOfColumns; i++) {
                    tableArr[counter][numberOfColumns] = rs.getString(i);
                }
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
                counter++;
            }
            System.out.println(tableName + ":" + (new JSONRT()).render(tableArr));
            return tableArr;
        } catch (Exception e) {
            System.out.println("exception caught: " + e);
        }
        return null;
    }

    public static Context getContext(spark.Request req) {
        Context ctx = req.session().attribute("context");
        if (ctx == null) {
            ctx = new Context();
            req.session().attribute("context", ctx);
        }
        return ctx;
    }

}
