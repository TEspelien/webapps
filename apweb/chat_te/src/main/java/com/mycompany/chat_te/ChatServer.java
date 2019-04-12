/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat_te;

import static spark.Spark.*;

/**
 *
 * @author tespelien
 */
public class ChatServer {

    public static void main(String[] args) {
        staticFiles.location("static/");

        get("/hello", (req, res) -> "hello world");
    }

}
