package me.tehbosscat.infinithing.Events;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Items.Other.InfiniPearl;
import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;
import java.util.UUID;

public class PearlEvents implements Listener {
    private static PearlEvents INSTANCE;
    private InfiniItemFactory f;

    private HashMap<UUID, Long> cooldown = Main.cooldown;
    private static int cooldownTime;

    private PearlEvents(){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();
    }


    public static PearlEvents GetInstance() {
        if(INSTANCE == null){
            INSTANCE = new PearlEvents();
        }

        return INSTANCE;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        Projectile entity = event.getEntity();
        InfiniItem infPearl = InfiniPearl.GetInstance();

        if(entity.getShooter() instanceof Player){
            Player player = (Player) entity.getShooter();
            if(player.getItemInHand().getItemMeta().getDisplayName().equals(infPearl.GetName()) && entity.getName().equals("entity.ThrownEnderpearl.name")){
                if(player.hasPermission("infini.pearl.use")){
                    if(Main.config.getBoolean("pearl.cooldown.cooldown-enabled")){
                        if(!cooldown.containsKey(player.getUniqueId())){
                            InfiniPearlLaunched(event, player);

                        }else{
                            long secondsLeft = (cooldown.get(player.getUniqueId()) / 1000) + cooldownTime - (System.currentTimeMillis() / 1000);
                            if(secondsLeft < 1){
                                InfiniPearlLaunched(event, player);

                            }else{
                                Main.SendPlayerMessage(player,"The "+ ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Pearl is cooling down. Cooldown: " + secondsLeft);
                                event.setCancelled(true);
                            }
                        }

                    }else{
                        InfiniPearlLaunched(event, player);
                    }

                }else{
                    Main.SendPlayerMessage(player, "You don't know how to use this item.");
                    event.setCancelled(true);
                }

                player.getInventory().addItem(f.CreateItem("pearl"));
            }else{
                Main.SendPlayerMessage(player,"Test");
            }
        }
    }

    private void InfiniPearlLaunched(ProjectileLaunchEvent event, Player player){
        double cost = InfiniPearl.GetInstance().GetPrice();

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
