package com.playivanikyt.codefarmer;

import com.playivanikyt.codefarmer.enchant.CodeSwordFarmer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Используйте: /swordfarmer <give/reload>");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Используйте: /swordfarmer give <ник> <уровень>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }

            int level;
            try {
                level = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Уровень должен быть числом!");
                return true;
            }

            if (level < 2 || level > 10) {
                sender.sendMessage(ChatColor.RED + "Уровень должен быть от 2 до 10!");
                return true;
            }

            // Создаем меч
            ItemStack sword = CodeSwordFarmer.createFarmerSword(level);
            if (sword == null) {
                sender.sendMessage(ChatColor.RED + "Ошибка при создании меча!");
                return true;
            }

            target.getInventory().addItem(sword);

            // HEX-цвет в Spigot
            String hexColor = ChatColor.translateAlternateColorCodes('&', "&x&0&0&F&F&0&0");
            sender.sendMessage(ChatColor.GREEN + "Вы выдали меч игроку " + target.getName() + " с уровнем " + level);

            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("codefarmer.admin")) {
                sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                return true;
            }

            CodeFarmer.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Конфиг плагина перезагружен!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Неизвестная команда!");
        return true;
    }
}
