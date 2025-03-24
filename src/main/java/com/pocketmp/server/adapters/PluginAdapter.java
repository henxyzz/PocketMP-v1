
package com.pocketmp.server.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.pocketmp.server.R;
import com.pocketmp.server.models.Plugin;
import java.util.List;

public class PluginAdapter extends RecyclerView.Adapter<PluginAdapter.ViewHolder> {
    private List<Plugin> plugins;

    public PluginAdapter(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_plugin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plugin plugin = plugins.get(position);
        holder.pluginName.setText(plugin.getName());
        holder.pluginStatus.setText(plugin.getStatus());
        holder.pluginSize.setText(String.format("%.2f MB", plugin.getSize() / 1024.0 / 1024.0));
    }

    @Override
    public int getItemCount() {
        return plugins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pluginName;
        public TextView pluginStatus;
        public TextView pluginSize;

        public ViewHolder(View view) {
            super(view);
            pluginName = view.findViewById(R.id.plugin_name);
            pluginStatus = view.findViewById(R.id.plugin_status);
            pluginSize = view.findViewById(R.id.plugin_size);
        }
    }
}
