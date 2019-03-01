package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class BucketEvents implements Listener {
    private Player player;
    private ItemStack playerBucket;
    private ItemStack waterBucket;
    private ItemStack lavaBucket;

    private void InstantiateVars(PlayerBucketEvent event){
        player = event.getPlayer();
        playerBucket = player.getItemInHand();
        waterBucket = Main.water.CreateInstance();
        lavaBucket = Main.lava.CreateInstance();
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent (PlayerBucketEmptyEvent event){
        InstantiateVars(event);
        double cost;

        if(playerBucket.isSimilar(waterBucket)){
            try{
                cost = Main.config.getDouble("bucket.types.water.use-cost");
                UseInfiniBucket(player, event, waterBucket, cost);
            }catch (Exception e){
                Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "bucket.water.use-cost in config.yml is wrong type, try float.");
            }
        }else if(playerBucket.isSimilar(lavaBucket)){
            try{
                cost = Main.config.getInt("bucket.types.lava.use-cost");
                UseInfiniBucket(player, event, lavaBucket, cost);
            }catch (Exception e){
                Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "bucket.lava.use-cost in config.yml is wrong type, try float.");
            }
        }
    }

    // TODO Remove this event and add GUI.
    @EventHandler
    public void onPlayerBucketFillEvent (PlayerBucketFillEvent event){
        InstantiateVars(event);

        if(playerBucket.isSimilar(Main.empty.CreateInstance())) {
            if (event.getItemStack().equals(new ItemStack(Material.LAVA_BUCKET, 1))){
                FillInfiniBucket(player, event, lavaBucket);

            }else if(event.getItemStack().isSimilar(new ItemStack(Material.WATER_BUCKET, 1))){
                FillInfiniBucket(player, event, waterBucket);
            }
        }
    }

    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, ItemStack bucket, double cost){
        if (Main.economy.has(player, cost)){
            event.setItemStack(bucket);
            Main.SendPlayerMessage(player, ChatColor.GREEN + "$" + cost + ChatColor.WHITE + " has been charged to your account.");
            Main.economy.withdrawPlayer(player, cost);
        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
            event.setCancelled(true);
        }
    }

    private void FillInfiniBucket(Player player, PlayerBucketFillEvent event, ItemStack bucket){
        if(!player.getInventory().contains(bucket)){
            event.setItemStack(bucket);
            Main.SendPlayerMessage(player, "You created a " + bucket.getItemMeta().getDisplayName() + ".");
        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + bucket.getItemMeta().getDisplayName() + ".");
            event.setCancelled(true);
        }
    }
}
