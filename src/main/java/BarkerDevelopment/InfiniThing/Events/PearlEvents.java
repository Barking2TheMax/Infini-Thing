package BarkerDevelopment.InfiniThing.Events;

import BarkerDevelopment.InfiniThing.Items.InfiniItemType;
import BarkerDevelopment.InfiniThing.Main;
import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.InfiniThing.Items.ThrownInfiniItem;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;


/**
 * Handles all events to do with Infini-Buckets.
 */
public class PearlEvents implements Listener {
    private static PearlEvents INSTANCE;
    private InfiniItemFactory f;

    private HashMap<UUID, Long> cooldown;
    private int COOLDOWN_TIME;


    // Instantiation.
    /**
     * Constructor implementing Singleton design pattern.
     */
    private PearlEvents(FileConfiguration config){
        f = InfiniItemFactory.GetInstance();
        InfiniItemType item = f.GetItem("pearl");
        cooldown = new HashMap<>();

        UpdateCooldown();
    }

    /**
     * PearlEvents implements the Singleton design pattern.
     *
     * @return The Singleton instance of PearlEvents.
     */
    public static PearlEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new PearlEvents(Main.config);
        }

        return INSTANCE;
    }


    // Getters and Setters.
    /**
     * Gets the cooldown value.
     *
     * @param cooldownTime The cooldown value.
     */
    public void SetCooldown(int cooldownTime){
        COOLDOWN_TIME = cooldownTime;
    }

    /**
     * Updates the cooldown value from the config.
     */
    public void UpdateCooldown(){
        InfiniItemType item = f.GetItem("pearl");

        try{
            COOLDOWN_TIME = Main.config.getInt(item.GetConfigPath() + ".options.cooldown.time");
        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + item.GetConfigPath() +
                    ".options.cooldown.time in config.yml is wrong type, try int.");
            COOLDOWN_TIME = 30;
        }
    }


    // Events.
    /**
     * This function only activates if the player throwing an Infini-Pearl.
     *
     * @param event Called when a player throws an entity.
     */
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        Projectile entity = event.getEntity();
        ThrownInfiniItem type = (ThrownInfiniItem) f.GetItem("pearl");
        ItemStack item = f.CreateItem(type);

        if(entity.getShooter() instanceof Player){
            Player player = (Player) entity.getShooter();

            if(player.getItemInHand().isSimilar(item) && entity.getType().equals(type.GetEntityType())){
                if(player.hasPermission(type.GetPermissionPath() + ".use")){
                    if(Main.config.getBoolean(type.GetConfigPath() + ".options.cooldown.cooldown-enabled")){
                        if(!cooldown.containsKey(player.getUniqueId())){
                            InfiniPearlLaunched(event, player, type);

                        }else{
                            long secondsLeft = COOLDOWN_TIME - ((System.currentTimeMillis() / 1000)  -
                                    (cooldown.get(player.getUniqueId()) / 1000));

                            if(secondsLeft < 1){
                                InfiniPearlLaunched(event, player, type);

                            }else{
                                Main.SendPlayerMessage(player,"The " + type.GetName() + " is cooling down. " +
                                        "Cooldown: " + secondsLeft);
                                event.setCancelled(true);
                            }
                        }

                    }else{
                        InfiniPearlLaunched(event, player, type);
                    }

                }else{
                    Main.SendPlayerMessage(player, "You don't know how to use this item.");
                    event.setCancelled(true);
                }

                player.getInventory().addItem(item);
            }
        }
    }


    // Methods.
    /**
     * A player tries to launch an Infini-Pearl. Only allows them to do so if:
     *      - Infini-Pearl is enabled in the config.
     *      - They have permission to use an Infini-Pearl.
     *      - They have the money to pay for the use.
     *
     * @param event ProjectileLaunchEvent event.
     * @param player Player triggering the event.
     * @param type The type of ThrownInfiniItem being used.
     */
    private void InfiniPearlLaunched(ProjectileLaunchEvent event, Player player, ThrownInfiniItem type){
        if (type.IsEnabled()){
            double cost = type.GetPrice();

            if (Main.ChargePlayer(player, cost)){
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                return;
            }

        }else{
            Main.SendPlayerMessage(player, type.GetName() + " has been disabled in the config.");
        }

        event.setCancelled(true);
    }
}
