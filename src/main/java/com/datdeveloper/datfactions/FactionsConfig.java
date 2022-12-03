package com.datdeveloper.datfactions;

import com.datdeveloper.datfactions.factionData.EFactionFlags;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.HashMap;
import java.util.Map;

public class FactionsConfig {
    // Faction Management
    private static ConfigValue<Integer> maxFactionNameLength;
    private static ConfigValue<Integer> maxFactionDescriptionLength;
    private static ConfigValue<Integer> maxFactionMotdLength;

    private static ConfigValue<Integer> maxFactionRoles;

    private static ConfigValue<Integer> globalMaxFactionLandCount;

    private static ConfigValue<Long> factionOfflineExpiryTime;
    private static ConfigValue<Boolean> removePlayerOnBan;

    // Power
    private static ConfigValue<Integer> playerMaxPower;
    private static ConfigValue<Integer> playerMinPower;
    private static ConfigValue<Integer> factionMaxPower;

    private static ConfigValue<Float> powerLandMultiplier;

    private static ConfigValue<Long> playerPassivePowerGainInterval;
    private static ConfigValue<Integer> playerPassivePowerGainAmount;
    private static ConfigValue<Float> rolePassivePowerGainMultiplier;

    private static ConfigValue<Integer> baseKillPowerGain;
    private static ConfigValue<Integer> baseKillMaxPowerGain;
    private static ConfigValue<Float> noFactionKillPowerMultiplier;
    private static ConfigValue<Float> enemyKillPowerMultiplier;
    private static ConfigValue<Float> roleKillPowerMultiplier;

    private static ConfigValue<Float> bonusPowerFlagMultiplier;

    // Misc
    private static ConfigValue<Boolean> useFactionChat;
    private static ConfigValue<Integer> teleportDelay;
    private static final Map<EFactionFlags, ConfigValue<Boolean>> flagBlacklist = new HashMap<>();

    FactionsConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("Faction Management");
        {
            maxFactionNameLength = builder
                    .comment("The maximum length a faction's name can be")
                    .defineInRange("MaxFactionNameLength", 20, 0, Integer.MAX_VALUE);
            maxFactionDescriptionLength = builder
                    .comment("The maximum length a faction's description can be")
                    .defineInRange("MaxFactionDescriptionLength", 120, 0, Integer.MAX_VALUE);
            maxFactionMotdLength = builder
                    .comment("The maximum length a faction's MOTD can be")
                    .defineInRange("MaxFactionMotdLength", 120, 0, Integer.MAX_VALUE);
            maxFactionRoles = builder
                    .comment("The maximum amount of roles a faction can have")
                    .defineInRange("MaxFactionRoles", 120, 0, Integer.MAX_VALUE);

            globalMaxFactionLandCount = builder
                    .comment("The total maximum amount of chunks a faction can have across all worlds")
                    .defineInRange("globalMaxFactionLandCount", 100, 0, Integer.MAX_VALUE);

            factionOfflineExpiryTime = builder
                    .comment("The amount of time a faction can spend offline before it is deleted in milliseconds, set to 0 to disable")
                    .defineInRange("FactionOfflineExpiryTime", 0, 0, Long.MAX_VALUE);
            removePlayerOnBan = builder
                    .comment("Whether to remove the player's info when they are banned from the server")
                    .define("RemovePlayerInfoOnBan", true);
        } builder.pop();

