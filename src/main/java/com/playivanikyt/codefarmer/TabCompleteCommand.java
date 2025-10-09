package com.playivanikyt.codefarmer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleteCommand implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Автодополнение для команды
            completions.add("give");
            completions.add("reload");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            // Автодополнение для имен игроков
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            // Автодополнение для уровней меча (2-10)
            completions.addAll(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10"));
        }

        return completions;
    }
}
