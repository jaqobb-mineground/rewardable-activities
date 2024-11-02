package dev.jaqobb.rewardable_activities.listener.entity;

import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import java.util.List;

public class EntityExplodeListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public EntityExplodeListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.plugin.isBlockBreakOwnershipCheckEnabled()) {
            return;
        }
        List<Block> blocks = event.blockList();
        for (Block block : blocks) {
            if (!this.plugin.hasMetadata(block, RewardableActivitiesConstants.BLOCK_PLACED_BY_PLAYER_KEY)) {
                continue;
            }
            this.plugin.unsetMetadata(block, RewardableActivitiesConstants.BLOCK_PLACED_BY_PLAYER_KEY);
        }
    }
}
