
package com.pocketmp.server.models;

public class Plugin {
    private String name;
    private String status;
    private long size;

    public Plugin(String name, String status, long size) {
        this.name = name;
        this.status = status;
        this.size = size;
    }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public long getSize() { return size; }
}
