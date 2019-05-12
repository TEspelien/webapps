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

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
        req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        String tableName = req.queryParams("tableName");

        System.out.println("trying to access " + tableName + ".");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tableName); // select everything in the table
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = Math.min(3, rsmd.getColumnCount());
            //int numberOfColumns = rsmd.getColumnCount();
            String[][] tableArr = new String[50][numberOfColumns];
            System.out.println(tableArr.length + " tracks shown");
            int counter = 0;
            System.out.println("there are " + numberOfColumns + " columns");
            while (rs.next() && counter < 50) { // prints the id and first two columns of all rows
                for (int i = 0; i < numberOfColumns; i++) {
                    tableArr[counter][i] = rs.getString(i + 1) +"--";
                    //System.out.print(rs.getString(i + 1) + " ");//sql indexing starts at 1
                }
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
