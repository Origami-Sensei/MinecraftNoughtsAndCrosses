package me.origami_sensei.noughtsandcrosses.commands;

import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import me.origami_sensei.noughtsandcrosses.Game;
import me.origami_sensei.noughtsandcrosses.models.GameRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class nacAccept implements CommandExecutor {
    private final NoughtsAndCrosses plugin;

    public nacAccept(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        //makes sure a player sent the message then takes an instance of the player
        if( sender instanceof Player p){

            plugin.getLogger().info("A game has been accepted");

            //gets the name of the player the request is for
            String playerName =args[0];

            //checks if they are online (I guess you could make it send it to offline players but IDK why)
            Player target = Bukkit.getServer().getPlayer(playerName);

            GameRequest game;

            try {

                plugin.getLogger().info("Game request validated");
                //searches for the game in the db
                assert target != null;

                game =  this.plugin.getDatabase().findGameRequest(target.getName(),p.getName());

            } catch (SQLException e) {

                plugin.getLogger().info("Unable to validate game request");
                throw new RuntimeException(e);
            }

            if(game != null){
                plugin.getLogger().info("Game Accept validated");
                try {
                    //deletes the game request since it has been accepted
                    this.plugin.getDatabase().deleteGameRequest(target.getName(),p.getName());
                } catch (SQLException e) {

                   plugin.getLogger().info("Unable to delete game request upon acceptance");
                    throw new RuntimeException(e);
                }

                //start game here
                Player player = (Player) sender;
                Game Game =new Game(plugin);
                Game.blankGame(player,target);



            }
            else{
                p.sendMessage("The game request you are trying to accept has either timed out or didn't exist");
                plugin.getLogger().info("Game Accept rejected");
            }

        }

        return false;
    }
}
