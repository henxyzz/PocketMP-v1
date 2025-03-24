
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
        // TODO: Implement first run setup
        // - Download PocketMine-MP
        // - Setup PHP runtime
        // - Initialize server files
    }
}
