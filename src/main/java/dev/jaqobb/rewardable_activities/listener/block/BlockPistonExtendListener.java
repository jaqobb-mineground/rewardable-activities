package dev.jaqobb.rewardable_activities.listener.block;

import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class BlockPistonExtendListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public BlockPistonExtendListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!this.plugin.isBlockBreakOwnershipCheckEnabled()) {
            return;
        }
        this.plugin.updatePistonBlocks(event.getDirection(), event.getBlocks());
    }
}
