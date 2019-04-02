package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.InfiniBucket;
import me.tehbosscat.infinithing.Main;
import me.tehbosscat.infinithing.Menus.BucketMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.block.Action.*;

public class BucketEvents implements Listener {
    private Player player;
    private ItemStack playerItem;
    private ItemStack emptyBucket;
    private ItemStack waterBucket;
    private ItemStack lavaBucket;

    private void InstantiateVars(PlayerBucketEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();
        waterBucket = Main.water.CreateInstance();
        lavaBucket = Main.lava.CreateInstance();
    }

    private void InstantiateVars(PlayerInteractEvent event){
        player = event.getPlayer();
        playerItem = event.getItem();
        emptyBucket = Main.empty.CreateInstance();
        waterBucket = Main.water.CreateInstance();
        lavaBucket = Main.lava.CreateInstance();
    }

    private void InstantiateVars(PlayerDropItemEvent event){
        player = event.getPlayer();
        playerItem = event.getItemDrop().getItemStack();
        emptyBucket = Main.empty.CreateInstance();
        waterBucket = Main.water.CreateInstance();
        lavaBucket = Main.lava.CreateInstance();
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent (PlayerBucketEmptyEvent event){
        InstantiateVars(event);
        double cost;

        if(playerItem.equals(waterBucket)){
            try{
                cost = Main.config.getDouble("bucket.types.water.use-cost");
                UseInfiniBucket(player, event, waterBucket, cost);
            }catch (Exception e){
                Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "bucket.water.use-cost in config.yml is wrong type, try float.");
            }
        }else if(playerItem.equals(lavaBucket)){
            try{
                cost = Main.config.getInt("bucket.types.lava.use-cost");
                UseInfiniBucket(player, event, lavaBucket, cost);
            }catch (Exception e){
                Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "bucket.lava.use-cost in config.yml is wrong type, try float.");
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!event.getAction().equals(LEFT_CLICK_BLOCK)){
            InstantiateVars(event);

            if (event.getItem().equals(emptyBucket)) {
                if (player.hasPermission("infini.bucket.spawn")) {
                    new BucketMenu().newBucketMenu(player);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBucketFillEvent (PlayerBucketFillEvent event){
        InstantiateVars(event);

        if(playerItem.equals(emptyBucket)){
            if (event.getItemStack().equals(new ItemStack(Material.LAVA_BUCKET, 1))){
                FillInfiniBucket(player, event, lavaBucket);

            }else if(event.getItemStack().equals(new ItemStack(Material.WATER_BUCKET, 1))){
                FillInfiniBucket(player, event, waterBucket);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        InstantiateVars(event);

        if (playerItem.equals(waterBucket) || playerItem.equals(lavaBucket)) {
            event.getItemDrop().remove();
            new InfiniBucket().giveItems(event.getPlayer());
        }
    }

    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, ItemStack bucket, double cost){
        if(player.hasPermission("infini.bucket.use")){
            if (Main.economy.has(player, cost)){
                event.setItemStack(bucket);
                Main.SendPlayerMessage(player, ChatColor.GREEN + "$" + cost + ChatColor.WHITE + " has been charged to your account.");
                Main.economy.withdrawPlayer(player, cost);
            }else {
                Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
                event.setCancelled(true);
            }
        }else{
            Main.SendPlayerMessage(player, "You don't know how to use this item.");
            event.setCancelled(true);
        }
    }

    private void FillInfiniBucket(Player player, PlayerBucketFillEvent event, ItemStack bucket){
        if (player.hasPermission("infini.bucket.fill")){
            if(!player.getInventory().contains(bucket)){
                event.setItemStack(bucket);
                Main.SendPlayerMessage(player, "You created a " + bucket.getItemMeta().getDisplayName() + ".");
            }else{
                Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + bucket.getItemMeta().getDisplayName() + ".");
                event.setCancelled(true);
            }
        }else{
            Main.SendPlayerMessage(player, "You don't know how to create an infinite source contained within a bucket.");
            event.setCancelled(true);
        }
    }
}
