package BarkerDevelopment.InfiniThing;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.InfiniThing.Items.InfiniItem;
import BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours.BucketSpawnButtonBehaviour;
import BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours.PearlSpawnButtonBehaviour;
import BarkerDevelopment.MinecraftMenus.Behaviours.CloseButtonBehaviour;
import BarkerDevelopment.MinecraftMenus.*;
import BarkerDevelopment.MinecraftMenus.Behaviours.*;
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


/**
 * Handles command execution.
 */
public class Commands extends CommandExecute implements Listener, CommandExecutor {
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
                            InfiniItem.ItemSpawnQuery(player, f.GetItem("empty"));
                            return true;

                        case 1: // Pearl
                            InfiniItem.ItemSpawnQuery(player, f.GetItem("pearl"));
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
        Menu menu = new Menu.Builder(Main.plugin)
                .Title("Select an " + ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Item")
                .Rows(4)
                .build();

        AddInfiniMenuButton(menu, f.GetItem("empty"), new BucketSpawnButtonBehaviour(), 12);
        AddInfiniMenuButton(menu, f.GetItem("pearl"), new PearlSpawnButtonBehaviour(), 14);

        MenuItem exitButton = new MenuItem.Builder(Material.STAINED_GLASS_PANE)
                .Text("Close Menu")
                .MaterialByte(14)
                .Action(new CloseButtonBehaviour())
                .build();
        menu.SetMenuItem(31, exitButton);

        return menu;
    }

    /**
     * Adds a MenuItem button to the menu.
     *
     * @param m Menu object to which the MenuItem is to be added.
     * @param item The MenuItem to add.
     * @param behaviour The OnClick behaviour of the MenuItem. NOTE: If nothing is to happen when clicked, use the
     *                  NullClick behaviour.
     * @param index The index of the button in the Menu object.
     */
    private void AddInfiniMenuButton(Menu m, InfiniItem item, I_OnClickBehaviour behaviour, int index){
        MenuItem button = new MenuItem.Builder(item.GetMaterial())
                .Text("Spawn " + item.GetName())
                .Action(behaviour)
                .build();

        double spawnCost = item.GetSpawnPrice();

        if (spawnCost > 0){
            button.AddSubText("Costs " + ChatColor.GREEN + "$" + item.GetSpawnPrice() + ChatColor.DARK_PURPLE +
                    ChatColor.ITALIC + " to spawn.");
        }

        m.SetMenuItem(index, button);
    }
}
