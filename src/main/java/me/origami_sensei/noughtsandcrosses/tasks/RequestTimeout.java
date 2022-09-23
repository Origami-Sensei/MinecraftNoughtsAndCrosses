package me.origami_sensei.noughtsandcrosses.tasks;

import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

//runs the delete timeout command



public class RequestTimeout extends BukkitRunnable {
    private final String sender;
    private final String recipient;
    NoughtsAndCrosses plugin;

    public RequestTimeout(NoughtsAndCrosses plugin, String sender, String recipient) {
        this.plugin = plugin;
        this.sender = sender;
        this.recipient =recipient;
    }

    @Override
    public void run() {
        plugin.getLogger().info("running timeout");
        try {
            this.plugin.getDatabase().deleteGameRequest(sender,recipient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
