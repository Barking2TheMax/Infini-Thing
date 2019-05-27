package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.Buckets.InfiniBucket;
import me.tehbosscat.infinithing.Items.Buckets.InfiniLava;
import me.tehbosscat.infinithing.Items.Buckets.InfiniWater;
import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
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
        player = event.getPlayer();
        playerItem = player.getItemInHand();

        if(playerItem.equals(f.CreateItem("water"))){
            UseInfiniBucket(player, event, InfiniWater.GetInstance());

        }else if(playerItem.equals( f.CreateItem("lava"))){
            UseInfiniBucket(player, event, InfiniLava.GetInstance());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        player = event.getPlayer();
        playerItem = event.getItem();

        if (event.getItem().equals(f.CreateItem("empty"))) {
            if (player.hasPermission("infini.bucket.spawn")) {
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
                FillInfiniBucket(player, event, f.CreateItem("lava"));

            }else if(event.getItemStack().getType().equals(Material.WATER_BUCKET)){
                FillInfiniBucket(player, event, f.CreateItem("water"));
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


    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, InfiniItem type){
        double cost = type.GetPrice();

        if(player.hasPermission("infini.bucket.use")){
            if (Main.economy.has(player, cost)){
                event.setItemStack(f.CreateItem(type));
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
