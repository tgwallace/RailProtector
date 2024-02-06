package hardbuckaroo.railprotector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public final class RailProtector extends JavaPlugin {
    DynmapAPI dapi;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new RailBreakWatcher(this), this);

        if(Bukkit.getPluginManager().getPlugin("DynMap") != null && Bukkit.getServer().getPluginManager().getPlugin("dynmap").isEnabled() && getConfig().getBoolean("Dynmap")) {
            dapi = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}