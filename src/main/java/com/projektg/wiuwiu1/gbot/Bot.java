package com.projektg.wiuwiu1.gbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.projektg.wiuwiu1.gbot.scripts.event.UprankScript;
import com.projektg.wiuwiu1.gbot.scripts.event.WelcomeMessage;
import com.projektg.wiuwiu1.gbot.scripts.period.FileRemover;
import com.projektg.wiuwiu1.gbot.utilities.CommandListender;
import com.projektg.wiuwiu1.gbot.utilities.Logger;

public class Bot {

    private final String ip;
    private final String nickname;
    private final String username;
    private final String pw;
    private final String vsId;

    public Bot(String ip, String nickname, String username, String pw, String vsId) {
        this.ip = ip;
        this.nickname = nickname;
        this.username = username;
        this.pw = pw;
        this.vsId = vsId;
    }

    public void init() {
        final TS3Config config = new TS3Config();
        config.setHost(this.ip);

        final TS3Query query = new TS3Query(config);
        try {
            query.connect();
        } catch (TS3ConnectionFailedException e) {
            Logger.log("System", "ERROR: failed to connect to teamspeak server");
            stop();
        }
        Logger.log("System", "bot has connected to query");

        final TS3Api api = query.getApi();
        api.login(username, pw);
        api.selectVirtualServerById(1); //vsid anstatt 1
        api.setNickname(this.nickname);
        api.sendChannelMessage(this.nickname + " is online!");

        CommandListender cl = new CommandListender(this);
        cl.start();
        initEventScripts(api);
        initTimedScripts(api);
    }

    public void stop() {
        Logger.log("System", "shutdown");
        Logger.exportLog();
        System.exit(0);
    }

    @Override
    public String toString() {
        return "ip: " + ip + " nickname: " + nickname + " username: " + username + " pw: " + pw + " vsId: " + vsId;
    }

    private void initEventScripts(final TS3Api api) {
        api.registerAllEvents(); // nur nötige events sollen importet werden
        TS3Listener listeners = new TS3Listener() {
            private UprankScript uprankScript = new UprankScript(api);

            @Override
            public void onTextMessage(TextMessageEvent tme) {

            }

            @Override
            public void onClientJoin(ClientJoinEvent cje) {
                new WelcomeMessage(api, cje).run();
                uprankScript.runJoin(cje);
            }

            @Override
            public void onClientLeave(ClientLeaveEvent cle) {
                uprankScript.runLeave(cle);
            }

            @Override
            public void onServerEdit(ServerEditedEvent see) {

            }

            @Override
            public void onChannelEdit(ChannelEditedEvent cee) {

            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent cdee) {

            }

            @Override
            public void onClientMoved(ClientMovedEvent cme) {

            }

            @Override
            public void onChannelCreate(ChannelCreateEvent cce) {

            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent cde) {

            }

            @Override
            public void onChannelMoved(ChannelMovedEvent cme) {

            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent cpce) {

            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent pkue) {

            }
        };
        api.addTS3Listeners(listeners);
    }

    private void initTimedScripts(final TS3Api api) {
        new FileRemover(api, 86400000).run();
    }
}
