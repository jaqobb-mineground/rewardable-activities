package dev.jaqobb.rewardable_activities.listener.block;

import com.cryptomorin.xseries.XMaterial;
import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardable_activities.data.RewardableActivity;
import dev.jaqobb.rewardable_activities.data.RewardableActivityReward;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public BlockPlaceListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (this.plugin.isBlockBreakOwnershipCheckEnabled()) {
            this.plugin.setMetadata(block, RewardableActivitiesConstants.BLOCK_PLACED_BY_PLAYER_KEY, true);
        }
        if (this.plugin.isBlockPlaceOwnershipCheckEnabled() && this.plugin.hasMetadata(block, RewardableActivitiesConstants.BLOCK_BROKEN_BY_PLAYER_KEY)) {
            this.plugin.unsetMetadata(block, RewardableActivitiesConstants.BLOCK_BROKEN_BY_PLAYER_KEY);
            return;
        }
        RewardableActivity activity = this.plugin.getRepository().getBlockPlaceActivity(XMaterial.matchXMaterial(block.getType()));
        if (activity == null) {
            return;
        }
        Player player = event.getPlayer();
        RewardableActivityReward reward = activity.getReward(player);
        if (reward == null) {
            return;
        }
        reward.reward(this.plugin, player);
    }
}
