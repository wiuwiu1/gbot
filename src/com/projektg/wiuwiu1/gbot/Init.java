package com.projektg.wiuwiu1.gbot;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

public class Init {

    public static void main(String[] args){
        try {
            JsonReader reader = new JsonReader(new FileReader("conf.json"));
            Gson gson = new Gson();
            Bot bot = gson.fromJson(reader, Bot.class);
        } catch (IOException e){
            System.out.println("conf.json dont exists!");
        } finally {
            System.out.println("building conf.json...");
            initJson();
        }
    }

    private static boolean initJson(){
        try {
            Writer writer = new FileWriter("conf.json");
            Gson gson = new Gson();
            gson.toJson("", writer);
        } catch (IOException e){
            System.out.println("Error");
        } finally {

        }
        return true;
    }


}
