package cz.maku.friday.bukkit.event;

import cz.maku.friday.player.FridayPlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FridayPlayerLoadEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final FridayPlayer fridayPlayer;

    public FridayPlayerLoadEvent(FridayPlayer fridayPlayer) {
        this.fridayPlayer = fridayPlayer;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public void kickPlayer(String reason) {
        fridayPlayer.kick(reason);
    }

}
