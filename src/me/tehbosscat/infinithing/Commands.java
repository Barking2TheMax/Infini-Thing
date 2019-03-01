package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Items.InfiniBucket;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Commands extends CommandExecute implements Listener, CommandExecutor {

    public String baseCmd = "infini";
    public ArrayList<String> baseArgArray = new ArrayList<>(Arrays.asList("bucket", "pearl"));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase(baseCmd)){
                if(args.length > 0){
                    int index = baseArgArray.indexOf( args[0].toLowerCase() );
                    double cost = 0;

                    switch(index){
                        // Bucket
                        case 0:
                            if(Main.config.getBoolean("bucket.spawn-cost-enabled")){
                                try{
                                    cost = Main.config.getDouble("bucket.spawn-cost");
                                    GiveInfiniBucket(player, Main.empty, cost);
                                }catch (Exception e){
                                    Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY+ "bucket.spawn-cost in config.yml is wrong type, try float.");
                                }
                            }else{
                                GiveInfiniBucket(player, Main.empty, cost);
                            }
                            return true;

                        // Pearl
                        case 1:
                            Main.SendPlayerMessage((Player)sender,ChatColor.RED + "InfiniPearl not yet implemented.");
                            return true;

                        // Unknown argument.
                        default:
                            Main.SendPlayerMessage((Player)sender,ChatColor.RED + "Please enter a valid argument.");
                            return false;
                    }
                }else{
                    Main.SendPlayerMessage((Player)sender,ChatColor.RED + "Please enter a valid argument.");
                    return false;
                }

            } else{
                Main.SendPlayerMessage((Player)sender,ChatColor.RED + "Command not recognised.");
                return false;
            }
        }else{
            Main.SendConsoleMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
    }

    public static void GiveInfiniBucket(Player player, InfiniBucket bucketItem, double cost){
        if (Main.economy.has(player, cost)){
            ItemStack bucket = bucketItem.createInstance();

            if(!player.getInventory().contains(bucket)){
                Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket.");
                Main.economy.withdrawPlayer(player, cost);
                Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + cost + ChatColor.WHITE + ".");
                bucketItem.giveItems(player);
            }else{
                Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + bucket.getItemMeta().getDisplayName() + ".");
            }
        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
        }
    }

    public static void GiveInfiniBucket(Player player, InfiniBucket bucketItem){
        ItemStack bucket = bucketItem.createInstance();

        if(!player.getInventory().contains(bucket)){
            Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket.");
            bucketItem.giveItems(player);
        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + bucket.getItemMeta().getDisplayName() + ".");
        }
    }
}