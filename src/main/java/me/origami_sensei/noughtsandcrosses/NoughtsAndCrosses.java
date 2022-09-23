package me.origami_sensei.noughtsandcrosses;

import me.origami_sensei.noughtsandcrosses.commands.nacAccept;
import me.origami_sensei.noughtsandcrosses.commands.nacHelp;
import me.origami_sensei.noughtsandcrosses.commands.nacRequest;
import me.origami_sensei.noughtsandcrosses.db.Database;
import me.origami_sensei.noughtsandcrosses.events.ClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;


public final class NoughtsAndCrosses extends JavaPlugin {
    private Database database;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Noughts and Crosses has started");

        try {
            this.database =new Database(this);
            this.database.InitialiseDatabase();
        } catch (SQLException e) {
            getLogger().info("Unable to connect to Noughts and Crosses database and create table(s)");
            throw new RuntimeException(e);
        }


        //adds the commands and events to the plugin( IDK. It needs these for the stuff to work)
        //game request:
        Objects.requireNonNull(getCommand("nac")).setExecutor(new nacRequest(this));
        //game accept:
        Objects.requireNonNull(getCommand("nacaccept")).setExecutor(new nacAccept(this));
        //game help:
        Objects.requireNonNull(getCommand("nachelp")).setExecutor(new nacHelp(this));

        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);

    }
    //does db stuff (go look at the database class to see more)
    public Database getDatabase() {
        return database;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Noughts and Crosses has stopped");
    }
}
