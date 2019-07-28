/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author wiuwiu1
 */
public class DBController {
    
    private Connection conn = null;
    private String url;
    
    public DBController(String url){
        this.url = url;
    }
    
    public void connect(){
        try {
            this.conn = DriverManager.getConnection(this.url);
            System.out.println("SQLite connection has been established");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void close(){
        try{
            if(conn != null){
               conn.close(); 
            } 
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet executeSQL(String sql){
        try{
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
