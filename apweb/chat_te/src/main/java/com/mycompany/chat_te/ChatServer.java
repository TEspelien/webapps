/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat_te;

import java.util.ArrayList;
import javax.servlet.MultipartConfigElement;
import spark.Request;
import static spark.Spark.*;

/**
 *
 * @author tespelien
 */
public class ChatServer {

    public static void main(String[] args) {

        staticFiles.location("static/");

        before("*", (req, res) -> {
            System.out.println("request coming in: " + req.requestMethod() + ":" + req.url());
        });
        get("/hello", (req, res) -> "hello world");
        get("/factorial", (req, res) -> factorial(req));
        get("/login", (req, res) -> login(req));
        get("/retrieve", (req, res) -> retrieveNewMessages(req));
        post("/post", (req, res) -> postMessage(req));
    }

    public static String login(spark.Request req) {
        Context ctx = getContext(req);
        ctx.initials = req.queryParams("initials");
        ctx.numRead = 0;
        return (ctx.initials != null ? "Success!" : "Unregistered login");
    }

    final static ArrayList<String> messages = new ArrayList<String>();

    public static String postMessage(spark.Request req) {
        verifyLoggedIn(req);
        Context ctx = getContext(req);
        System.out.println("postMessage called in the server successfully");
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
        req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        String str = ctx.initials + ": " + req.queryParams("text");
        System.out.println(str);
        synchronized (messages) {
            messages.add(str);
        }
        ctx.numRead++;
        return str;
    }

    public static String retrieveNewMessages(spark.Request req) {
        verifyLoggedIn(req);
        String str = "";
        synchronized (messages) {
            int n = getContext(req).numRead;
            System.out.println("numRead is " + n);
            System.out.println("there are " + messages.size() + " messages present");
            for (int i = n; i < messages.size(); i++) {
                str += "\n \n" + messages.get(i);
                getContext(req).numRead++;
            }
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
