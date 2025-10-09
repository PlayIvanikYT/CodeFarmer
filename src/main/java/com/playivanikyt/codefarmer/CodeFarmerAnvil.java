package com.playivanikyt.codefarmer;

import com.playivanikyt.codefarmer.enchant.CodeSwordFarmer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CodeFarmerAnvil implements Listener {

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack firstItem = anvil.getItem(0);
        ItemStack secondItem = anvil.getItem(1);

        if (firstItem == null || secondItem == null) {
            return; // Если нет двух предметов, ничего не делаем
        }

        // Проверяем, есть ли зачарование "Фармер" на первом предмете
        int level1 = CodeSwordFarmer.getLevel(firstItem);
        int level2 = CodeSwordFarmer.getLevel(secondItem);

        // ✅ Если два меча "Фармер" одного уровня - улучшаем уровень
        if (level1 > 0 && level2 > 0 && level1 == level2) {
            int newLevel = level1 + 1;
            if (newLevel > 10) {
                return; // Максимальный уровень - 10
            }

            // Создаем новый меч с улучшенным зачарованием
            ItemStack resultItem = CodeSwordFarmer.createFarmerSword(newLevel);
            event.setResult(resultItem);
            anvil.setRepairCost(30); // Стоимость улучшения - 30 уровней опыта
            return;
        }

        // ✅ Если "Фармер" + книга зачарований → объединяем
        if (level1 > 0 && secondItem.getType() == Material.ENCHANTED_BOOK) {
            ItemStack resultItem = firstItem.clone();
            ItemMeta meta = resultItem.getItemMeta();
            EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) secondItem.getItemMeta();

            if (meta != null && bookMeta != null) {
                // Добавляем зачарования из книги к мечу
                bookMeta.getStoredEnchants().forEach((enchant, lvl) -> meta.addEnchant(enchant, lvl, true));
                resultItem.setItemMeta(meta);
                event.setResult(resultItem);
                anvil.setRepairCost(10); // Цена объединения - 10 уровней опыта
            }
            return;
        }

        // ✅ Если обычный меч + книга "Фармер" → добавляем зачарование
        if (level2 > 0 && firstItem.getType() == Material.NETHERITE_SWORD) {
            ItemStack resultItem = firstItem.clone();
            ItemMeta meta = resultItem.getItemMeta();

            if (meta != null) {
                meta.addEnchant(new CodeSwordFarmer(), level2, true);
                resultItem.setItemMeta(meta);
                event.setResult(resultItem);
                anvil.setRepairCost(10); // Цена добавления зачарования - 10 уровней опыта
            }
        }
    }
}
