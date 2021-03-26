package cz.maku.friday.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

public class FridayHologram {

    private final Location location;
    private final List<String> lines;
    private Map<ArmorStand, Location> armorStands;

    public FridayHologram(List<String> lines, Location location) {
        this.location = location;
        this.lines = lines;
    }

    public FridayHologram spawn() {
        lines.forEach(line -> {
            Location nextLoc = location.add(0, 0.5, 0);
            ArmorStand h = (ArmorStand) location.getWorld().spawnEntity(nextLoc, EntityType.ARMOR_STAND);
            h.setVisible(false);
            h.setGravity(false);
            h.setCustomNameVisible(true);
            h.setCustomName(line);
            armorStands.put(h, nextLoc);
        });
        return this;
    }

    public void delete() {
        armorStands.forEach((as, loc) -> {
            as.remove();
            armorStands.remove(as, loc);
        });
    }

}
