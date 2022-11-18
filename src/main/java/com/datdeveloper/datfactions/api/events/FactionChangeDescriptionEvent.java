package com.datdeveloper.datfactions.api.events;

import com.datdeveloper.datfactions.factionData.Faction;
import net.minecraft.commands.CommandSource;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Fired when the faction name changes
 * Can be cancelled, changes to the description will be reflected
 */
@Cancelable
public class FactionChangeDescriptionEvent extends FactionEvent {
    String newDescription;
    public FactionChangeDescriptionEvent(@Nullable CommandSource instigator, @NotNull Faction faction, String newDescription) {
        super(instigator, faction);
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}