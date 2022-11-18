package com.datdeveloper.datfactions.api.events;

import com.datdeveloper.datfactions.factionData.Faction;
import com.datdeveloper.datfactions.factionData.permissions.FactionRole;
import net.minecraft.commands.CommandSource;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Fired when a faction changes the position of a role in the hierarchy
 * Cancellable, and changes to newParent will be reflected
 */
@Cancelable
public class FactionRoleChangeOrderEvent extends FactionRoleEvent {
    /**
     * The new parent of the role
     */
    @NotNull
    FactionRole newParent;

    public FactionRoleChangeOrderEvent(@Nullable CommandSource instigator, @NotNull Faction faction, @NotNull FactionRole role, @NotNull FactionRole newParent) {
        super(instigator, faction, role);
        this.newParent = newParent;
    }

    public @NotNull FactionRole getNewParent() {
        return newParent;
    }

    public void setNewParent(@NotNull FactionRole newParent) {
        this.newParent = newParent;
    }
}
