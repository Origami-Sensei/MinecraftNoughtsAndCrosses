package me.origami_sensei.noughtsandcrosses.db;


import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;

public class CredentialGetter {
    private final NoughtsAndCrosses plugin;

    public CredentialGetter(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    public Object getCredentials(){
        plugin.saveDefaultConfig();
        String url = plugin.getConfig().getString("url");
        String username = plugin.getConfig().getString("username");
        String password = plugin.getConfig().getString("password");

        return new String[]{url, username, password};
    }
}