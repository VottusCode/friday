package cz.maku.friday.bukkit.event;

import cz.maku.friday.player.FridayPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FridayPlayerQuitEvent extends Event {

    private final FridayPlayer fridayPlayer;

    public FridayPlayerQuitEvent(FridayPlayer fridayPlayer) {
        this.fridayPlayer = fridayPlayer;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public FridayPlayer getFridayPlayer() {
        return fridayPlayer;
    }

}
