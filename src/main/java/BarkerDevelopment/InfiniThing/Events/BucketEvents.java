package BarkerDevelopment.InfiniThing.Events;

import BarkerDevelopment.InfiniThing.Main;
import BarkerDevelopment.InfiniThing.Items.InfiniItemType;
import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.InfiniThing.Menus.BucketMenuBehaviours.*;
import BarkerDevelopment.MinecraftMenus.*;
import BarkerDevelopment.MinecraftMenus.Behaviours.CloseButtonBehaviour;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


/**
 * Handles all events to do with Infini-Buckets.
 */
public class BucketEvents implements Listener {
    private static BucketEvents INSTANCE;
    private InfiniItemFactory f;
    private Menu menu;

    private Player player;
    private ItemStack playerItem;


    // Instantiation.
    /**
     * Constructor implementing Singleton design pattern.
     */
    private BucketEvents(){
        f = InfiniItemFactory.GetInstance();
        menu = InitialiseBucketMenu();
    }

    /**
     * BucketEvents implements the Singleton design pattern.
     *
     * @return The Singleton instance of BucketEvents.
     */
    public static BucketEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new BucketEvents();
        }

        return INSTANCE;
    }

    /**
     * Generates the Infini-Bucket menu.
     *
     * @return An Infini-Bucket menu object.
     */
    private Menu InitialiseBucketMenu(){
        Menu menu = new Menu.Builder(Main.plugin)
                .Title("Select an Infini-Bucket")
                .Rows(4)
                .build();

        AddBucketMenuButton(menu, f.GetItem("lava"), new LavaButtonBehaviour(), 12);
        AddBucketMenuButton(menu, f.GetItem("water"), new WaterButtonBehaviour(), 14);

        MenuItem exitButton = new MenuItem.Builder(Material.STAINED_GLASS_PANE)
                .Text("Close Menu")
                .MaterialByte(14)
                .Action(new CloseButtonBehaviour())
                .build();
        menu.SetMenuItem(31, exitButton);

        return menu;
    }


    // Events.
    /**
     * This function only activates if the player LEFT clicks and is holding an empty Infini-Bucket; if they are, open
     * the Infini-Bucket Menu.
     *
     * @param event Called when a player interacts with the world.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        player = event.getPlayer();
        playerItem = event.getItem();

        if(playerItem  == null) return;
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
            InfiniItemType type = f.GetItem("empty");
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

    /**
     *  This function only activates if the player is consuming an Infini-Milk.
     *
     * @param event Called when a player consumes a food item or potion.
     */
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event){
        player = event.getPlayer();
        playerItem = player.getItemInHand();

        if(playerItem.getType().equals(Material.MILK_BUCKET)){
            if(playerItem.equals(f.CreateItem("milk"))){
                DrinkInfiniMilk(player, event);
            }
        }
    }

    /**
     * This function only activates if the player is holding either an Infini-Lava or an Infini-Water.
     *
     * @param event Called when a player empties a bucket.
     */
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

    /**
     * This function only activates if the player is holding an Empty Infini-Bucket.
     *
     * @param event Called when a player fills a bucket.
     */
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

    /**
     * This function only activates if the player drops an Infini-Bucket of Lava or Water.
     *
     * @param event Called when a player drops an item.
     */
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


    // Methods.
    /**
     * Adds a MenuItem button to the menu.
     *
     * @param m Menu object to which the MenuItem is to be added.
     * @param item The MenuItem to add.
     * @param behaviour The OnClick behaviour of the MenuItem. NOTE: If nothing is to happen when clicked, use the
     *                  NullClick behaviour.
     * @param index The index of the button in the Menu object.
     */
    private void AddBucketMenuButton(Menu m, InfiniItemType item, I_OnClickBehaviour behaviour, int index){
        MenuItem button = new MenuItem.Builder(item.GetMaterial())
                .Text("Select " + item.GetName())
                .SubText(item.GetLoreString())
                .Action(behaviour)
                .build();

        double spawnCost = item.GetSpawnPrice();
        if (spawnCost > 0){
            button.AddSubText("Costs " + ChatColor.GREEN + "$" + item.GetSpawnPrice() + ChatColor.DARK_PURPLE +
                    ChatColor.ITALIC + " to spawn.");
        }

        m.SetMenuItem(index, button);
    }

    /**
     * A player tries to drink an Infini-Milk. Only allows them to do so if:
     *      - Infini-Milk is enabled in the config.
     *      - They have permission to use an Infini_milk.
     *      - They have the money to pay for the use.
     *
     * @param player Player triggering the event.
     * @param event PlayerItemConsumeEvent event.
     */
    private void DrinkInfiniMilk(Player player, PlayerItemConsumeEvent event){
        InfiniItemType milk =  f.GetItem("milk");
        if(milk.IsEnabled()){
            double useCost = milk.GetPrice();

            if(player.hasPermission(milk.GetPermissionPath() + ".use")){
                if (Main.ChargePlayer(player, useCost)) {
                    for (PotionEffect effect: player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                }

            }else{
                Main.SendPlayerMessage(player, "You don't know how to use this item.");
            }

        }else{
            Main.SendPlayerMessage(player, milk.GetName() + " has been disabled in the config.");
        }

        event.setCancelled(true);
    }

    /**
     * A player tries to empty an Infini-Bucket. Only allows them to do so if:
     *      - Infini-Lava and/or Infini-Water is enabled in the config.
     *      - They have permission to use an Infini-Bucket.
     *      - They have the money to pay for the use.
     *
     * @param player Player triggering the event.
     * @param event PlayerItemConsumeEvent event.
     * @param type The type of InfiniItemType being used.
     */
    private void UseInfiniBucket(Player player, PlayerBucketEmptyEvent event, InfiniItemType type){
        if(type.IsEnabled()){
            double useCost = type.GetPrice();

            if(player.hasPermission(type.GetPermissionPath() + ".use")){
                if (Main.ChargePlayer(player, useCost)) {
                    event.setItemStack(f.CreateItem(type));
                    return;
                }

            }else{
                Main.SendPlayerMessage(player, "You don't know how to use this item.");
            }

        }else{
            Main.SendPlayerMessage(player, type.GetName() + " has been disabled in the config.");
        }

        event.setCancelled(true);
    }

    /**
     * A player tries to fill an Infini-Bucket. Only allows them to do so if:
     *      - Infini-Lava and/or Infini-Water is enabled in the config.
     *      - They have permission to fill an Infini-Bucket.
     *      - They have the money to pay for the creation.
     *
     * @param player Player triggering the event.
     * @param event PlayerItemConsumeEvent event.
     * @param type The type of InfiniItemType being used.
     */
    private void FillInfiniBucket(Player player, PlayerBucketFillEvent event, InfiniItemType type){
        if(type.IsEnabled()){
            double spawnCost = type.GetSpawnPrice();
            if (player.hasPermission(type.GetPermissionPath() + ".fill")) {
                if (Main.ChargePlayer(player, spawnCost)) {
                    Main.SendPlayerMessage(player, "You created a " + type.GetName() + ".");
                    event.setItemStack(f.CreateItem(type));
                    return;
                }

            } else {
                Main.SendPlayerMessage(player, "You don't know how to create an infinite source contained " +
                        "within a bucket.");
            }

        }else{
            Main.SendPlayerMessage(player, type.GetName() + " has been disabled in the config.");
        }

        event.setCancelled(true);
    }
}
