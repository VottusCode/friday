package cz.maku.friday.inputmode;

import com.google.common.collect.Lists;
import cz.maku.friday.player.FridayPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;


public class FridayInputMode {

    private List<FridayPlayer> inputPlayers = Lists.newArrayList();
    @Getter
    private final FridayPlayer fp;
    @Getter
    private final String input;
    @Getter
    private final String stop;

    public FridayInputMode(FridayPlayer fp, String input, String stop) {
        this.fp = fp;
        this.input = input;
        this.stop = stop;
        inputPlayers.add(fp);
    }

    public void stop() {
        inputPlayers.remove(fp);
    }

    public boolean isInInputMode() {
        return inputPlayers.contains(fp);
    }

    public void sendInputMessage() {
        for (int i = 0; i < 4; i++) {
            fp.sendSpace();
        }
        fp.sendCenteredMessage("§aAktuálně jsi v §2§lzadávacím režimu§a.");
        fp.sendCenteredMessage("§aNapiš do chatu §b" + input + " §apro pokračování.");
        fp.sendCenteredMessage("§7Pokud chceš zadávání §c§lukončit §7stačí napsat '§b" + stop + "§7'.");
        fp.sendSpace();
    }

}
