package me.tehbosscat.infinithing.Menus;

import me.tehbosscat.infinithing.Items.Buckets.InfiniBucket;
import me.tehbosscat.infinithing.Items.Buckets.InfiniLava;
import me.tehbosscat.infinithing.Items.Buckets.InfiniWater;
import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BucketMenu implements Listener {
    private final static String NAME = "Select an " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket";
    private final static int inventSize = 9;
    private final static String BUTTON_LORE = "Click to select this bucket type.";

    private ItemStack waterBucket;
    private ItemStack lavaBucket;

    public void newBucketMenu(Player player){
        Inventory menu = Main.server.createInventory(null, inventSize, NAME);

        ItemStack empty = AddMeta(new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 15));
        ItemStack lavaButton = InfiniBucket.AddMeta(new ItemStack(Material.LAVA_BUCKET, 1), InfiniLava.NAME, InfiniLava.getLoreString(), BUTTON_LORE);
        ItemStack waterButton = InfiniBucket.AddMeta(new ItemStack(Material.WATER_BUCKET, 1), InfiniWater.NAME, InfiniWater.getLoreString(), BUTTON_LORE);

        for (int i = 0; i < inventSize; i++) {
            switch (i) {
                case 3:
                    menu.setItem(i, lavaButton);
                    break;
                case 5:
                    menu.setItem(i, waterButton);
                    break;
                default:
                    menu.setItem(i, empty);
                    break;
            }
        }

        player.openInventory(menu);
    }

    private static ItemStack AddMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();

        if(inventory == null) return;
        if(!inventory.getName().equals(NAME)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item == null || !item.hasItemMeta()) return;

        ItemStack emptyBucket = Main.empty.CreateInstance();
        lavaBucket = Main.lava.CreateInstance();
        waterBucket = Main.water.CreateInstance();

        if(item.getItemMeta().getDisplayName().equals(InfiniLava.NAME)){
            if(!player.getInventory().contains(lavaBucket)){
                ReplaceBucket(player, emptyBucket, lavaBucket);
            }
            else{
                Main.SendPlayerMessage(player, ChatColor.RED + "A strange force prevents your from creating another" + ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Lava" + ChatColor.RED + " so close to the one in your inventory.");
            }
        }else if (item.getItemMeta().getDisplayName().equals(InfiniWater.NAME)){
            if(!player.getInventory().contains(waterBucket)) {
                ReplaceBucket(player, emptyBucket, waterBucket);
            }
            else {
                Main.SendPlayerMessage(player, ChatColor.RED +"A strange force prevents your from creating another" + ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Water" + ChatColor.RED + " so close to the one in your inventory.");
            }
        }
        player.closeInventory();
    }

    public static void ReplaceBucket(Player player, ItemStack oldBucket, ItemStack newBucket){
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getContents()[i].equals(oldBucket)){
                inventory.setItem(i , newBucket);
                break;
            }
        }
    }
}
