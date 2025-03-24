
package com.pocketmp.server.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pocketmp.server.R;
import com.pocketmp.server.adapters.PluginAdapter;
import com.pocketmp.server.models.Plugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginsFragment extends Fragment {
    private RecyclerView pluginList;
    private PluginAdapter adapter;
    private Button addPlugin;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plugins, container, false);
        
        pluginList = view.findViewById(R.id.plugin_list);
        addPlugin = view.findViewById(R.id.add_plugin);
        
        setupPluginList();
        setupListeners();
        
        return view;
    }
    
    private void setupPluginList() {
        adapter = new PluginAdapter(loadInstalledPlugins());
        pluginList.setLayoutManager(new LinearLayoutManager(getContext()));
        pluginList.setAdapter(adapter);
    }
    
    private void setupListeners() {
        addPlugin.setOnClickListener(v -> showPluginDownloadDialog());
    }
    
    private List<Plugin> loadInstalledPlugins() {
        List<Plugin> plugins = new ArrayList<>();
        File pluginDir = new File(getContext().getFilesDir(), "server/plugins");
        
        if (pluginDir.exists()) {
            for (File file : pluginDir.listFiles()) {
                if (file.getName().endsWith(".phar")) {
                    plugins.add(new Plugin(
                        file.getName().replace(".phar", ""),
                        "Terinstal",
                        file.length()
                    ));
                }
            }
        }
        
        return plugins;
    }
    
    private void showPluginDownloadDialog() {
        PluginDownloadDialog dialog = new PluginDownloadDialog();
        dialog.show(getChildFragmentManager(), "download_plugin");
    }
}
