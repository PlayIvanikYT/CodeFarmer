package com.playivanikyt.codefarmer;

import com.playivanikyt.codefarmer.enchant.CodeSwordFarmer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CodeFarmerListener implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        ItemStack item = event.getEntity().getKiller().getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;

        int level = CodeSwordFarmer.getLevel(item);
        if (level > 0) {
            double multiplier = 1.0 + (level * 0.5);
            event.setDroppedExp((int) (event.getDroppedExp() * multiplier));
        }
    }
}
