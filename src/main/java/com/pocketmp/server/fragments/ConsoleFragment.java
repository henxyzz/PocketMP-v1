
package com.pocketmp.server.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import com.pocketmp.server.R;

public class ConsoleFragment extends Fragment {
    private TextView consoleOutput;
    private EditText commandInput;
    private Button sendCommand;
    private Button startServer;
    private Button stopServer;
    private TextView serverStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_console, container, false);
        
        consoleOutput = view.findViewById(R.id.console_output);
        commandInput = view.findViewById(R.id.command_input);
        sendCommand = view.findViewById(R.id.send_command);
        startServer = view.findViewById(R.id.start_server);
        stopServer = view.findViewById(R.id.stop_server);
        serverStatus = view.findViewById(R.id.server_status);

        setupListeners();
        
        return view;
    }

    private void setupListeners() {
        sendCommand.setOnClickListener(v -> sendCommandToServer());
        startServer.setOnClickListener(v -> startMinecraftServer());
        stopServer.setOnClickListener(v -> stopMinecraftServer());
    }

    private void sendCommandToServer() {
        String command = commandInput.getText().toString();
        Intent intent = new Intent(getContext(), ServerService.class);
        intent.setAction("SEND_COMMAND");
        intent.putExtra("command", command);
        getContext().startService(intent);
        commandInput.setText("");
    }

    private void startMinecraftServer() {
        Intent intent = new Intent(getContext(), ServerService.class);
        intent.setAction("START_SERVER");
        getContext().startService(intent);
        serverStatus.setText("Status: Memulai server...");
        startServer.setEnabled(false);
        stopServer.setEnabled(true);
    }

    private void stopMinecraftServer() {
        Intent intent = new Intent(getContext(), ServerService.class);
        intent.setAction("STOP_SERVER");
        getContext().startService(intent);
        serverStatus.setText("Status: Menghentikan server...");
        startServer.setEnabled(true);
        stopServer.setEnabled(false);
    }

    private void updateConsole(String text) {
        consoleOutput.append(text + "\n");
    }
}
