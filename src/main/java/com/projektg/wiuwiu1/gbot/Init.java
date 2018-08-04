package com.projektg.wiuwiu1.gbot;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

public class Init {

    public static void main(String[] args){
        try {
            Gson gson = new Gson();
            System.out.println("creating bot..");
            Bot bot = gson.fromJson(new FileReader("conf.json"), Bot.class);
            System.out.println("starting bot (" + bot.toString() + ")...");
            bot.init();
        } catch (IOException e){
            System.out.println("conf.json dont exists!");
        } finally {
            System.out.println("building missing conf.json...");
            initJson();
        }
    }

    private static boolean initJson(){
        try {
            Writer writer = new FileWriter("conf.json");
            Gson gson = new Gson();
            gson.toJson("", writer);
            // zurück zu try
        } catch (IOException e){
            System.out.println("Error: coudnt build config json");
        } finally {
            System.out.println("Shutdown");
            System.exit(0);
        }
        return true;
    }


}
