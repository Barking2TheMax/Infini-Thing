package BarkerDevelopment.InfiniThing;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.InfiniThing.Items.InfiniItemType;
import BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours.BucketSpawnButtonBehaviour;
import BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours.PearlSpawnButtonBehaviour;
import BarkerDevelopment.MinecraftMenus.Behaviours.CloseButtonBehaviour;
import BarkerDevelopment.MinecraftMenus.*;
import BarkerDevelopment.MinecraftMenus.Behaviours.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Command class: Handles command execution.
 */
public class Commands implements CommandExecutor {
    private InfiniItemFactory f;
    private Menu menu;

    public final String baseCmd = "infini";
    public final ArrayList<String> baseArgArray = new ArrayList<>(Arrays.asList("bucket", "pearl"));


    // Instantiation.
    /**
     * Default constructor.
     */
    public Commands(){
        f = InfiniItemFactory.GetInstance();
        menu = InitialiseInfiniMenu();
    }


    // Events.
    /**
     * Called when a user (Console or Player) enters a command into chat.
     *
     * @param sender Command sender.
     * @param cmd The root of the command.
     * @param label
     * @param args The arguments of the command.
     * @return TRUE if command is caught. FALSE if it is not caught.
     */
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
                        case 0: // Bucket
                            f.GetItem("empty").Give(player);
                            return true;

                        case 1: // Pearl
                            f.GetItem("pearl").Give(player);
                            return true;

                        default: // Unknown argument.
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


    // Methods.
    /**
     * Generates the Infini-Menu menu.
     *
     * @return An Infini-Menu menu object.
     */
    private Menu InitialiseInfiniMenu(){
        return new Menu.Builder(Main.plugin)
                .Title("Select an Infini-Item")
                .Rows(4)
                .Background(7)
                .MenuItem(AddInfiniMenuButton(f.GetItem("empty"), new BucketSpawnButtonBehaviour()), 12)
                .MenuItem(AddInfiniMenuButton(f.GetItem("pearl"), new PearlSpawnButtonBehaviour()), 14)
                .MenuItem(new MenuItem.Builder(Material.STAINED_GLASS_PANE)
                        .Text("Close Menu")
                        .MaterialByte(14)
                        .Action(new CloseButtonBehaviour())
                        .build(), 31)
                .build();
    }

    /**
     * Adds a MenuItem button to the menu.
     *
     * @param item The MenuItem to add.
     * @param behaviour The OnClick behaviour of the MenuItem. NOTE: If nothing is to happen when clicked, use the
     *                  NullClick behaviour.
     */
    private MenuItem AddInfiniMenuButton(InfiniItemType item, I_OnClickBehaviour behaviour){
        MenuItem.Builder bb = new MenuItem.Builder(item.GetMaterial())
                .Text(item.GetName())
                .Action(behaviour);

        double spawnCost = item.GetSpawnPrice();
        if (spawnCost > 0){
            bb.SubText("Costs " + ChatColor.GREEN + "$" + item.GetSpawnPrice() + ChatColor.DARK_PURPLE +
                    ChatColor.ITALIC + " to spawn.");
        }

        return bb.build();
    }
}
