package com.lunardev.oneblock.commands;

import com.lunardev.lunarlib.commands.CommandBuilder;
import com.lunardev.lunarlib.commands.CommandFramework;
import com.lunardev.lunarlib.commands.CommandParameters;
import com.lunardev.oneblock.OneBlockPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MainCommand {

    MainCommand() {
    }

    public static Component helpCommand() {
        TextComponent solidLinePart = Component.text(" ", NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.STRIKETHROUGH);
        Component solidLine = Component.text("").append(solidLinePart);
        for (int i = 0; i < 15; i++) {
            solidLine = solidLine.append(solidLinePart);
        }

        Component component = Component.text("Help Command");
        return solidLine.append(component).append(solidLine);
    }

    public static CommandFramework setupCommand(OneBlockPlugin plugin) {
        return new CommandBuilder("oneblock")
                .withAliases("ob")
                .withDescription("Main command of OneBlock")
                .withArgsAtPosition(0, setupCreateIslandCommand())
                .withAction((CommandParameters parameters) -> {
                    parameters.getSender().sendMessage(helpCommand());
                    return true;
                })
                .buildAndRegister(plugin);
    }

    private static CommandFramework setupCreateIslandCommand() {
        return new CommandBuilder("create")
                .withAliases("c")
                .withDescription("Create a new block island")
                .withAction((CommandParameters parameters) -> {
                    // TODO added functionality to create island
                    // Create a new Island
                    return true;
                })
                .build();
    }

}
