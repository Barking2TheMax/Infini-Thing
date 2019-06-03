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
    private final MenuItem EMPTY_SLOT;


    public Menu(String init_title, int rows){
        TITLE = init_title;
        EMPTY_SLOT = new MenuItem.Builder(Material.AIR)
                .build();
        menuItems = InitialiseMenuItems(rows);

        Main.pluginManager.registerEvents(this, Main.plugin);
    }

    public Menu(String init_title, int rows, Material emptySlot, int materialByte){
        TITLE = init_title;
        EMPTY_SLOT = new MenuItem.Builder(emptySlot)
                .MaterialByte(materialByte)
                .build();
        menuItems = InitialiseMenuItems(rows);

        Main.pluginManager.registerEvents(this, Main.plugin);
    }

    public MenuItem GetMenuItem(int index){
        return menuItems[index];
    }

    public void UpdateMenuItem(int index, MenuItem item){
        menuItems[index] = item;
    }

    public void RemoveMenuItem(int index){
        menuItems[index] = EMPTY_SLOT;
    }

    private MenuItem[] InitialiseMenuItems(int rows){
        MenuItem[] array = new MenuItem[rows * 9];

        for (int i = 0; i < array.length; i++) {
            array[i] = EMPTY_SLOT;
        }

        return array;
    }

    public void Show(Player player){
        int length = menuItems.length;
        Inventory menu = Main.server.createInventory(null, length, TITLE);

        for (int i = 0; i < length; i++) {
            menu.setItem(i, menuItems[i].CreateItem());
        }

        player.openInventory(menu);
    }

    public boolean ItemInMenuItems(ItemStack item){
        for (MenuItem menuItem : menuItems) {
            if(menuItem.equals(EMPTY_SLOT)) continue;

            if (menuItem.CreateItem().equals(item)){
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if(inventory == null) return;
        if(!inventory.getName().equals(TITLE)) return;

        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();

        if(item == null || !ItemInMenuItems(item)) return;

        menuItems[event.getSlot()].OnClickAction(player);

        player.closeInventory();
    }
}

