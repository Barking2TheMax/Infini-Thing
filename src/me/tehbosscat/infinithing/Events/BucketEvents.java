package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Main;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import me.tehbosscat.infinithing.Menus.BaseMenu.Menu;
import me.tehbosscat.infinithing.Menus.BaseMenu.MenuItem;
import me.tehbosscat.infinithing.Menus.BucketMenuBehaviours.LavaButtonBehaviour;
import me.tehbosscat.infinithing.Menus.BucketMenuBehaviours.WaterButtonBehaviour;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;


public class BucketEvents implements Listener {
    private static BucketEvents INSTANCE;
    private InfiniItemFactory f;
    private Menu menu;

    private Player player;
    private ItemStack playerItem;


    private BucketEvents(){
        f = InfiniItemFactory.GetInstance();
        menu = InitialiseBucketMenu();
    }

    public static BucketEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new BucketEvents();
        }

        return INSTANCE;
    }

    private Menu InitialiseBucketMenu(){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();

        Menu menu = new Menu(
                "Select an " + f.GetItem("empty").GetName(),
                3
        );

        AddBucketMenuButton(menu, f.GetItem("lava"), new LavaButtonBehaviour(), 12);
        AddBucketMenuButton(menu, f.GetItem("water"), new WaterButtonBehaviour(), 14);

        return menu;
    }

    private void AddBucketMenuButton(Menu m, InfiniItem item, I_OnClickBehaviour behaviour, int index){
        MenuItem button = new MenuItem.Builder(item.GetMaterial())
                .Text("Select " + item.GetName())
                .SubText(item.GetLoreString())
                .Action(behaviour)
                .build();

        double spawnCost = item.GetSpawnPrice();

        if (spawnCost > 0){
            button.AddSubText("Costs " + ChatColor.GREEN + "$" + item.GetSpawnPrice() + ChatColor.DARK_PURPLE + ChatColor.ITALIC + " to spawn.");
        }

        m.UpdateMenuItem(index, button);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        player = event.getPlayer();
        playerItem = event.getItem();

        if(playerItem  == null) return;
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
            InfiniItem type = f.GetItem("empty");
            if (event.getItem().isSimilar(f.CreateItem(type))){
                if (player.hasPermission(type.GetPermissionPath() + ".spawn")) {
                    menu.Show(player);
                    event.setCancelled(true);

                }else{
                    Main.SendPlayerMessage(player, "You don't know how to use this item.");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();

        ItemStack milk = f.CreateItem("milk");

        if(playerItem.equals(milk)){
            DrinkInfiniMilk(player, event, f.GetItem("milk"));
        }
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent (PlayerBucketEmptyEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();
        ItemStack water = f.CreateItem("water");
        ItemStack lava = f.CreateItem("lava");

        if(playerItem.equals(water)){
            UseInfiniBucket(player, event, f.GetItem("water"));

        }else if(playerItem.equals(lava)) {
            UseInfiniBucket(player, event, f.GetItem("lava"));
        }
    }

    @EventHandler
    public void onPlayerBucketFillEvent (PlayerBucketFillEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();

        ItemStack eventItem = event.getItemStack();

        if(playerItem.isSimilar(f.CreateItem("empty"))){
            if (eventItem.getType().equals(Material.LAVA_BUCKET)){
                FillInfiniBucket(player, event, f.GetItem("lava"));

            }else if(eventItem.getType().equals(Material.WATER_BUCKET)){
                FillInfiniBucket(player, event, f.GetItem("water"));

            }else if(eventItem.getType().equals(Material.MILK_BUCKET)){
                FillInfiniBucket(player, event, f.GetItem("milk"));
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        player = event.getPlayer();
        playerItem = event.getItemDrop().getItemStack();

        ItemStack lava = f.CreateItem("lava");
        ItemStack milk = f.CreateItem("milk");
        ItemStack water = f.CreateItem("water");

        if (playerItem.equals(lava) || playerItem.equals(milk) || playerItem.equals(water)) {
            event.getItemDrop().remove();
            player.getInventory().addItem(f.CreateItem("empty"));
        }
    }

    private void DrinkInfiniMilk(Player player, PlayerItemConsumeEvent event, InfiniItem type){

    }

    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, InfiniItem type){
        double useCost = type.GetPrice();

        if(player.hasPermission(type.GetPermissionPath() + ".use")){
            if (Main.economy.has(player, useCost)) {
                event.setItemStack(f.CreateItem(type));
                Main.SendPlayerMessage(player, ChatColor.GREEN + "$" + useCost + ChatColor.WHITE + " has been charged to your account.");
                Main.economy.withdrawPlayer(player, useCost);

            } else {
                Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient funds.");
                event.setCancelled(true);
            }

        }else{
            Main.SendPlayerMessage(player, "You don't know how to use this item.");
            event.setCancelled(true);
        }
    }

    private void FillInfiniBucket(Player player, PlayerBucketFillEvent event, InfiniItem type){
        ItemStack item = f.CreateItem(type);

        if (player.hasPermission(type.GetPermissionPath() + ".fill")){
            player.getInventory().addItem(item);
            Main.SendPlayerMessage(player, "You created a " + type.GetName() + ".");

        }else{
            Main.SendPlayerMessage(player, "You don't know how to create an infinite source contained within a bucket.");
        }

        event.setCancelled(true);
    }
}
