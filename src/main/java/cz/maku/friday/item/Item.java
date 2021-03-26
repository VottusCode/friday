package cz.maku.friday.item;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

  private final ItemStack is;
  private final ItemMeta meta;

  public Item(Material material, int amount) {
    is = new ItemStack(material, amount);
    meta = is.getItemMeta();
  }

  public Item setDisplayNameName(String displayName) {
    meta.setDisplayName(displayName);
    return this;
  }

  public Item setUnbreakable(boolean unbreak) {
    meta.setUnbreakable(unbreak);
    return this;
  }

  public Item hideEnchants() {
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public Item addEnchant(Enchantment enchant, int level) {
    meta.addEnchant(enchant, level, true);
    return this;
  }

  public Item addLore(String lore) {
    List<String> lores = new ArrayList<>();
    if (meta.getLore() != null && meta.getLore().size() > 0) {
      lores = meta.getLore();
    }
    lores.add(lore);
    meta.setLore(lores);
    return this;
  }

  public Item setGlow(boolean glow) {
    meta.addEnchant(Enchantment.DURABILITY, 1, glow);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public ItemStack build() {
    is.setItemMeta(meta);
    return is;
  }
}
