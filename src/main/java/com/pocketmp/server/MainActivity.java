
package com.pocketmp.server;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            
            switch (item.getItemId()) {
                case R.id.navigation_setup:
                    fragment = new SetupFragment();
                    break;
                case R.id.navigation_console:
                    fragment = new ConsoleFragment();
                    break;
                case R.id.navigation_plugins:
                    fragment = new PluginsFragment();
                    break;
                case R.id.navigation_files:
                    fragment = new FilesFragment();
                    break;
            }

            if (fragment != null) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
                return true;
            }
            return false;
        });

        // Initial setup check and server start
        checkFirstRun();
    }

    private void checkFirstRun() {
        File serverDir = new File(getFilesDir(), "server");
        if (!serverDir.exists()) {
            serverDir.mkdirs();
            
            // Download PocketMine-MP
            downloadFile("https://github.com/pmmp/PocketMine-MP/releases/latest/download/PocketMine-MP.phar",
                        new File(serverDir, "PocketMine-MP.phar"));
            
            // Download PHP runtime
            downloadFile("https://jenkins.pmmp.io/job/PHP-8.0-Aggregate/lastSuccessfulBuild/artifact/PHP-8.0-Linux-x86_64.tar.gz",
                        new File(serverDir, "php.tar.gz"));
            
            // Extract PHP
            try {
                Runtime.getRuntime().exec("tar xzf php.tar.gz", null, serverDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Buat server.properties default
            createDefaultServerProperties(serverDir);
        }
    }

    private void downloadFile(String url, File destination) {
        try {
            URL fileUrl = new URL(url);
            FileUtils.copyURLToFile(fileUrl, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultServerProperties(File serverDir) {
        Properties props = new Properties();
        props.setProperty("server-name", "PocketMine-MP Server");
        props.setProperty("gamemode", "survival");
        props.setProperty("difficulty", "normal");
        props.setProperty("allow-flight", "off");
        props.setProperty("announce-player-achievements", "on");
        props.setProperty("spawn-protection", "16");
        props.setProperty("max-players", "20");
        props.setProperty("allow-nether", "on");
        props.setProperty("white-list", "off");
        
        try (FileOutputStream out = new FileOutputStream(new File(serverDir, "server.properties"))) {
            props.store(out, "PocketMine-MP Server Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
