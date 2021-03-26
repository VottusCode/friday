package cz.maku.friday.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SpigotUtil {

  public static double[] getTPS() {
    String version = Bukkit.getServer().getClass().getPackage().getName();
    version = version.substring(version.lastIndexOf(".") + 1);

    try {
      Class<?> serverClass = Class.forName(
        "net.minecraft.server." + version + ".MinecraftServer"
      );
      Object serverObject = serverClass
        .getDeclaredMethod("getServer")
        .invoke(serverClass);
      Field field = serverClass.getDeclaredField("recentTps");
      Object tpsObj = field.get(serverObject);
      double[] result = (double[]) tpsObj;
      return result;
    } catch (
      NoSuchMethodException
      | IllegalAccessException
      | InvocationTargetException
      | NoSuchFieldException
      | ClassNotFoundException var6
    ) {
      var6.printStackTrace();
      return null;
    }
  }

  public static String centerTitle(String title) {
    StringBuilder result = new StringBuilder();
    int spaces = (27 - ChatColor.stripColor(title).length());

    for (int i = 0; i < spaces; i++) {
      result.append(" ");
    }

    return result.append(title).toString();
  }

  public static ItemStack createHead(String value) {
    ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
    SkullMeta meta = (SkullMeta) head.getItemMeta();
    GameProfile profile = new GameProfile(UUID.randomUUID(), "");
    profile.getProperties().put("textures", new Property("textures", value));
    Field profileField = null;
    try {
      profileField = meta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(meta, profile);
    } catch (
      IllegalArgumentException
      | IllegalAccessException
      | NoSuchFieldException
      | SecurityException e
    ) {
      e.printStackTrace();
    }
    head.setItemMeta(meta);
    return head;
  }
}
