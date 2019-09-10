package com.projektg.wiuwiu1.gbot.scripts.period;

import com.github.theholywaffle.teamspeak3.TS3Api;
import java.util.Timer;
import java.util.TimerTask;

abstract class BasePeriodScript implements Runnable {

    protected final TS3Api api;
    private final long period;

    public BasePeriodScript(TS3Api api, long period) {
        this.api = api;
        this.period = period;
    }

    @Override
    public void run() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                execute();
            }
        };
        new Timer().schedule(task, period, period);
    }

    abstract void execute();

}
