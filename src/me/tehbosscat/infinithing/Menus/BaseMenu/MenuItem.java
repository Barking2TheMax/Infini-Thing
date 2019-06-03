package me.tehbosscat.infinithing.Menus.BaseMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MenuItem {

    public static class Builder{
        private String _text;
        private ArrayList<String> _subText;
        private Material _material;
        private int _materialByte;
        private int _num;
        private I_OnClickBehaviour _onClickAction;

        public Builder(Material material){
           _material = material;
        }

        public Builder Text(String text){
            _text = text;

            return this;
        }

        public Builder SubText(String subText){
            if (_subText == null){
                _subText = new ArrayList<String>();
            }

            _subText.add(subText);

            return this;
        }

        public Builder MaterialByte(int materialByte){
            _materialByte = materialByte;

            return this;
        }

        public Builder Num(int num){
            _num = num;

            return this;
        }

        public Builder Action(I_OnClickBehaviour action){
            _onClickAction = action;

            return this;
        }

        public MenuItem build(){
            MenuItem item = new MenuItem();
            item.text = _text == null ? "" : _text;
            item.subText = _subText == null ? new ArrayList<>() : _subText;
            item.material = _material;
            item.materialByte = _materialByte;
            item.num = _num == 0 ? 1 : _num;
            item.onClickAction = _onClickAction;

            return item;
        }
    }

    private String text;
    private ArrayList<String> subText;
    private Material material;
    private int materialByte;
    private int num;
    private I_OnClickBehaviour onClickAction;

    private MenuItem(){ }

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

    public int GetNum(){
        return num;
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


    public ItemStack CreateItem(){
        if(material == Material.AIR){
            return null;

        }else{
            ItemStack item = new ItemStack(material, num, (byte) materialByte);
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
