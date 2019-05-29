package me.tehbosscat.infinithing.Menus.BaseMenu;

import me.tehbosscat.infinithing.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu implements Listener {
    private String TITLE;
    private MenuItem[] menuItems;
    private final Material EMPTY_SLOT;


    public Menu(String init_title){
        TITLE = init_title;
        menuItems = new MenuItem[9];
        EMPTY_SLOT = Material.AIR;
    }

    public Menu(String init_title, int rows, Material emptySlot){
        TITLE = init_title;
        menuItems = new MenuItem[rows * 9];
        EMPTY_SLOT = emptySlot;
    }

    public MenuItem GetMenuItem(int index){
        return menuItems[index];
    }

    public void UpdateMenuItem(int index, MenuItem item){
        menuItems[index] = item;
    }

    public void RemoveMenuItem(int index){
        menuItems[index] = new MenuItem(EMPTY_SLOT);
    }


    public void Show(Player player){
        int length = menuItems.length;
        Inventory menu = Main.server.createInventory(null, length, TITLE);

        for (int i = 0; i < length; i++) {
            menu.setItem(i, menuItems[i].CreateItem());
        }

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if(inventory == null) return;
        if(!inventory.getName().equals(TITLE)) return;

        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();

        if(item == null || !item.hasItemMeta()) return;

        menuItems[event.getSlot()].OnClickAction(player);

        player.closeInventory();
    }
}

