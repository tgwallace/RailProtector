package hardbuckaroo.railprotector;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Rail;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.*;

public class RailBreakWatcher implements Listener {
    private final RailProtector plugin;
    public RailBreakWatcher(RailProtector plugin){
        this.plugin = plugin;
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreakEvent(BlockBreakEvent event){
        if(!plugin.getConfig().getBoolean("protections.break")) return;
        Block originalRail = event.getBlock();

        if(traceRail(originalRail,event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,-1),event.getPlayer())
        ) {
            event.setCancelled(true);
            event.getPlayer().sendRawMessage("That rail is protected by RailProtector.");
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        if(!plugin.getConfig().getBoolean("protections.explode")) return;

        List<Block> blocks = event.blockList();

        blocks.removeIf(originalRail -> traceRail(originalRail)
                || traceRail(originalRail.getRelative(0,1,0))
                || traceRail(originalRail.getRelative(0,-1,0))
                || traceRail(originalRail.getRelative(0,-2,0))
                || traceRail(originalRail.getRelative(1,0,0))
                || traceRail(originalRail.getRelative(-1,0,0))
                || traceRail(originalRail.getRelative(0,0,1))
                || traceRail(originalRail.getRelative(0,0,-1))
                || traceRail(originalRail.getRelative(1,1,0))
                || traceRail(originalRail.getRelative(-1,1,0))
                || traceRail(originalRail.getRelative(0,1,1))
                || traceRail(originalRail.getRelative(0,1,-1))
                || traceRail(originalRail.getRelative(1,-2,0))
                || traceRail(originalRail.getRelative(-1,-2,0))
                || traceRail(originalRail.getRelative(0,-2,1))
                || traceRail(originalRail.getRelative(0,-2,-1))
        );
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBurnEvent(BlockBurnEvent event) {
        if(!plugin.getConfig().getBoolean("protections.burn")) return;

        Block originalRail = event.getBlock();

        if(traceRail(originalRail)
                || traceRail(originalRail.getRelative(0,1,0))
                || traceRail(originalRail.getRelative(0,-1,0))
                || traceRail(originalRail.getRelative(0,-2,0))
                || traceRail(originalRail.getRelative(1,0,0))
                || traceRail(originalRail.getRelative(-1,0,0))
                || traceRail(originalRail.getRelative(0,0,1))
                || traceRail(originalRail.getRelative(0,0,-1))
                || traceRail(originalRail.getRelative(1,1,0))
                || traceRail(originalRail.getRelative(-1,1,0))
                || traceRail(originalRail.getRelative(0,1,1))
                || traceRail(originalRail.getRelative(0,1,-1))
                || traceRail(originalRail.getRelative(1,-2,0))
                || traceRail(originalRail.getRelative(-1,-2,0))
                || traceRail(originalRail.getRelative(0,-2,1))
                || traceRail(originalRail.getRelative(0,-2,-1)))
            event.setCancelled(true);
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        if(!plugin.getConfig().getBoolean("protections.explode")) return;

        List<Block> blocks = event.blockList();

        blocks.removeIf(originalRail -> traceRail(originalRail)
                || traceRail(originalRail.getRelative(0,1,0))
                || traceRail(originalRail.getRelative(0,-1,0))
                || traceRail(originalRail.getRelative(0,-2,0))
                || traceRail(originalRail.getRelative(1,0,0))
                || traceRail(originalRail.getRelative(-1,0,0))
                || traceRail(originalRail.getRelative(0,0,1))
                || traceRail(originalRail.getRelative(0,0,-1))
                || traceRail(originalRail.getRelative(1,1,0))
                || traceRail(originalRail.getRelative(-1,1,0))
                || traceRail(originalRail.getRelative(0,1,1))
                || traceRail(originalRail.getRelative(0,1,-1))
                || traceRail(originalRail.getRelative(1,-2,0))
                || traceRail(originalRail.getRelative(-1,-2,0))
                || traceRail(originalRail.getRelative(0,-2,1))
                || traceRail(originalRail.getRelative(0,-2,-1))
        );
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        if(!plugin.getConfig().getBoolean("protections.bucketempty")) return;

        Block originalRail = event.getBlock();

        if(traceRail(originalRail,event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,-1),event.getPlayer())
        ) {
            event.setCancelled(true);
            event.getPlayer().sendRawMessage("That rail is protected by RailProtector.");
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockFromToEvent(BlockFromToEvent event) {
        if(!plugin.getConfig().getBoolean("protections.waterflow")) return;

        Block originalRail = event.getToBlock().getRelative(0,-1,0);

        if(traceRail(originalRail)
                || traceRail(originalRail.getRelative(0,1,0))
                || traceRail(originalRail.getRelative(0,-1,0))
                || traceRail(originalRail.getRelative(0,-2,0))
                || traceRail(originalRail.getRelative(1,0,0))
                || traceRail(originalRail.getRelative(-1,0,0))
                || traceRail(originalRail.getRelative(0,0,1))
                || traceRail(originalRail.getRelative(0,0,-1))
                || traceRail(originalRail.getRelative(1,1,0))
                || traceRail(originalRail.getRelative(-1,1,0))
                || traceRail(originalRail.getRelative(0,1,1))
                || traceRail(originalRail.getRelative(0,1,-1))
                || traceRail(originalRail.getRelative(1,-2,0))
                || traceRail(originalRail.getRelative(-1,-2,0))
                || traceRail(originalRail.getRelative(0,-2,1))
                || traceRail(originalRail.getRelative(0,-2,-1))
        ) {
            event.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if(!plugin.getConfig().getBoolean("protections.place")) return;

        Block originalRail = event.getBlock();

        if(originalRail.getBlockData().getMaterial().isSolid() && traceRail(originalRail.getRelative(0,-1,0),event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendRawMessage("That rail is protected by RailProtector.");
        }
        else if(traceRail(originalRail,event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,0,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,0,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,1,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,1,-1),event.getPlayer())
                || traceRail(originalRail.getRelative(1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(-1,-2,0),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,1),event.getPlayer())
                || traceRail(originalRail.getRelative(0,-2,-1),event.getPlayer())){
            event.setCancelled(true);
            event.getPlayer().sendRawMessage("That rail is protected by RailProtector.");
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPistonExtendEvent(BlockPistonExtendEvent event) {
        if(!plugin.getConfig().getBoolean("protections.piston")) return;

        List<Block> blocks = event.getBlocks();

        for(Block originalRail : blocks){
            if(traceRail(originalRail)
                    || traceRail(originalRail.getRelative(0,1,0))
                    || traceRail(originalRail.getRelative(0,-1,0))
                    || traceRail(originalRail.getRelative(0,-2,0))
                    || traceRail(originalRail.getRelative(1,0,0))
                    || traceRail(originalRail.getRelative(-1,0,0))
                    || traceRail(originalRail.getRelative(0,0,1))
                    || traceRail(originalRail.getRelative(0,0,-1))
                    || traceRail(originalRail.getRelative(1,1,0))
                    || traceRail(originalRail.getRelative(-1,1,0))
                    || traceRail(originalRail.getRelative(0,1,1))
                    || traceRail(originalRail.getRelative(0,1,-1))
                    || traceRail(originalRail.getRelative(1,-2,0))
                    || traceRail(originalRail.getRelative(-1,-2,0))
                    || traceRail(originalRail.getRelative(0,-2,1))
                    || traceRail(originalRail.getRelative(0,-2,-1)))
                event.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPistonRetractEvent(BlockPistonRetractEvent event) {
        if(!plugin.getConfig().getBoolean("protections.piston")) return;

        List<Block> blocks = event.getBlocks();

        for(Block originalRail : blocks){
            if(traceRail(originalRail)
                    || traceRail(originalRail.getRelative(0,1,0))
                    || traceRail(originalRail.getRelative(0,-1,0))
                    || traceRail(originalRail.getRelative(0,-2,0))
                    || traceRail(originalRail.getRelative(1,0,0))
                    || traceRail(originalRail.getRelative(-1,0,0))
                    || traceRail(originalRail.getRelative(0,0,1))
                    || traceRail(originalRail.getRelative(0,0,-1))
                    || traceRail(originalRail.getRelative(1,1,0))
                    || traceRail(originalRail.getRelative(-1,1,0))
                    || traceRail(originalRail.getRelative(0,1,1))
                    || traceRail(originalRail.getRelative(0,1,-1))
                    || traceRail(originalRail.getRelative(1,-2,0))
                    || traceRail(originalRail.getRelative(-1,-2,0))
                    || traceRail(originalRail.getRelative(0,-2,1))
                    || traceRail(originalRail.getRelative(0,-2,-1)))
                event.setCancelled(true);
        }
    }

    public boolean traceRail(Block originalRail) {
        if(!originalRail.getBlockData().getMaterial().toString().contains("RAIL")) return false;

        List<Player> playerList = originalRail.getWorld().getPlayers();
        if(playerList.isEmpty()) return true;
        CraftPlayer player = (CraftPlayer) playerList.get(0);
        ServerPlayer sp = player.getHandle();
        MinecraftServer server = sp.getServer();
        ServerLevel level = sp.serverLevel();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "FakePlayer");
        ServerPlayer fakeSP = new ServerPlayer(server, level, gameProfile, ClientInformation.createDefault());
        Player fakePlayer = fakeSP.getBukkitEntity().getPlayer();

        return traceRail(originalRail,fakePlayer);
    }

    public boolean traceRail(Block originalRail, Player player) {
        if (!originalRail.getBlockData().getMaterial().toString().contains("RAIL")) return false;

        DynmapAPI dynmap = plugin.dapi;
        Set<MarkerSet> allSets = dynmap.getMarkerAPI().getMarkerSets();
        for(MarkerSet delete : allSets) {
            delete.deleteMarkerSet();
        }

        boolean isProt1 = false, isProt2 = false, isWild1 = true, isWild2 = true;
        int maxDistance = Bukkit.spigot().getConfig().getInt("maxdistance"), currentDistance = 0;

        for (int x = 1; x <= 2; x++) {
            Block backRail = originalRail;
            Block frontRail = originalRail;
            Rail.Shape shape = ((Rail) frontRail.getState().getBlockData()).getShape();
            if (x == 1) {
                switch (shape.toString()) {
                    case "ASCENDING_NORTH":
                        frontRail = frontRail.getRelative(0, 1, -1);
                        break;
                    case "ASCENDING_SOUTH":
                        frontRail = frontRail.getRelative(0, 1, 1);
                        break;
                    case "ASCENDING_EAST":
                        frontRail = frontRail.getRelative(1, 1, 0);
                        break;
                    case "ASCENDING_WEST":
                        frontRail = frontRail.getRelative(-1, 1, 0);
                        break;
                    case "NORTH_SOUTH":
                    case "NORTH_WEST":
                    case "NORTH_EAST":
                        frontRail = frontRail.getRelative(0, -1, -1);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0, 1, 0);
                        }
                        break;
                    case "SOUTH_EAST":
                    case "SOUTH_WEST":
                        frontRail = frontRail.getRelative(0, -1, 1);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0, 1, 0);
                        }
                        break;
                    case "EAST_WEST":
                        frontRail = frontRail.getRelative(1,-1,0);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0,1,0);
                        }
                        break;
                }
            } else {
                switch (shape.toString()) {
                    case "ASCENDING_NORTH":
                        frontRail = frontRail.getRelative(0, 0, 1);
                        break;
                    case "ASCENDING_SOUTH":
                        frontRail = frontRail.getRelative(0, 0, -1);
                        break;
                    case "ASCENDING_EAST":
                        frontRail = frontRail.getRelative(-1, 0, 0);
                        break;
                    case "ASCENDING_WEST":
                        frontRail = frontRail.getRelative(1, 0, 0);
                        break;
                    case "NORTH_SOUTH":
                        frontRail = frontRail.getRelative(0, -1, 1);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0, 1, 0);
                        }
                        break;
                    case "NORTH_EAST":
                    case "SOUTH_EAST":
                        frontRail = frontRail.getRelative(1,-1,0);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0,1,0);
                        }
                        break;
                    case "NORTH_WEST":
                    case "EAST_WEST":
                    case "SOUTH_WEST":
                        frontRail = frontRail.getRelative(-1,-1,0);
                        if(!frontRail.getType().toString().contains("RAIL")) {
                            frontRail = frontRail.getRelative(0,1,0);
                        }
                        break;
                }
            }
            while (frontRail.getBlockData().getMaterial().toString().contains("RAIL") && !frontRail.equals(originalRail) && (maxDistance == 0 || currentDistance < maxDistance)) {
                currentDistance++;
                shape = ((Rail) frontRail.getState().getBlockData()).getShape();
                if (shape.toString().equals("ASCENDING_EAST")) {
                    if (!frontRail.getRelative(1, 1, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1, 1, 0);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail) && frontRail.getRelative(-1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    } else if (!frontRail.getRelative(-1, -1, 0).equals(backRail) && frontRail.getRelative(-1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, -1, 0);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    }
                } else if (shape.toString().equals("ASCENDING_WEST")) {
                    if (!frontRail.getRelative(-1, 1, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 1, 0);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail) && frontRail.getRelative(1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1, 0, 0);
                    }  else if (!frontRail.getRelative(1, -1, 0).equals(backRail) && frontRail.getRelative(1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1, -1, 0);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1, 0, 0);
                    }
                } else if (shape.toString().equals("ASCENDING_NORTH")) {
                    if (!frontRail.getRelative(0, 1, -1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 1, -1);
                    } else if (!frontRail.getRelative(0, 0, 1).equals(backRail) && frontRail.getRelative(0, 0, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    }  else if (!frontRail.getRelative(0, -1, 1).equals(backRail) && frontRail.getRelative(0, -1, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, 1);
                    } else if (!frontRail.getRelative(0, 0, 1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    }
                } else if (shape.toString().equals("ASCENDING_SOUTH")) {
                    if (!frontRail.getRelative(0, 1, 1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 1, 1);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail) && frontRail.getRelative(0, 0, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    }  else if (!frontRail.getRelative(0, -1, -1).equals(backRail) && frontRail.getRelative(0, -1, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, -1);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    }
                } else if (shape.toString().equals("EAST_WEST")) {
                    if (!frontRail.getRelative(1, 0, 0).equals(backRail) && frontRail.getRelative(1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail) && frontRail.getRelative(-1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    } else if (!frontRail.getRelative(1, -1, 0).equals(backRail) && frontRail.getRelative(1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,-1,0);
                    } else if (!frontRail.getRelative(-1, -1, 0).equals(backRail) && frontRail.getRelative(-1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, -1, 0);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    }
                } else if (shape.toString().equals("NORTH_SOUTH")) {
                    if (!frontRail.getRelative(0, 0, 1).equals(backRail) && frontRail.getRelative(0, 0, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail) && frontRail.getRelative(0, 0, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    } else if (!frontRail.getRelative(0, -1, 1).equals(backRail) && frontRail.getRelative(0, -1, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, 1);
                    } else if (!frontRail.getRelative(0, -1, -1).equals(backRail) && frontRail.getRelative(0, -1, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, -1);
                    } else if (!frontRail.getRelative(0, 0, 1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    }
                } else if (shape.toString().equals("NORTH_EAST")) {
                    if (!frontRail.getRelative(0, 0, -1).equals(backRail) && frontRail.getRelative(0, 0, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail) && frontRail.getRelative(1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    } else if (!frontRail.getRelative(0, -1, -1).equals(backRail) && frontRail.getRelative(0, -1, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, -1);
                    } else if (!frontRail.getRelative(1, -1, 0).equals(backRail) && frontRail.getRelative(1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,-1,0);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    }
                } else if (shape.toString().equals("NORTH_WEST")) {
                    if (!frontRail.getRelative(0, 0, -1).equals(backRail) && frontRail.getRelative(0, 0, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail) && frontRail.getRelative(-1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    } else if (!frontRail.getRelative(0, -1, -1).equals(backRail) && frontRail.getRelative(0, -1, -1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, -1);
                    } else if (!frontRail.getRelative(-1, -1, 0).equals(backRail) && frontRail.getRelative(-1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, -1, 0);
                    } else if (!frontRail.getRelative(0, 0, -1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, -1);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    }
                } else if (shape.toString().equals("SOUTH_EAST")) {
                    if (!frontRail.getRelative(0, 0, 1).equals(backRail) && frontRail.getRelative(0, 0, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail) && frontRail.getRelative(1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    } else if (!frontRail.getRelative(0, -1, 1).equals(backRail) && frontRail.getRelative(0, -1, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, 1);
                    } else if (!frontRail.getRelative(1, -1, 0).equals(backRail) && frontRail.getRelative(1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,-1,0);
                    } else if (!frontRail.getRelative(0, 0, 1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(1,0,0);
                    }
                } else if (shape.toString().equals("SOUTH_WEST")) {
                    if (!frontRail.getRelative(0, 0, 1).equals(backRail) && frontRail.getRelative(0, 0, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail) && frontRail.getRelative(-1, 0, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    } else if (!frontRail.getRelative(0, -1, 1).equals(backRail) && frontRail.getRelative(0, -1, 1).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, -1, 1);
                    } else if (!frontRail.getRelative(-1, -1, 0).equals(backRail) && frontRail.getRelative(-1, -1, 0).getType().toString().contains("RAIL")) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, -1, 0);
                    } else if (!frontRail.getRelative(0, 0, 1).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(0, 0, 1);
                    } else if (!frontRail.getRelative(-1, 0, 0).equals(backRail)) {
                        backRail = frontRail;
                        frontRail = frontRail.getRelative(-1, 0, 0);
                    }
                }
                if(Bukkit.getPluginManager().getPlugin("dynmap") != null && Bukkit.getServer().getPluginManager().getPlugin("dynmap").isEnabled() && plugin.getConfig().getBoolean("Dynmap")) {
                    MarkerSet m;
                    if(dynmap.getMarkerAPI().getMarkerSet("RailProtect.markerset") == null) {
                        m = dynmap.getMarkerAPI().createMarkerSet("RailProtect.markerset", "Rail Lines", dynmap.getMarkerAPI().getMarkerIcons(), false);
                    } else {
                        m = dynmap.getMarkerAPI().getMarkerSet("RailProtect.markerset");
                    }
                    AreaMarker am = m.createAreaMarker(frontRail.getLocation().toString(), "Rail Line", true, frontRail.getWorld().getName(), new double[]{frontRail.getX() - 1, frontRail.getX() + 1}, new double[]{frontRail.getZ() - 1, frontRail.getZ() + 1}, false);
                    am.setFillStyle(1, 0x000000);
                    am.setLineStyle(1, 1, 0x000000);
                }
            }
            if(frontRail.equals(originalRail)) return false;

            List<Player> playerList = originalRail.getWorld().getPlayers();
            if(playerList.isEmpty()) return true;
            CraftPlayer p = (CraftPlayer) playerList.get(0);
            ServerPlayer sp = p.getHandle();
            MinecraftServer server = sp.getServer();
            ServerLevel level = sp.serverLevel();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "FakePlayer");
            ServerPlayer fakeSP = new ServerPlayer(server, level, gameProfile, ClientInformation.createDefault());
            Player fakePlayer = fakeSP.getBukkitEntity().getPlayer();

            BlockBreakEvent playerBreakEvent = new BlockBreakEvent(frontRail, player);
            Bukkit.getPluginManager().callEvent(playerBreakEvent);
            if (playerBreakEvent.isCancelled()) {
                if(x==1) isProt1 = true;
                if(x==2) isProt2 = true;
            } else playerBreakEvent.setCancelled(true);

            BlockBreakEvent anonBreakEvent = new BlockBreakEvent(frontRail, fakePlayer);
            Bukkit.getPluginManager().callEvent(anonBreakEvent);
            if (anonBreakEvent.isCancelled()) {
                if(x==1) isWild1 = false;
                if(x==2) isWild2 = false;
            } else anonBreakEvent.setCancelled(true);
        }

        if(isProt1 && isProt2) return true;
        else if(isProt1 && !isWild1 && !isProt2 && !isWild2) return false;
        else if(isProt2 && !isWild2 && !isProt1 && !isWild1) return false;
        else if (isProt1 && isWild2) return true;
        else if (isProt2 && isWild1) return true;
        else return false;
    }
}
