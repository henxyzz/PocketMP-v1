
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
        holder.nameText.setText(plugin.getName());
        holder.statusText.setText(plugin.getStatus());
        holder.sizeText.setText(formatSize(plugin.getSize()));
    }
    
    @Override
    public int getItemCount() {
        return plugins.size();
    }
    
    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, statusText, sizeText;
        
        ViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.plugin_name);
            statusText = view.findViewById(R.id.plugin_status);
            sizeText = view.findViewById(R.id.plugin_size);
        }
    }
}
