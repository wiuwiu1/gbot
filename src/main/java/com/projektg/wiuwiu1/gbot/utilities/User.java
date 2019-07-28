/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.utilities;

import java.util.Date;

/**
 *
 * @author wiuwiu1
 */
public class User {
    private int clientId;
    private Date joinDate;
    private int clientDatabaseId;
    
    public User(int clientId, Date joinDate, int clientDatabaseId){
        this.clientId = clientId;
        this.joinDate = joinDate;
        this.clientDatabaseId = clientDatabaseId;
    }

    public int getClientId() {
        return clientId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public int getClientDatabaseId() {
        return clientDatabaseId;
    }
    
    
}
