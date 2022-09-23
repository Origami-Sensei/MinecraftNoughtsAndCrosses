package me.origami_sensei.noughtsandcrosses.commands;

import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class nacHelp implements CommandExecutor {
    private final NoughtsAndCrosses plugin;

    public nacHelp(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    //simple command for getting help with the plugin:
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //reports the help message to the server
        plugin.getLogger().info("a player has asked for help using the plugin");
        if( sender instanceof Player p){
            //sends the help message to the player who asked
            p.sendMessage("-This is a plugin that allows you to play games of noughts and crosses against other players.");
            p.sendMessage("-To send a request type /nac <username>");
            p.sendMessage("-to accept a game request click the message or type /nacaccept <username>");
        }
        return true;
    }


}
