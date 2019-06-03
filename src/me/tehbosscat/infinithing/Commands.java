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
                            InfiniItem.ItemSpawnQuery(player, f.GetItem("empty"));
                            return true;

                        // Pearl
                        case 1:
                            InfiniItem.ItemSpawnQuery(player, f.GetItem("pearl"));
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
}
