package cz.maku.friday.bukkit.sign;

import cz.maku.friday.Friday;
import cz.maku.friday.util.Logger;
import java.util.List;
import lombok.Data;
import org.bukkit.Location;

@Data
public class Sign {

  private final Location location;
  private final org.bukkit.block.Sign sign;
  private final Logger logger;

  public Sign(Location location) {
    this.location = location;
    this.sign = (org.bukkit.block.Sign) location.getBlock().getState();
    this.logger = Friday.getPlugin().getLog();
  }

  public void setLines(List<Object> lines) {
    if (lines.size() > 3) {
      throw new IllegalArgumentException(
        "Maximum size of List can be 3 (0 - 3)."
      );
    } else {
      for (int i = 0; i < 3; i++) {
        sign.setLine(i, (String) lines.get(i));
        sign.update();
      }
    }
  }

  public String getLine(int line) {
    return sign.getLine(line);
  }
}
