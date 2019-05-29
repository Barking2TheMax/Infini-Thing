package me.tehbosscat.infinithing.Menus.BaseMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MenuItem {
    private String text;
    private ArrayList<String> subText;
    private Material material;
    private int num;
    private I_OnClickBehaviour onClickAction;


    public MenuItem(Material init_material){
        text = "";
        subText = new ArrayList<>();
        material = init_material;
        num = 1;
        onClickAction = new NullClick();
    }

    public MenuItem(String init_text, Material init_material, int init_num){
        text = init_text;
        subText = new ArrayList<>();
        material = init_material;
        num = init_num;
        onClickAction = new NullClick();
    }

    public MenuItem(String init_text, String init_subText, Material init_material, int init_num){
        text = init_text;
        subText = new ArrayList<>();
        subText.add(init_subText);
        material = init_material;
        num = init_num;
        onClickAction = new NullClick();
    }


    public String GetText(){
        return text;
    }

    public ArrayList<String> GetSubText(){
        return subText;
    }

    public boolean OnClickAction(Player player){
        return onClickAction.Clicked(player);
    }


    public void SetText(String string){
        text = string;
    }

    public void SetSubText(ArrayList<String> array){
        subText = array;
    }

    public void SetSubText(String string){
        subText = new ArrayList<>();
        subText.add(string);
    }

    public void AddSubText(String string){
        subText.add(string);
    }

    public void SetNum(int integer){
        num = integer;
    }

    public void IncrementNum(){
        num++;
    }

    public void DecrementNum(){
        num--;
    }

    public void AddOnClick(I_OnClickBehaviour behaviour){
        onClickAction = behaviour;
    }

    public ItemStack CreateItem(){
        if(material == Material.AIR){
            return null;
        }else{
            ItemStack item = new ItemStack(material, num);
            return AddMeta(item);
        }
    }

    private ItemStack AddMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(text);
        meta.setLore(subText);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }


}
