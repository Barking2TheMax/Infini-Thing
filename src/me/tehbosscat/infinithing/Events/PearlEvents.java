package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Items.ThrownInfiniItem;
import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PearlEvents implements Listener {
    private static PearlEvents INSTANCE;
    private InfiniItemFactory f;

    private HashMap<UUID, Long> cooldown = Main.cooldown;
    private int COOLDOWN_TIME;

    private PearlEvents(FileConfiguration config){
        f = InfiniItemFactory.GetInstance();

        InfiniItem item = f.GetItem("pearl");

        try{
            COOLDOWN_TIME = config.getInt(item.GetConfigPath() + ".options.cooldown.time");
        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + item.GetConfigPath()+ ".options.cooldown.time in config.yml is wrong type, try int.");
            COOLDOWN_TIME = 30;
        }
    }

    public static PearlEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new PearlEvents(Main.config);
        }

        return INSTANCE;
    }

    public void SetCooldown(int cooldownTime){
        COOLDOWN_TIME = cooldownTime;
    }

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
                            long secondsLeft = COOLDOWN_TIME - ((System.currentTimeMillis() / 1000)  - (cooldown.get(player.getUniqueId()) / 1000));

                            if(secondsLeft < 1){
                                InfiniPearlLaunched(event, player, type);

                            }else{
                                Main.SendPlayerMessage(player,"The " + type.GetName() + " is cooling down. Cooldown: " + secondsLeft);
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

    private void InfiniPearlLaunched(ProjectileLaunchEvent event, Player player, ThrownInfiniItem type){
        double cost = type.GetPrice();

        if (Main.economy.has(player, cost)){
            Main.economy.withdrawPlayer(player, cost);
            Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + cost + ChatColor.WHITE + ".");
            cooldown.put(player.getUniqueId(), System.currentTimeMillis());

        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
            event.setCancelled(true);
        }
    }
}
