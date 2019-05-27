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
import org.bukkit.inventory.ItemStack;

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
                            SpawnInfiniBucket(player);
                            return true;

                        // Pearl
                        case 1:
                            SpawnInfiniPearl(player);
                            Main.SendPlayerMessage(player,ChatColor.RED + "InfiniPearl not yet implemented.");
                            return true;

                        // Unknown argument.
                        default:
                            Main.SendPlayerMessage(player,ChatColor.RED + "Please enter a valid argument.");
                            return false;
                    }
                }else{
                    Main.SendPlayerMessage(player,ChatColor.RED + "Please enter a valid argument.");
                    return false;
                }

            } else{
                Main.SendPlayerMessage(player,ChatColor.RED + "Command not recognised.");
                return false;
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

    private static void GiveInfiniBucket(Player player, InfiniItem bucket, double cost){
        if (Main.economy.has(player, cost)){
            ItemStack bucketItem = f.CreateItem(bucket);

            if(!player.getInventory().contains(bucketItem)){
                Main.SendPlayerMessage(player,"Thank you for using " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket.");
                Main.economy.withdrawPlayer(player, cost);
                Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + cost + ChatColor.WHITE + ".");
                player.getInventory().addItem(bucketItem);

            }else{
                Main.SendPlayerMessage(player,ChatColor.RED + "You already have an " + bucketItem.getItemMeta().getDisplayName() + ".");
            }

        }else {
            Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
        }
    }


    public static void SpawnInfiniPearl(Player player){

    }




}
