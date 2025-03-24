
package com.pocketmp.server.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.pocketmp.server.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PluginDownloadDialog extends DialogFragment {
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_plugin_download, null);
        
        EditText urlInput = view.findViewById(R.id.plugin_url);
        
        builder.setView(view)
               .setTitle("Unduh Plugin")
               .setPositiveButton("Unduh", (dialog, id) -> {
                   String url = urlInput.getText().toString();
                   if (!url.isEmpty()) {
                       downloadPlugin(url);
                   }
               })
               .setNegativeButton("Batal", (dialog, id) -> dialog.cancel());
        
        return builder.create();
    }
    
    private void downloadPlugin(String urlStr) {
        new Thread(() -> {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                
                String fileName = urlStr.substring(urlStr.lastIndexOf('/') + 1);
                File pluginDir = new File(requireContext().getFilesDir(), "server/plugins");
                if (!pluginDir.exists()) {
                    pluginDir.mkdirs();
                }
                
                File outputFile = new File(pluginDir, fileName);
                
                try (InputStream in = conn.getInputStream();
                     FileOutputStream out = new FileOutputStream(outputFile)) {
                    
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
