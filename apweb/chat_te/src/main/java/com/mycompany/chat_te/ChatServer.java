/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat_te;

import java.util.ArrayList;
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
    }

    public ArrayList<String> messages = new ArrayList<String>();

    public void postMessageFromRequest(spark.Request req) {
        messages.add(req.body());
    }

    public String retrieveAllMessages() {
        String allMessages = "";
        for (String s : messages) {
            allMessages += "<br>\n" + s;
        }
        return allMessages;
    }

    public static void verifyLoggedIn(spark.Request req){
        Context ctx = getContext(req);
        if(ctx.initials == null){
            halt(401, "You have been banned for malicious behaviour.");
        }
    }
    
    //get a context or make one from a request
    public static Context getContext(spark.Request req){
        Context ctx = req.session().attribute("context");
        if (ctx == null){
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
