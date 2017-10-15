package org.xapek.andiodine.tasker;

import android.support.annotation.Nullable;
import android.util.Log;

import org.xapek.andiodine.config.IodineConfiguration;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by tmw on 14-10-17.
 */

public class TunnelStatus {
    static final String TAG = "TunnelStatusMutex";

    private static final Lock mutex = new ReentrantLock();

    private static final ArrayList<IodineConfiguration> activeTunnels = new ArrayList<>();

    public static boolean AddActive(IodineConfiguration iodineConfiguration) {
        try {
            mutex.lock();
            try {
                activeTunnels.add(iodineConfiguration);
            } finally {
                mutex.unlock();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error acquiring lock", e);
            return false;
        }
        return true;
    }

    public static boolean RemoveActive(IodineConfiguration iodineConfiguration) {
        try {
            mutex.lock();
            try {
                activeTunnels.remove(iodineConfiguration);
            } finally {
                mutex.unlock();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error acquiring lock", e);
            return false;
        }
        return true;
    }

    @Nullable
    static IodineConfiguration GetOne() {
        mutex.lock();
        final IodineConfiguration r;
        try {
            if (activeTunnels.isEmpty())
                r = null;
            else r = activeTunnels.get(0);
        } finally {
            mutex.unlock();
        }
        return r;
    }

    static IodineConfiguration[] GetAll() {
        mutex.lock();
        final IodineConfiguration[] objects;
        try {
            objects = activeTunnels.toArray(new IodineConfiguration[activeTunnels.size()]);
        } finally {
            mutex.unlock();
        }
        return objects;
    }
}
