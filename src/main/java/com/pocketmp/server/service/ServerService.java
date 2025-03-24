
package com.pocketmp.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.File;
import java.io.IOException;

public class ServerService extends Service {
    private Process serverProcess;
    private boolean isRunning = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startServer() {
        if (!isRunning) {
            try {
                ProcessBuilder pb = new ProcessBuilder("php", "PocketMine-MP.phar");
                pb.directory(new File(getFilesDir(), "server"));
                serverProcess = pb.start();
                isRunning = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        if (isRunning && serverProcess != null) {
            serverProcess.destroy();
            isRunning = false;
        }
    }
}
