package dev.jaqobb.rewardable_activities.listener.block;

import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import java.util.List;

public class BlockExplodeListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public BlockExplodeListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
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
