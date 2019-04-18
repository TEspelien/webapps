/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat_te;

import java.util.ArrayList;
import spark.Request;
import static spark.Spark.*;

/**
 *
 * @author tespelien
 */
public class ChatServer {

    public static void main(String[] args) {
        staticFiles.location("static/");

        get("/hello", (req, res) -> "hello world");
        get("/factorial", (req, res) -> factorial(req));
        get("/login", (req, res) -> login(req));
        get("/retrieve", (req, res) -> retrieveNewMessages(req));
        //get("/send", (req, res) -> sendMessage());
    }

    public static String login(spark.Request req) {
        Context ctx = getContext(req);
        ctx.initials = req.queryParams("initials");
        return (ctx.initials != null ? "Success!" : "Unregistered login");
    }

    public static ArrayList<String> messages = new ArrayList<String>();

    public void postMessageFromRequest(spark.Request req) {
        messages.add(req.body());
    }

    public static String retrieveNewMessages(spark.Request req) {
        String str = "";
        int n = getContext(req).numRead;
        for (int i = n - 1; i < messages.size(); i++) {
            str += "<br>\n" + messages.get(i);
        }
        return str;
    }

    public static void verifyLoggedIn(spark.Request req) {
        Context ctx = getContext(req);
        if (ctx.initials == null) {
            halt(401, "You have been banned for a week due to malicious behaviour.");
        }
    }

    //get a context or make one from a request
    public static Context getContext(spark.Request req) {
        Context ctx = req.session().attribute("context");
        if (ctx == null) {
            ctx = new Context();
            req.session().attribute("context", ctx);
        }
        return ctx;
    }

    public static String factorial(spark.Request req) {
        int test;
        try {
            test = Integer.parseInt(req.queryParams("numbers"));
        } catch (Exception e) {
            return "parsing exception";
        }
        double res = 1;
        for (int i = 1; i <= test; i++) {
            res *= i;
        }

        return test + "! is " + res;
    }
}
