package dev.jaqobb.rewardable_activities.listener.block;

import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockPistonRetractListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public BlockPistonRetractListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!event.isSticky()) {
            return;
        }
        if (!this.plugin.isBlockBreakOwnershipCheckEnabled()) {
            return;
        }
        this.plugin.updatePistonBlocks(event.getDirection(), event.getBlocks());
    }
}
