package cz.maku.friday.title;

import cz.maku.friday.player.FridayPlayer;
import lombok.Data;

@Data
public class FridayTitle {

    private final String title;
    private final String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public FridayTitle(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = 10;
        this.stay = 10;
        this.fadeOut = 10;
    }

    public void send(TitleFormat format, FridayPlayer fp) {
        if (format.equals(TitleFormat.SUCCESS)) {
            fp.getPlayer().sendTitle("§2§l" + title, "§a" + subtitle, fadeIn, stay, fadeOut);
        } else if (format.equals(TitleFormat.WARNING)) {
            fp.getPlayer().sendTitle("§6§l" + title, "§e" + subtitle, fadeIn, stay, fadeOut);
        } else if (format.equals(TitleFormat.ERROR)) {
            fp.getPlayer().sendTitle("§4§l" + title, "§c" + subtitle, fadeIn, stay, fadeOut);
        } else if (format.equals(TitleFormat.LOADING)) {
            fp.getPlayer().sendTitle("§8§l" + title, "§7" + subtitle, fadeIn, stay, fadeOut);
        } else if (format.equals(TitleFormat.NONE)) {
            fp.getPlayer().sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

}
