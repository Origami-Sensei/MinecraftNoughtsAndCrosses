package me.origami_sensei.noughtsandcrosses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private final NoughtsAndCrosses plugin;

    public Game(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    //makes a blank game (no moves played)
    public void blankGame(Player player, Player target){

        //gets random player to start game to be fair (since noughts and crosses is solved losing is impossible while playing perfectly
        Random rand = new Random();
        int starter =rand.nextInt(2);

        //generates the title based on what random number is chosen
        String title;
        if(starter==0){
            title =player.getName()+"'s turn";
        }
        else{
            title =target.getName()+"'s turn";

        }
        //creates the inventory with
        Inventory gui = Bukkit.createInventory(player, InventoryType.DROPPER, ChatColor.AQUA+title);
        ItemStack one   =new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

        //TODO: do this a better way
        ItemStack[] gameSquares ={one,one,one,one,one,one,one,one,one};
        gui.setContents(gameSquares);
        player.openInventory(gui);
        target.openInventory(gui);
    }
    public Boolean checkRow(ItemStack[] row){
        ItemStack item = row[1];
        return row[0].equals(row[1]) && row[1].equals(row[2]) && !item.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
    }
    public int[] checkForWin(ItemStack[] board){
        ItemStack[] row;
        Boolean  win;
        for(int i=0;i<3;i++){




            row = new ItemStack[]{board[i], board[i+3],board[i+6]};
            win = this.checkRow(row);

            if(win){
                return new int[]{i, i + 3, i + 6};
            }


            row = new ItemStack[]{board[3*i], board[3*i+1],board[3*i+2]};
            win = this.checkRow(row);

            if(win){
                return new int[]{3*i, 3*i+1, 3*i+2};
            }

        }
        row = new ItemStack[]{board[0], board[4],board[8]};
        win = this.checkRow(row);

        if(win){
            return new int[]{0,4,8};
        }


        row = new ItemStack[]{board[2], board[4],board[6]};
        win = this.checkRow(row);

        if(win){
            return new int[]{2,4,6};
        }



        return new int[] {};
    }
    public void gameInstance(ItemStack[] board, ArrayList<HumanEntity> players, String moveMaker){

        Player player1 = (Player) players.get(0);
        Player player2 = (Player) players.get(1);
        String title =moveMaker+"'s turn";
        Inventory gui = Bukkit.createInventory(player1, InventoryType.DROPPER, ChatColor.AQUA+title);
        gui.setContents(board);
        int[] potentialWin = this.checkForWin(board);

        if(potentialWin.length>0){

            String Winner ="";
            if(player1.getName().equals(moveMaker)){
                Winner =player2.getName();
            }
            else if(player2.getName().equals(moveMaker)){
                Winner =player1.getName();

            }
            String WinnerTitle =Winner+" Wins!";


            for(int i=0;i<3;i++){
                board[potentialWin[i]].addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS,1);
                ItemMeta im = board[potentialWin[i]].getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                lore.add(WinnerTitle);
                im.setLore(lore);
                board[potentialWin[i]].setItemMeta(im);

            }

            plugin.getLogger().info(WinnerTitle);
            @NotNull Inventory WinnerGui = Bukkit.createInventory(player1, InventoryType.DROPPER, ChatColor.AQUA + WinnerTitle);
            WinnerGui.setContents(board);
            player1.openInventory(WinnerGui);
            player2.openInventory(WinnerGui);

        }
        else{
            player1.openInventory(gui);
            player2.openInventory(gui);

        }


    }
}
