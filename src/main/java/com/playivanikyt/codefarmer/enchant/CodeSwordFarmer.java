package com.playivanikyt.codefarmer.enchant;

import com.playivanikyt.codefarmer.CodeFarmer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CodeSwordFarmer extends Enchantment {

    private static final NamespacedKey KEY = new NamespacedKey(CodeFarmer.getInstance(), "farmer");

    public CodeSwordFarmer() {
        super(KEY);
    }

    public static void register() {
        try {
            java.lang.reflect.Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception ignored) {}

        if (Enchantment.getByKey(KEY) == null) {
            Enchantment.registerEnchantment(new CodeSwordFarmer());
        }
    }

    public static ItemStack createFarmerSword(int level) {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            // Используем альтернативный способ для HEX-цветов
            String swordName = ChatColor.translateAlternateColorCodes('&', "&x&0&0&F&F&0&0Меч «Выгодный фарм»");
            meta.setDisplayName(swordName);
            meta.addEnchant(new CodeSwordFarmer(), level, true);
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Фармер " + level));
            sword.setItemMeta(meta);
        }
        return sword;
    }

    public static int getLevel(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;
        return item.getEnchantmentLevel(new CodeSwordFarmer());
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType() == Material.NETHERITE_SWORD;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public String getName() {
        return "Farmer";
    }

    @Override
    public NamespacedKey getKey() {
        return KEY;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }
}
