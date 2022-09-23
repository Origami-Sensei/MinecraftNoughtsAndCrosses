package me.origami_sensei.noughtsandcrosses.commands;


import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import me.origami_sensei.noughtsandcrosses.tasks.RequestTimeout;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class nacRequest implements CommandExecutor {

    private final NoughtsAndCrosses plugin;

    public nacRequest(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    // /nac <PlayerName>
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if(sender instanceof Player p){
        if(args.length ==0){
            p.sendMessage("You didn't specify a player to play against");
        }
        else{
            String playerName =args[0];
            Player target = Bukkit.getServer().getPlayer(playerName);

            if(target ==null){
                p.sendMessage("The player you wanted to play against isn't online");
            }
            else{
                try {
                    this.plugin.getDatabase().createGameRequest(p.getName(),target.getName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                BukkitTask setTimeout =new RequestTimeout(plugin,p.getName(),target.getName()).runTaskLater(plugin,6000L);



                p.sendMessage("You sent a request to "+target.getName());
                target.sendMessage(p.getName()+" wants to play noughts and crosses against you");

                TextComponent message =new TextComponent("Click here to accept!");

                message.setColor(ChatColor.GREEN);
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("Click here to accept the game request")));
                String instruction ="/nacaccept "+p.getDisplayName();
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,instruction));
                target.spigot().sendMessage(message);
            }
        }
    }


    return true;
    }
}
