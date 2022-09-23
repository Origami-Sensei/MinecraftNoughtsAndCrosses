package me.origami_sensei.noughtsandcrosses.events;

import me.origami_sensei.noughtsandcrosses.Game;
import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//monitors for when a user clicks anything
public class ClickEvent implements Listener {


    private final NoughtsAndCrosses plugin;

    public ClickEvent(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {

        //pretty much we want the user to not be able to take stuff out of the inventory, but we want them to be able to use their own inventory
        //this code does that by setting setCancelled to true but only for our game inventory

        List<HumanEntity> viewers = e.getViewers();

        ArrayList<HumanEntity> players = new ArrayList<>(viewers);

        for (int i = 0; i < players.size(); i++) {

            Player p = (Player) players.get(i);
            String title =p.getName()+"'s turn";
            String WinnerTitle =p.getName()+" Wins!";
            //TODO: find non-depreciated way of getting title
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + WinnerTitle)){
                e.setCancelled(true);
            }

            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + title)) {

                Player player = (Player) e.getWhoClicked();
                if(player.getName().equals(p.getName())){


                    //this is to change the glass to a nautilus shell/crossbow
                    //get it NOUGHTilus shell CROSSbow
                    //clearly its peak humour

                    if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE) && e.getCurrentItem() !=null){
                        final @Nullable ItemStack @NotNull [] inv = Objects.requireNonNull(e.getClickedInventory()).getContents();

                        int noughts =0;
                        int crosses =0;
                        for (ItemStack item : inv) {
                            assert item != null;
                            if (item.getType().equals(Material.NAUTILUS_SHELL)) {
                                noughts += 1;
                            } else if ((item.getType().equals(Material.CROSSBOW))) {
                                crosses += 1;
                            }
                        }
                        if(noughts ==crosses) {
                            e.getCurrentItem().setType(Material.CROSSBOW);
                        }
                        else{
                            e.getCurrentItem().setType(Material.NAUTILUS_SHELL);
                        }
                        Game nextTurn =new Game(plugin);
                        Player nextMover = null;
                        if(i==1) {
                            nextMover = (Player) players.get(0);
                        } else if (i==0) {
                            nextMover = (Player) players.get(1);
                        }

                        assert nextMover != null;
                        nextTurn.gameInstance(inv, players,nextMover.getName());



                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
