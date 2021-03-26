package cz.maku.friday.storage.config;

import cz.maku.friday.Friday;
import cz.maku.friday.util.Logger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class Config {

  private final String fileName;
  private final JavaPlugin plugin;
  private Logger logger;
  private File file;
  private YamlConfiguration yaml;

  public Config(JavaPlugin plugin, String fileName) {
    this.fileName = fileName;
    this.plugin = plugin;
    logger = Friday.getPlugin().getLog();
    file = new File(plugin.getDataFolder() + "/" + fileName + ".yml");
    yaml = YamlConfiguration.loadConfiguration(file);
  }

  public void createNewFile() {
    try {
      file.createNewFile();
      logger.log(
        Logger.LoggerType.SUCCESS,
        "File " + fileName + " was created at " + plugin.getDataFolder() + ".",
        true
      );
    } catch (IOException exception) {
      exception.printStackTrace();
      logger.log(
        Logger.LoggerType.ERROR,
        "There is an error while creating file " +
        fileName +
        " in folder " +
        plugin.getDataFolder() +
        ".",
        true
      );
    }
  }

  public void createStringList(String value, List<String> key) {
    yaml.set(value, key);
    save();
  }

  public void createStringList(String value, String... keys) {
    yaml.set(value, Arrays.stream(keys).collect(Collectors.toList()));
    save();
  }

  public void createSection(String value) {
    yaml.createSection(value);
    save();
  }

  public void set(String value, Object key) {
    yaml.set(value, key);
    save();
  }

  public void setLocation(String value, Location location) {
    yaml.set(value, location);
    save();
  }

  public void setStringList(String value, List<String> key) {
    yaml.set(value, key);
    save();
  }

  public void setStringList(String value, String... keys) {
    yaml.set(value, Arrays.stream(keys).collect(Collectors.toList()));
    save();
  }

  public void removeFromStringList(String value, String key) {
    List<String> list = getStringList(value);
    list.remove(key);
    setStringList(value, list);
  }

  public void addToStringList(String value, String key) {
    List<String> list = getStringList(value);
    list.add(key);
    setStringList(value, list);
  }

  public void addSectionToSection(String value, String key) {
    getSection(value).createSection(key);
  }

  public Location getLocation(String value) {
    return yaml.getLocation(value);
  }

  public Object get(String value) {
    return yaml.get(value);
  }

  public String getString(String value) {
    return yaml.getString(value);
  }

  public boolean getBoolean(String value) {
    return yaml.getBoolean(value);
  }

  public int getInteger(String value) {
    return yaml.getInt(value);
  }

  public double getDouble(String value) {
    return yaml.getDouble(value);
  }

  public long getLong(String value) {
    return yaml.getLong(value);
  }

  public String getRoute() {
    return yaml.getCurrentPath();
  }

  public List<String> getStringList(String value) {
    return yaml.getStringList(value);
  }

  public ConfigurationSection getSection(String value) {
    return yaml.getConfigurationSection(value);
  }

  public boolean isExists() {
    return file.exists();
  }

  public void save() {
    try {
      yaml.save(file);
    } catch (IOException exception) {
      exception.printStackTrace();
      logger.log(
        Logger.LoggerType.ERROR,
        "There is an error while saving file " +
        fileName +
        " in folder " +
        plugin.getDataFolder() +
        ".",
        true
      );
    }
  }

  public void reload() {
    yaml = YamlConfiguration.loadConfiguration(file);

    InputStream defaultStream = plugin.getResource(file.getName());
    if (defaultStream != null) {
      YamlConfiguration defauultConfig = YamlConfiguration.loadConfiguration(
        new InputStreamReader(defaultStream)
      );
      yaml.setDefaults(defauultConfig);
    }
  }
}
