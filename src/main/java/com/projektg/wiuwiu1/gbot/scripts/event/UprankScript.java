/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;
import static com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType.REGULAR;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import com.projektg.wiuwiu1.gbot.utilities.DBController;
import com.projektg.wiuwiu1.gbot.utilities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wiuwiu1
 */
public class UprankScript extends BaseEventScript {

    private final String defaultServerGroup = "Guest";
    private final String uprankServerGroup = "Normal";
    private HashMap<Integer, User> uprankers = new HashMap<Integer, User>();
    private DBController controll = null;
    private Boolean isInit = false;
    private final int minTime = 10;

    public UprankScript(TS3Api api) {
        super(api);
    }

    public void runJoin(ClientJoinEvent cje) {
        System.out.println("Join:" + cje.getClientId());
        for (Client cl : api.getClients()) {
            if (cl.getId() == cje.getClientId()) {
                for (ServerGroup sg : api.getServerGroupsByClient(cl)) {
                    if (sg.getName().equalsIgnoreCase(defaultServerGroup)) {
                        uprankers.put(cl.getId(), new User(cl.getId(), new Date(), cl.getDatabaseId()));
                    }
                }
            }
        }

    }

    public void runLeave(ClientLeaveEvent cle) {
        User cl = uprankers.get(cle.getClientId());
        if (cl instanceof User) {
            System.out.println("User left: " + cl.getClientDatabaseId());
            if (controll == null) {
                controll = new DBController("jdbc:sqlite:user.db");
                controll.connect();
                if (!this.isInit) {
                    initDB();
                }
            }
            ResultSet rs = controll.executeSQL("SELECT ConnectionTime FROM TSUsers WHERE ClientDatabaseId = " + cl.getClientDatabaseId() + ";");

            try {
                if (rs.next() == false) {
                    int totalTime = getTimeDiff(cl.getJoinDate(), new Date());
                    if (totalTime > minTime) {
                        doUprank(cl);
                    } else {
                        System.out.println("User not found! Creating...");
                        controll.executeSQL("INSERT INTO TSUsers (ClientDatabaseId, ConnectionTime) VALUES (" + cl.getClientDatabaseId() + ", " + getTimeDiff(cl.getJoinDate(), new Date()) + ");");
                    }
                } else {
                    int totalTime = getTimeDiff(cl.getJoinDate(), new Date()) + rs.getInt("ConnectionTime");
                    if (totalTime > minTime) {
                        doUprank(cl);
                    } else {
                        System.out.println("New total connection time is set: ClientDatabaseId: " + cl.getClientDatabaseId() + " - totalTime: " + totalTime);
                        controll.executeSQL("UPDATE TSUsers SET ConnectionTime = " + totalTime + " WHERE ClientDatabaseId = " + cl.getClientDatabaseId() + ";");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.uprankers.remove(cl.getClientId());
        }
    }

    private void initDB() {
        ResultSet rs = controll.executeSQL("SELECT name FROM sqlite_master WHERE type='table' AND name='TSUsers';");

        try {
            if (rs.next() == false) {
                System.out.println("No SQLite Table found! Creating...");
                controll.executeSQL("CREATE TABLE TSUsers (UserID INTEGER PRIMARY KEY, ClientDatabaseId INTEGER NOT NULL, ConnectionTime INTEGER);");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.isInit = true;
    }

    private int getTimeDiff(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / 1000);
    }

    private void doUprank(User cl) {
        System.out.println("User (ClientDatabaseId: " + cl.getClientDatabaseId() + ") achieved the uprank");
        api.getServerGroups().stream().filter((group) -> (group.getName().equalsIgnoreCase(uprankServerGroup) && group.getType() == REGULAR)).forEachOrdered((item) -> {
            api.addClientToServerGroup(item.getId(), cl.getClientDatabaseId());
        });
        controll.executeSQL("DELETE FROM TSUsers WHERE ClientDatabaseId = " + cl.getClientDatabaseId() + ";");
    }

}
