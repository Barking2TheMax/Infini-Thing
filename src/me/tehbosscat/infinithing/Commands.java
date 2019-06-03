package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import me.tehbosscat.infinithing.Menus.BaseMenu.Menu;
import me.tehbosscat.infinithing.Menus.BaseMenu.MenuItem;
import me.tehbosscat.infinithing.Menus.InfiniMenuBehaviours.BucketSpawnButtonBehaviour;
import me.tehbosscat.infinithing.Menus.BaseMenu.CloseButtonBehaviour;
import me.tehbosscat.infinithing.Menus.InfiniMenuBehaviours.PearlSpawnButtonBehaviour;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

public class Commands extends CommandExecute implements Listener, CommandExecutor {
    private InfiniItemFactory f;
    private Menu menu;

    public final String baseCmd = "infini";
    public final ArrayList<String> baseArgArray = new ArrayList<>(Arrays.asList("bucket", "pearl"));

    public Commands(){
        f = InfiniItemFactory.GetInstance();
        menu = InitialiseInfiniMenu();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase(baseCmd)){
                if(args.length == 0){
                    menu.Show(player);
                    return true;

                } else if(args.length == 1){
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
                    Main.SendPlayerMessage(player,ChatColor.RED + "Too many arguments.");
                    return false;
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

    private Menu InitialiseInfiniMenu(){
        Menu menu = new Menu(
                "Select an " + ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Item",
                4
        );

        AddInfiniMenuButton(menu, f.GetItem("empty"), new BucketSpawnButtonBehaviour(), 12);
        AddInfiniMenuButton(menu, f.GetItem("pearl"), new PearlSpawnButtonBehaviour(), 14);

        MenuItem exitButton = new MenuItem.Builder(Material.STAINED_GLASS_PANE)
                .Text("Close Menu")
                .MaterialByte(14)
                .Action(new CloseButtonBehaviour())
                .build();
        menu.UpdateMenuItem(31, exitButton);

        return menu;
    }

    private void AddInfiniMenuButton(Menu m, InfiniItem item, I_OnClickBehaviour behaviour, int index){
        MenuItem button = new MenuItem.Builder(item.GetMaterial())
                .Text("Spawn " + item.GetName())
                .Action(behaviour)
                .build();

        double spawnCost = item.GetSpawnPrice();

        if (spawnCost > 0){
            button.AddSubText("Costs " + ChatColor.GREEN + "$" + item.GetSpawnPrice() + ChatColor.DARK_PURPLE + ChatColor.ITALIC + " to spawn.");
        }

        m.UpdateMenuItem(index, button);
    }
}
