package com.cdrussell.caster.rx.casterrxjava;


import android.os.SystemClock;
import android.util.Log;

class Database {

    private static final String TAG = Database.class.getSimpleName();

    static String readValue() {
        SystemClock.sleep(50);

        for (int i = 0; i < 100; i++) {
            Log.i(TAG, String.format("Reading value: %d%%", i));
            SystemClock.sleep(20);
        }
        Log.i(TAG, "Reading value: 100%");
        return "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.";
    }
}
