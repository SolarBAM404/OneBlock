package com.lunardev.oneblock.commands.subcommands;

import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.settings.Localization;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class InfoBlockSubCommand extends SimpleSubCommand {

    public InfoBlockSubCommand() {
        super("info|i");
        setDescription("Gets info about island.");
        setUsage("[player]");
    }

    @Override
    protected void onCommand() {
        String playerName;
        if (args.length == 0) {
            checkConsole();
            playerName = sender.getName();
        } else {
            playerName = args[0];
        }

        if (playerName == null) {
            tell(Localization.Commands.MUST_ENTER_PLAYER_NAME);
            return;
        }

        if (playerName.equals("all")) {
            tell(Localization.Commands.CANNOT_CHECK_ALL_BLOCKS);
            return;
        }

        if (playerName.equals("me")) {
            checkConsole();
            playerName = getPlayer().getName();
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        BlockDetails block = BlockDetails.getBlockDetails(player);

        if (block == null) {
            tell(Localization.Blocks.BLOCK_NOT_FOUND);
            return;
        }

        int requiredXp = block.getCurrentLevel().getLevelExperience() - block.getCurrentXp();

        for (String message : Localization.Blocks.INFO_COMMAND_MESSAGE) {
            tell(message
                    .replace("{player}", player.getName())
                    .replace("{level}", String.valueOf(block.getCurrentLevel().getLevelNumber()))
                    .replace("{blocks}", String.valueOf(block.getBlocksMined()))
                    .replace("{xp}", String.valueOf(block.getCurrentXp()))
                    .replace("{nextXp}", String.valueOf(requiredXp))
            );
        }
    }
}
