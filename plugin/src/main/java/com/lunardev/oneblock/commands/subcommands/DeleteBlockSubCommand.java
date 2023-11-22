package com.lunardev.oneblock.commands.subcommands;

import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.settings.Localization;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class DeleteBlockSubCommand extends SimpleSubCommand {

    public DeleteBlockSubCommand() {
        super("delete|d");
        setDescription("Deletes your block.");
    }

    @Override
    protected void onCommand() {
        checkConsole();

        boolean wasDeleted = BlockDetails.deleteBreakBlock(getPlayer());

        if (!wasDeleted) {
            Common.tell(getPlayer(), Localization.Blocks.BLOCK_NOT_FOUND);
            return;
        }

        Common.tell(getPlayer(), Localization.Blocks.BLOCK_DELETED);

    }
}
