package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Items.Buckets.InfiniBucket;
import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

public class Commands extends CommandExecute implements Listener, CommandExecutor {
    private static InfiniItemFactory f = InfiniItemFactory.GetInstance();

    public String baseCmd = "infini";
    public ArrayList<String> baseArgArray = new ArrayList<>(Arrays.asList("bucket", "pearl"));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase(baseCmd)){
                if(args.length > 0){
                    int index = baseArgArray.indexOf( args[0].toLowerCase() );

                    switch(index){
                        // Bucket
                        case 0:
                            if(player.hasPermission("infini.bucket.spawn")){
                                SpawnInfiniBucket(player);
                            }else{
                                Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an Infini-Bucket.");
                            }
                            return true;

                        // Pearl
                        case 1:
                            if(player.hasPermission("infini.pearl.spawn")){
                                if(!player.getInventory().contains(Main.pearl.CreateInstance())){
                                    SpawnInfiniPearl(player);
                                }else{
                                    Main.SendPlayerMessage(player,ChatColor.RED +"A strange force prevents your from creating another" + ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Pearl" + ChatColor.RED + " so close to the one in your inventory.");
                                }
                            }else{
                                Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an Infini-Pearl.");
                            }
                            return true;

                        // Unknown argument.
                        default:
                            Main.SendPlayerMessage(player,ChatColor.RED + "Please enter a valid argument.");
                            return true;
                    }
                }else{
                    Main.SendPlayerMessage(player,"Menu not yet implemented. WIP");
                    return true;
                }

            } else{
                Main.SendPlayerMessage(player,ChatColor.RED + "Command not recognised.");
                return true;
            }

        }else{
            Main.SendConsoleMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
    }

    private static void SpawnInfiniBucket(Player player){
        double spawnCost;

        if(Main.config.getBoolean("bucket.options.spawning.spawnable")){
            if(Main.config.getBoolean("bucket.options.spawning.spawn-cost-enabled")){
                try{
                    spawnCost = Main.config.getDouble("bucket.options.spawning.spawn-cost");

                }catch (Exception e){
                    Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY+ "bucket.options.spawning.spawn-cost in config.yml is wrong type, try float.");
                    spawnCost  = 0;
                }

            }else{
                spawnCost  = 0;
            }

            GiveInfiniBucket(player, InfiniBucket.GetInstance(), spawnCost);
        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + "InfiniBucket has been disabled in the config.");
        }
    }

    private static void SpawnInfiniPearl(Player player){
        if(Main.config.getBoolean("pearl.spawning.spawnable")){
            if(Main.config.getBoolean("pearl.spawning.spawn-cost-enabled")){
                try{
                    double cost = Main.config.getDouble("pearl.spawning.spawn-cost");
                    GiveInfiniPearl(player, cost);
                }catch (Exception e){
                    Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY+ "bucket.options.spawning.spawn-cost in config.yml is wrong type, try float.");
                }
            }else{
                GiveInfiniPearl(player);
            }
        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + "InfiniBucket has been disabled in the config.");
        }
    }

    private static void GiveInfiniPearl(Player player){
        Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Pearl.");
        Main.pearl.giveItems(player);
    }

    private static void GiveInfiniPearl(Player player, double cost){
        if(cost == 0){
            GiveInfiniPearl(player);
        } else if (Main.economy.has(player, cost)){
            Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Pearl.");
            Main.economy.withdrawPlayer(player, cost);
            Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + cost + ChatColor.WHITE + ".");
            Main.pearl.giveItems(player);
        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
        }
    }

    private static void GiveInfiniBucket(Player player, InfiniBucket bucketItem){
        Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket.");
        bucketItem.giveItems(player);
    }

    private static void GiveInfiniBucket(Player player, InfiniBucket bucketItem, double cost){
        if(cost == 0){
            GiveInfiniBucket(player, bucketItem);
        } else if (Main.economy.has(player, cost)){
            Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket.");
            Main.economy.withdrawPlayer(player, cost);
            Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + cost + ChatColor.WHITE + ".");
            bucketItem.giveItems(player);
        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
        }
    }


    public static void SpawnInfiniPearl(Player player){

    }




}
