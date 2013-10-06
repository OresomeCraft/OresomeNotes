package com.oresomecraft.oresomenotes;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerTagEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String target;
    private String tag;
    private String tagger;

    public PlayerTagEvent(String target, String tag, String tagger) {
        this.target = target;
        this.tag = tag;
        this.tagger = tag;
    }

    public String getTagged() {
        return target;
    }

    public String getTag() {
        return tag;
    }

    public String getTagger() {
        return tagger;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}