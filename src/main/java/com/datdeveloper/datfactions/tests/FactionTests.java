package com.datdeveloper.datfactions.tests;

import com.datdeveloper.datfactions.Datfactions;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(Datfactions.MODID)
public class FactionTests extends BaseTest {
    @GameTest(template = "empty")
    @PrefixGameTestTemplate(false)
    public static void testSetup(GameTestHelper helper) {

    }
    
    @GameTest(template = "empty")
    @PrefixGameTestTemplate(false)
    public static void testCommands(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();

        ServerPlayer serverPlayer = makeMockServerPlayer(player);
        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack();
        Commands commands = player.getServer().getCommands();
        ParseResults<CommandSourceStack> command = commands.getDispatcher().parse("factions create test", commandSourceStack);
        int result = commands.performCommand(command, "factions create test");
        assert (result == 1);
    }
}