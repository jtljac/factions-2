package com.datdeveloper.datfactions.commands;

import com.datdeveloper.datfactions.factionData.*;
import com.datdeveloper.datmoddingapi.asyncTask.AsyncHandler;
import com.datdeveloper.datmoddingapi.command.util.Pager;
import com.datdeveloper.datmoddingapi.util.DatChatFormatting;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.datdeveloper.datfactions.commands.FactionPermissions.FACTIONLIST;

public class FactionListCommand extends BaseFactionCommand {
    static void register(final LiteralArgumentBuilder<CommandSourceStack> command) {
        final Predicate<CommandSourceStack> predicate = FactionPermissions.hasPermission(FACTIONLIST);
        final LiteralCommandNode<CommandSourceStack> subCommand = Commands.literal("list")
                .requires(predicate)
                .then(Commands.argument("page", IntegerArgumentType.integer(0))
                        .executes(c -> execute(c, c.getArgument("page", Integer.class))))
                .executes(c -> execute(c, 1))
                .build();

        command.then(subCommand);
        command.then(Commands.literal("faction").requires(predicate).redirect(subCommand));
        command.then(Commands.literal("show").requires(predicate).redirect(subCommand));
    }

    private static int execute(final CommandContext<CommandSourceStack> context, final int page) {
        final FactionPlayer player = FPlayerCollection.getInstance().getPlayer(context.getSource().getPlayer());
        AsyncHandler.runAsyncTask(() -> {
            final Collection<Faction> factions = FactionCollection.getInstance().getAll().values().stream()
                    .filter(faction -> !faction.hasFlag(EFactionFlags.DEFAULT))
                    .sorted(Comparator.comparing(Faction::getName))
                    .collect(Collectors.toList());

            final Pager<Faction> pager = new Pager<>("/f list", "Factions", factions, (faction) ->
                    faction.getNameWithDescription(player.getFaction())
                    .withStyle(faction.isAnyoneOnline() ? DatChatFormatting.PlayerColour.ONLINE : DatChatFormatting.PlayerColour.OFFLINE)
            );

            pager.sendPage(page, context.getSource().source);
        });
        return 1;
    }
}