        builder.push("Power");
        {
            playerMaxPower = builder
                    .comment("The maximum amount of power a player can have")
                    .defineInRange("PlayerMaxPower", 200, 0, Integer.MAX_VALUE);
            playerMinPower = builder
                    .comment("The minimum amount of power a player can have")
                    .define("PlayerMaxPower", 0);
            factionMaxPower = builder
                    .comment("The maximum amount of power a faction can have")
                    .defineInRange("FactionMaxPower", 0, 0, Integer.MAX_VALUE);

            powerLandMultiplier = builder
                    .comment("How much to multiply the faction power by to get the amount of chunk worth the faction is able to hold")
                    .define("PowerLandMultiplier", 1.f);

            playerPassivePowerGainInterval = builder
                    .comment("The amount of time in milliseconds between a player passively gaining max power")
                    .defineInRange("PlayerPassivePowerGainInterval", 1800, 0, Long.MAX_VALUE);
            playerPassivePowerGainAmount = builder
                    .comment("The base amount of max power a player gains passively just by being online")
                    .define("PlayerPassivePowerGainAmount", 5);
            rolePassivePowerGainMultiplier = builder
                    .comment(
                            "The multiplier for passive power gain for being the owner of the faction",
                            "The multiplier for other roles is inferred from their position in the faction's role hierarchy"
                    )
                    .define("RolePassivePowerGainMultiplier", 2.f);

            baseKillPowerGain = builder
                    .comment("The base amount of power a player gains by killing a player")
                    .define("BaseKillPowerGain", 5);
            baseKillMaxPowerGain = builder
                    .comment("The base amount of max power a player gains by killing a player")
                    .define("BaseKillMaxPowerGain", 5);

            noFactionKillPowerMultiplier = builder
                    .comment("The multiplier for the amount of power a player gains by killing a player who isn't in a faction")
                    .define("NoFactionKillPowerMultiplier", 0.f);
            enemyKillPowerMultiplier = builder
                    .comment("The multiplier for the amount of power a player gains by killing a player who is in an enemy faction")
                    .define("EnemyKillPowerMultiplier", 2.f);
            roleKillPowerMultiplier = builder
                .comment(
                        "The multiplier for passive power gain for being the owner of the faction",
                        "The multiplier for other roles is inferred from their position in the faction's role hierarchy"
                )
                .define("RoleKillPowerMultiplier", 2.f);

            bonusPowerFlagMultiplier = builder
                .comment(
                        "The multiplier for power gained when killing a player on chunks owned by a faction with the BONUSPOWER flag"
                )
                .define("BonusPowerFlagMultiplier", 2.f);
        } builder.pop();

        builder.push("Miscellaneous");
        {
            useFactionChat = builder
                    .comment("Allow Enable the faction chat system, allows players to talk with just their faction")
                    .define("UseFactionChat", true);

            teleportDelay = builder
                    .comment("The amount of time a player must stand still in seconds before they teleport to home")
                    .defineInRange("TeleportDelay", 5, 0, Integer.MAX_VALUE);

            builder
                    .comment("Allow/Disallow players setting specific faction flags")
                    .push("Allowed Faction Flags");
            for (final EFactionFlags factionFlag : EFactionFlags.values()) {
                if (factionFlag.admin) continue;

                flagBlacklist.put(
                        factionFlag,
                        builder
                                .comment(factionFlag.description)
                                .define(String.valueOf(factionFlag), true)
                );
            } builder.pop();
        } builder.pop();
    }

    public static int getMaxFactionNameLength() {
        return maxFactionNameLength.get();
    }

    public static int getMaxFactionDescriptionLength() {
        return maxFactionDescriptionLength.get();
    }

    public static int getMaxFactionMotdLength() {
        return maxFactionMotdLength.get();
    }

    public static int getMaxFactionRoles() {
        return maxFactionRoles.get();
    }

    public static int getGlobalMaxFactionLandCount() {
        return globalMaxFactionLandCount.get();
    }

    public static long getFactionOfflineExpiryTime() {
        return factionOfflineExpiryTime.get();
    }

    public static boolean getRemovePlayerOnBan() {
        return removePlayerOnBan.get();
    }

    public static int getPlayerMaxPower() {
        return playerMaxPower.get();
    }
    public static int getPlayerMinPower() {
        return playerMinPower.get();
    }

    public static int getFactionMaxPower() {
        return factionMaxPower.get();
    }

    public static float getPowerLandMultiplier() {
        return powerLandMultiplier.get();
    }

    public static long getPlayerPassivePowerGainInterval() {
        return playerPassivePowerGainInterval.get();
    }

    public static int getPlayerPassivePowerGainAmount() {
        return playerPassivePowerGainAmount.get();
    }

    public static float getRolePassivePowerGainMultiplier() {
        return rolePassivePowerGainMultiplier.get();
    }

    public static int getBaseKillPowerGain() {
        return baseKillPowerGain.get();
    }

    public static int getBaseKillMaxPowerGain() {
        return baseKillMaxPowerGain.get();
    }

    public static float getNoFactionKillPowerMultiplier() {
        return noFactionKillPowerMultiplier.get();
    }

    public static float getEnemyKillPowerMultiplier() {
        return enemyKillPowerMultiplier.get();
    }

    public static float getroleKillPowerMultiplier() {
        return roleKillPowerMultiplier.get();
    }

    public static float getBonusPowerFlagMultiplier() {
        return bonusPowerFlagMultiplier.get();
    }

    public static boolean getUseFactionChat() {
        return useFactionChat.get();
    }

    public static int getTeleportDelay() {
        return teleportDelay.get();
    }

    public static boolean getFlagBlacklisted(final EFactionFlags flag) {
        return flagBlacklist.get(flag).get();
    }
}
