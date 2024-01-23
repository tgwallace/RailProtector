package hardbuckaroo.railprotector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RailProtector extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new RailBreakWatcher(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}