package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;


abstract class BaseEventScript implements Runnable {

    protected TS3Api api;

    public BaseEventScript(TS3Api api) {
        this.api = api;
    }

    @Override
    public abstract void run();

}
