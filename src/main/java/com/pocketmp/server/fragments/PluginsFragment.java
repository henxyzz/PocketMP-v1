
package com.pocketmp.server.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.pocketmp.server.R;

public class PluginsFragment extends Fragment {
    private RecyclerView pluginList;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plugins, container, false);
        
        pluginList = view.findViewById(R.id.plugin_list);
        // TODO: Setup plugin adapter and download functionality
        
        return view;
    }
}
