package me.tehbosscat.infinithing;

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

    public final String baseCmd = "infini";
    public final ArrayList<String> baseArgArray = new ArrayList<>(Arrays.asList("bucket", "pearl"));

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
                            ItemSpawnQuery(player, f.GetItem("empty"));
                            return true;

                        // Pearl
                        case 1:
                            ItemSpawnQuery(player, f.GetItem("pearl"));
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

    private void ItemSpawnQuery(Player player, InfiniItem item){
        if(player.hasPermission(item.GetPermissionPath() + ".spawn")){
            if(!player.getInventory().contains(f.CreateItem(item))){
                SpawnItem(player, item);

            }else{
                Main.SendPlayerMessage(player,ChatColor.RED +"A strange force prevents your from creating another" + item.GetName() + ChatColor.RED + " so close to the one in your inventory.");
            }

        }else{
            Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an ." + item.GetName());
        }
    }

    private void SpawnItem(Player player, InfiniItem item){ // "infini.bucket
        if(player.hasPermission(item.GetPermissionPath() + ".spawn")){
            if(!player.getInventory().contains(f.CreateItem(item))){
                SpawnInfiniItem(player, item);

            }else{
                Main.SendPlayerMessage(player,ChatColor.RED +"A strange force prevents your from creating another " + item.GetName() + ChatColor.RED + " so close to the one in your inventory.");
            }

        }else{
            Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an " + item.GetName());
        }
    }

    private void SpawnInfiniItem(Player player, InfiniItem item){
        String configPath = item.GetConfigPath();
        double spawnCost;

        if(Main.config.getBoolean(configPath + ".options.spawning.spawnable")){
            if(Main.config.getBoolean(configPath + ".options.spawning.spawn-cost-enabled")){
                try{
                    spawnCost = Main.config.getDouble(configPath + ".options.spawning.spawn-cost");

                }catch (Exception e){
                    Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + configPath + ".options.spawning.spawn-cost in config.yml is wrong type, try float.");
                    spawnCost  = 0;
                }

            }else{
                spawnCost  = 0;
            }

            if(spawnCost == 0){
                GiveInfiniItem(player, item);

            } else {
                if (Main.economy.has(player, spawnCost)){
                    Main.economy.withdrawPlayer(player, spawnCost);
                    Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + spawnCost + ChatColor.WHITE + ".");
                    GiveInfiniItem(player, item);

                }else {
                    Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
                }
            }

        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + item.GetName() + " has been disabled in the config.");
        }
    }

    private static void GiveInfiniItem(Player player, InfiniItem item){
        Main.SendPlayerMessage(player,"Thank you for using " + item.GetName());
        player.getInventory().addItem(f.CreateItem(item));
    }
}
