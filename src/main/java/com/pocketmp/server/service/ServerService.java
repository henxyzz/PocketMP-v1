
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case "START_SERVER":
                        startServer();
                        break;
                    case "STOP_SERVER":
                        stopServer();
                        break;
                    case "SEND_COMMAND":
                        String command = intent.getStringExtra("command");
                        sendCommand(command);
                        break;
                }
            }
        }
        return START_STICKY;
    }

    public void startServer() {
        if (!isRunning) {
            try {
                ProcessBuilder pb = new ProcessBuilder("./bin/php7/bin/php", "PocketMine-MP.phar");
                pb.directory(new File(getFilesDir(), "server"));
                pb.redirectErrorStream(true);
                serverProcess = pb.start();
                isRunning = true;

                // Baca output server
                new Thread(() -> {
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(serverProcess.getInputStream()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            broadcastConsoleUpdate(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        if (isRunning && serverProcess != null) {
            sendCommand("stop");
            try {
                serverProcess.waitFor(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverProcess.destroy();
            isRunning = false;
        }
    }

    private void sendCommand(String command) {
        if (isRunning && serverProcess != null) {
            try {
                OutputStream out = serverProcess.getOutputStream();
                out.write((command + "\n").getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastConsoleUpdate(String line) {
        Intent intent = new Intent("CONSOLE_UPDATE");
        intent.putExtra("line", line);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
