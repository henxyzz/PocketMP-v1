
package com.pocketmp.server.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.EditText;
import android.widget.Button;
import com.pocketmp.server.R;

public class SetupFragment extends Fragment {
    private EditText serverNameEdit;
    private EditText serverPortEdit;
    private EditText maxPlayersEdit;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        
        serverNameEdit = view.findViewById(R.id.server_name_edit);
        serverPortEdit = view.findViewById(R.id.server_port_edit);
        maxPlayersEdit = view.findViewById(R.id.max_players_edit);
        saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> saveServerProperties());
        
        return view;
    }

    private void saveServerProperties() {
        // TODO: Save to server.properties file
    }
}
