package com.datdeveloper.datfactions.commands;

import com.datdeveloper.datfactions.commands.arguments.FactionArgument;
import com.datdeveloper.datfactions.factionData.FPlayerCollection;
import com.datdeveloper.datfactions.factionData.Faction;
import com.datdeveloper.datfactions.factionData.FactionPlayer;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.function.Predicate;

import static com.datdeveloper.datfactions.commands.FactionPermissions.FACTIONINFO;

public class FactionInfoCommand extends BaseFactionCommand {
    static void register(final LiteralArgumentBuilder<CommandSourceStack> command) {
        final Predicate<CommandSourceStack> predicate = FactionPermissions.hasPermission(FACTIONINFO);
        final LiteralCommandNode<CommandSourceStack> subCommand = Commands.literal("info")
                .requires(predicate)
                .then(Commands.argument("targetFaction", new FactionArgument())
                        .executes(c -> {
                            final FactionPlayer factionPlayer = FPlayerCollection.getInstance().getPlayer(c.getSource().getPlayer());
                            final Faction target = c.getArgument("targetFaction", Faction.class);

                            c.getSource().sendSystemMessage(target.getChatSummary(factionPlayer.getFaction()));

                            return 1;
                        }))
                .executes(c -> {
                    final FactionPlayer factionPlayer = FPlayerCollection.getInstance().getPlayer(c.getSource().getPlayer());

                    if (factionPlayer == null || !factionPlayer.hasFaction()) return 2;

                    c.getSource().sendSystemMessage(factionPlayer.getFaction().getChatSummary(factionPlayer.getFaction()));

                    return 1;
                })
                .build();

        command.then(subCommand);
        command.then(Commands.literal("faction").requires(predicate).redirect(subCommand));
        command.then(Commands.literal("show").requires(predicate).redirect(subCommand));
    }
}