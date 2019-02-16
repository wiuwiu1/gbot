package com.projektg.wiuwiu1.gbot;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

public class Init {
    
    static Bot bot;

    public static void main(String[] args){
        try {
            Gson gson = new Gson();
            System.out.println("creating bot..");
            bot = gson.fromJson(new FileReader("conf.json"), Bot.class);
            System.out.println("starting bot (" + bot.toString() + ")...");
        } catch (IOException e){
            initJson();
        } catch (NullPointerException e){
            initJson();
        } 
        bot.init();
    }
    
    private static void initJson(){
        System.out.println("conf.json dont exists!");
            System.out.println("building missing conf.json...");
            try {
                Writer writer = new FileWriter("conf.json");
                Gson gson = new Gson();
                bot = new Bot("127.0.0.1", "G-Bot", "serveradmin", "XfVarJcz", "1"); //eigentlich von der standart jason erstellen lassen
                gson.toJson(bot, writer);
            } catch (IOException ee){
                System.out.println("Error: coudnt build config json");
                System.out.println("Shutdown");
                System.exit(0);
            } 
    }
}
