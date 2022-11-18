package com.datdeveloper.datfactions.api.events;

import com.datdeveloper.datfactions.factionData.Faction;
import net.minecraft.commands.CommandSource;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Fired when the faction name changes
 * Can be cancelled, changes to the motd will be reflected
 */
@Cancelable
public class FactionChangeMotdEvent extends FactionEvent {
    String newMotd;
    public FactionChangeMotdEvent(@Nullable CommandSource instigator, @NotNull Faction faction, String newMotd) {
        super(instigator, faction);
        this.newMotd = newMotd;
    }

    public String getNewMotd() {
        return newMotd;
    }

    public void setNewMotd(String newMotd) {
        this.newMotd = newMotd;
    }
}