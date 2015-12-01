package com.qualcomm.ftcrobotcontroller.opmodes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.qualcomm.robotcore.util.BatteryChecker;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Eric Golde on 11/21/2015.
 */


/*
* ██████████████████████████████████████████████████████████████████████████████████████████████████████████
* DO NOT USE THIS FILE. IT DOES NOTHING AND IS USELESS. DONT DELETE IT BECAUSE IT MIGHT BE USEFULL LATER IN THE PROJECT BUT I DONT KNOW
* ██████████████████████████████████████████████████████████████████████████████████████████████████████████
* */


public class ECheckBatteryLevel{
    public Context b;
    public long c;
    public long d = 5000L;
    public BatteryChecker.BatteryWatcher e;
    Handler batteryHandler;
    Runnable a = new Runnable() {
        public void run() {
            float var1 = ECheckBatteryLevel.this.getBatteryLevel();
            ECheckBatteryLevel.this.e.updateBatteryLevel(var1);
            RobotLog.i("Battery Checker, Level Remaining: " + var1);
            ECheckBatteryLevel.this.batteryHandler.postDelayed(ECheckBatteryLevel.this.a, ECheckBatteryLevel.this.c);
        }
    };

    public ECheckBatteryLevel(Context context, BatteryChecker.BatteryWatcher watcher, long delay) {
        this.b = context;
        this.e = watcher;
        this.c = delay;
        this.batteryHandler = new Handler();
    }

    public float getBatteryLevel() {
        IntentFilter var1 = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent var2 = this.b.registerReceiver((BroadcastReceiver)null, var1);
        int var3 = var2.getIntExtra("level", -1);
        int var4 = var2.getIntExtra("scale", -1);
        int var5 = -1;
        if(var3 >= 0 && var4 > 0) {
            var5 = var3 * 100 / var4;
        }

        return (float)var5;
    }

    public void startBatteryMonitoring() {
        this.batteryHandler.postDelayed(this.a, this.d);
    }

    public void endBatteryMonitoring() {
        this.batteryHandler.removeCallbacks(this.a);
    }

    public interface BatteryWatcher {
        void updateBatteryLevel(float var1);
    }
}
