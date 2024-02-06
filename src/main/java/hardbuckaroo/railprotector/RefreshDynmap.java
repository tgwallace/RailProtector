package hardbuckaroo.railprotector;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.logging.Level;

public class RefreshDynmap {
    private final RailProtector plugin;
    public RefreshDynmap(RailProtector plugin){
        this.plugin = plugin;
    }

    public void updateDynMap(){
        DynmapAPI dapi = plugin.dapi;
        MarkerSet m = dapi.getMarkerAPI().getMarkerSet("RailProtect.markerset");
        if(m==null) return;
        plugin.getLogger().log(Level.INFO,"Checking Dynmap for rail removals.");
        for(AreaMarker am : m.getAreaMarkers()) {
            World world = Bukkit.getServer().getWorld(am.getMarkerID().split(":")[0]);
            String[] coords = am.getMarkerID().split(":")[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);
            Block block = world.getBlockAt(x,y,z);
            if(!block.getBlockData().getMaterial().toString().contains("RAIL")) {
                am.deleteMarker();
            }
        }
    }
}
