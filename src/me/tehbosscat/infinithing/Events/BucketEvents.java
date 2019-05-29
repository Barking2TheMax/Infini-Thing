package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;


public class BucketEvents implements Listener {
    private static BucketEvents INSTANCE;
    private InfiniItemFactory f;

    private Player player;
    private ItemStack playerItem;


    private BucketEvents(){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();
    }


    public static BucketEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new BucketEvents();
        }

        return INSTANCE;
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent (PlayerBucketEmptyEvent event){
        InfiniItem type;
        ItemStack item;
        ItemStack water = f.CreateItem("water");
        ItemStack lava = f.CreateItem("lava");

        player = event.getPlayer();
        playerItem = player.getItemInHand();

        if(playerItem.equals(water)){
            type = f.GetItem("water");
            item = water;

        }else if(playerItem.equals(lava)) {
            type = f.GetItem("lava");
            item = lava;

        } else{
            type = f.GetItem("milk");
            item = f.CreateItem("milk");
        }

        if(player.hasPermission(type.GetPermissionPath() + ".use")){
            UseInfiniBucket(player, event, item, type.GetPrice());

        }else{
            Main.SendPlayerMessage(player, "You don't know how to use this item.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        InfiniItem type = f.GetItem("empty");

        player = event.getPlayer();
        playerItem = event.getItem();


        if (event.getItem().equals(f.CreateItem(type))) {
            if (player.hasPermission(type.GetPermissionPath() + ".spawn")) {
                new BucketMenu().newBucketMenu(player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBucketFillEvent (PlayerBucketFillEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();

        if(playerItem.equals(f.CreateItem("empty"))){
            if (event.getItemStack().getType().equals(Material.LAVA_BUCKET)){
                FillInfiniBucket(player, event, f.GetItem("lava"));

            }else if(event.getItemStack().getType().equals(Material.WATER_BUCKET)){
                FillInfiniBucket(player, event, f.GetItem("water"));
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        player = event.getPlayer();
        playerItem = event.getItemDrop().getItemStack();

        ItemStack WATER = f.CreateItem("water");
        ItemStack LAVA = f.CreateItem("lava");

        if (playerItem.equals(WATER) || playerItem.equals(LAVA)) {
            event.getItemDrop().remove();
            player.getInventory().addItem(f.CreateItem("empty"));
        }
    }


    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, ItemStack type, double cost){
        if (Main.economy.has(player, cost)) {
            event.setItemStack(type);
            Main.SendPlayerMessage(player, ChatColor.GREEN + "$" + cost + ChatColor.WHITE + " has been charged to your account.");
            Main.economy.withdrawPlayer(player, cost);

        } else {
            Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient funds.");
            event.setCancelled(true);
        }
    }

    private void FillInfiniBucket(Player player, PlayerBucketFillEvent event, InfiniItem type){
        ItemStack item = f.CreateItem(type);

        if (player.hasPermission(type.GetPermissionPath() + ".fill")){
            if(!player.getInventory().contains(item)){
                event.setItemStack(item);
                Main.SendPlayerMessage(player, "You created a " + type.GetName() + ".");

            }else{
                Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + type.GetName() + ".");
                event.setCancelled(true);
            }

        }else{
            Main.SendPlayerMessage(player, "You don't know how to create an infinite source contained within a bucket.");
            event.setCancelled(true);
        }
    }
}
