package com.playivanikyt.codefarmer;

import com.playivanikyt.codefarmer.enchant.CodeSwordFarmer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CodeFarmer extends JavaPlugin {

    private static CodeFarmer instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new CodeFarmerListener(), this);
        Bukkit.getPluginManager().registerEvents(new CodeFarmerAnvil(), this);

        CodeSwordFarmer.register();

        getCommand("swordfarmer").setExecutor(new GiveCommand());
        getCommand("swordfarmer").setTabCompleter(new TabCompleteCommand());

        getLogger().info("CodeFarmer успешно загружен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CodeFarmer выключен.");
    }

    public static CodeFarmer getInstance() {
        return instance;
    }
}
