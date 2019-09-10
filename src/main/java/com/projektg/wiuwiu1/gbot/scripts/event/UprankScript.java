package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;
import static com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType.REGULAR;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import com.projektg.wiuwiu1.gbot.utilities.DBController;
import com.projektg.wiuwiu1.gbot.utilities.Logger;
import com.projektg.wiuwiu1.gbot.utilities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class UprankScript extends BaseEventScript {

    private final String DEFAULT_SERVER_GROUP = "Guest";
    private final String UPRANK_SERVER_GROUP = "Normal";
    private HashMap<Integer, User> uprankers = new HashMap<Integer, User>();
    private DBController controll = null;
    private Boolean isInit = false;
    private final int MIN_TIME = 10;

    public UprankScript(TS3Api api) {
        super(api);
    }

    public void runJoin(ClientJoinEvent cje) {
        System.out.println("User joint:" + cje.getClientId());
        for (Client cl : api.getClients()) {
            if (cl.getId() == cje.getClientId()) {
                for (ServerGroup sg : api.getServerGroupsByClient(cl)) {
                    if (DEFAULT_SERVER_GROUP.equals(sg.getName())) {
                        uprankers.put(cl.getId(), new User(cl.getId(), new Date(), cl.getDatabaseId()));
                    }
                }
            }
        }

    }

    public void runLeave(ClientLeaveEvent cle) {
        User cl = uprankers.get(cle.getClientId());
        if (cl instanceof User) {
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
                    if (totalTime > MIN_TIME) {
                        doUprank(cl);
                    } else {
                        controll.executeSQL("INSERT INTO TSUsers (ClientDatabaseId, ConnectionTime) VALUES (" + cl.getClientDatabaseId() + ", " + getTimeDiff(cl.getJoinDate(), new Date()) + ");");
                    }
                } else {
                    int totalTime = getTimeDiff(cl.getJoinDate(), new Date()) + rs.getInt("ConnectionTime");
                    if (totalTime > MIN_TIME) {
                        doUprank(cl);
                    } else {
                        controll.executeSQL("UPDATE TSUsers SET ConnectionTime = " + totalTime + " WHERE ClientDatabaseId = " + cl.getClientDatabaseId() + ";");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.uprankers.remove(cl.getClientId());
            controll.close();
        }
    }

    private void initDB() {
        ResultSet rs = controll.executeSQL("SELECT name FROM sqlite_master WHERE type='table' AND name='TSUsers';");

        try {
            if (rs.next() == false) {
                Logger.log("Uprank Script", "no SQLite table found. creating...");
                controll.executeSQL("CREATE TABLE TSUsers (UserID INTEGER PRIMARY KEY, ClientDatabaseId INTEGER NOT NULL, ConnectionTime INTEGER);");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.isInit = true;
    }

    private int getTimeDiff(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / 1000);
    }

    private void doUprank(User cl) {
        Logger.log("Uprank Script", "User (ClientDatabaseId: " + cl.getClientDatabaseId() + ") achieved the uprank");
        api.getServerGroups().stream().filter((group) -> (group.getName().equalsIgnoreCase(UPRANK_SERVER_GROUP) && group.getType() == REGULAR)).forEachOrdered((item) -> {
            api.addClientToServerGroup(item.getId(), cl.getClientDatabaseId());
        });
        controll.executeSQL("DELETE FROM TSUsers WHERE ClientDatabaseId = " + cl.getClientDatabaseId() + ";");
    }

    @Override
    public void run() {
    }

}
