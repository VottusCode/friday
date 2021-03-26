package cz.maku.friday.util;

import cz.maku.friday.player.FridayPlayer;
import java.util.Map;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Data
public class MenuCreator {

  private final String title;
  private final int rows;
  private Map<Integer, ItemStack> items;
  private Inventory gui;

  public MenuCreator(String title, int rows, Map<Integer, ItemStack> items) {
    this.title = title;
    this.rows = rows;
    this.items = items;
    gui = Bukkit.createInventory(null, rows * 9, title);
  }

  public MenuCreator open(FridayPlayer fp) {
    fp.open(gui);
    return this;
  }
}
