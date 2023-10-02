package com.datdeveloper.datfactions.api.events;

import com.datdeveloper.datfactions.factiondata.Faction;
import com.datdeveloper.datfactions.factiondata.permissions.ERolePermissions;
import com.datdeveloper.datfactions.factiondata.permissions.FactionRole;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * Events for when permissions are added to/removed from a faction role
 * @see FactionRoleChangePermissionsEvent.PreAdd
 * @see FactionRoleChangePermissionsEvent.PreRemove
 * @see FactionRoleChangePermissionsEvent.PostAdd
 * @see FactionRoleChangePermissionsEvent.PostRemove
 */
public class FactionRoleChangePermissionsEvent extends FactionRoleEvent {
    /**
     * The permissions being added to/removed from the role
     */
    Set<ERolePermissions> permissions;

    /**
     * @param faction The faction the event is about
     * @param role The role gaining/losing the permissions
     * @param permissions The permissions being added to/removed from the role
     */
    protected FactionRoleChangePermissionsEvent(@NotNull final Faction faction, @NotNull final FactionRole role, final Set<ERolePermissions> permissions) {
        super(faction, role);
        this.permissions = permissions;
    }

    /**
     * Get the permissions being added to/removed from the role
     * @return the permissions being added to/removed from the role
     */
    public Set<ERolePermissions> getPermissions() {
        return permissions;
    }

    /**
     * Superclass for pre permission change events
     * <br>
     * Supplies a setter for the permissions
     */
    @HasResult
    protected static class Pre extends FactionRoleChangePermissionsEvent implements IFactionPreEvent, IFactionEventDenyReason {
        /** The instigator of the action (if there is one) */
        private final ServerPlayer instigator;

        /** A reason for why the event was denied */
        private Component denyReason = null;

        /**
         * @param instigator  The player that instigated the event
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to/removed from the role
         */
        public Pre(@Nullable final ServerPlayer instigator, @NotNull final Faction faction, @NotNull final FactionRole role, final Set<ERolePermissions> permissions) {
            super(faction, role, permissions);
            this.instigator = instigator;
        }

        /**
         * Set the permissions being added to/removed from the role
         * @param permissions the Permissions being added to/removed from the role
         */
        public void setPermissions(final Set<ERolePermissions> permissions) {
            this.permissions = permissions;
        }

        /** {@inheritDoc} */
        @Override
        public @Nullable ServerPlayer getInstigator() {
            return instigator;
        }

        /** {@inheritDoc} */
        @Override
        public Component getDenyReason() {
            return denyReason;
        }

        /** {@inheritDoc} */
        @Override
        public void setDenyReason(final Component denyReason) {
            this.denyReason = denyReason;
        }
    }

    /**
     * Fired before permissions are added to a role
     * <br>
     * The purpose of this event is to allow modifying/checking the permissions that are being added to a role.
     * This could be to make sure a specific permission is always added at the same time as another one, or to
     * deny a specific permission for a specific role.
     * <p>
     *     This event {@linkplain HasResult has a result}.<br>
     *     To change the result of this event, use {@link #setResult}. Results are interpreted in the following manner:
     * </p>
     * <ul>
     *     <li>Allow - The check will succeed, and the permissions will be added to the role</li>
     *     <li>Default - The additional permissions will be accepted if the player already has those permissions</li>
     *     <li>Deny - The check will fail, and the permissions will not be added.</li>
     * </ul>
     * <p>
     *     When setting the result to deny, you should provide a reason with {@link #setDenyReason(Component)} to
     *     allow commands to give a reason for not finishing
     * </p>
     */
    @HasResult
    public static class PreAdd extends Pre {
        /**
         * @param instigator  The player that instigated the event
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to the role
         */
        public PreAdd(@Nullable final ServerPlayer instigator,
                   @NotNull final Faction faction,
                   @NotNull final FactionRole role,
                   final Set<ERolePermissions> permissions) {
            super(instigator, faction, role, permissions);
        }
    }

    /**
     * Fired before permissions are removed from a role
     * <br>
     * The purpose of this event is to allow modifying/checking the permissions that are being removed from A role.
     * This could be to make sure a specific permission is always added at the same time as another one, or to
     * deny a specific permission for a specific role.
     * <p>
     *     This event {@linkplain HasResult has a result}.<br>
     *     To change the result of this event, use {@link #setResult}. Results are interpreted in the following manner:
     * </p>
     * <ul>
     *     <li>Allow - The check will succeed, and the permissions will be removed from the role</li>
     *     <li>Default - The removal of the permissions will be accepted if the player already has those permissions</li>
     *     <li>Deny - The check will fail, and the permissions will not be removed.</li>
     * </ul>
     * <p>
     *     When setting the result to deny, you should provide a reason with {@link #setDenyReason(Component)} to
     *     allow commands to give a reason for not finishing
     * </p>
     */
    @HasResult
    public static class PreRemove extends Pre {
        /**
         * @param instigator  The player that instigated the event
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to/removed from the role
         */
        public PreRemove(@Nullable final ServerPlayer instigator,
                         @NotNull final Faction faction,
                         @NotNull final FactionRole role,
                         final Set<ERolePermissions> permissions) {
            super(instigator, faction, role, permissions);
        }
    }

    /**
     * Superclass for post permission change events
     * <br>
     * Sets the flags set to unmodifiable
     */
    public static class Post extends FactionRoleChangePermissionsEvent {
        /**
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to/removed from the role
         */
        protected Post(@NotNull final Faction faction,
                       @NotNull final FactionRole role,
                       final Set<ERolePermissions> permissions) {
            super(faction, role, Collections.unmodifiableSet(permissions));
        }
    }

    /**
     * Fired after permissions are added to a role
     * <br>
     * The intention of this event is to allow observing permission additions for roles to update other resources
     */
    public static class PostAdd extends Post {
        /**
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to/removed from the role
         */
        protected PostAdd(@NotNull final Faction faction, @NotNull final FactionRole role, final Set<ERolePermissions> permissions) {
            super(faction, role, permissions);
        }
    }

    /**
     * Fired after permissions are removed from a role
     * <br>
     * The intention of this event is to allow observing permission removals for roles to update other resources
     */
    public static class PostRemove extends Post {
        /**
         * @param faction     The faction the event is about
         * @param role        The role gaining/losing the permissions
         * @param permissions The permissions being added to/removed from the role
         */
        protected PostRemove(@NotNull final Faction faction, @NotNull final FactionRole role, final Set<ERolePermissions> permissions) {
            super(faction, role, permissions);
        }
    }
}