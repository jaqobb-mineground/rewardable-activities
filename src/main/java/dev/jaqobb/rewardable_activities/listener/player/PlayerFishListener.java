package dev.jaqobb.rewardable_activities.listener.player;

import com.cryptomorin.xseries.XMaterial;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardable_activities.data.RewardableActivity;
import dev.jaqobb.rewardable_activities.data.RewardableActivityReward;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public PlayerFishListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        Entity caught = event.getCaught();
        if (caught == null) {
            return;
        }
        Item caughtItem = (Item) caught;
        RewardableActivity activity = this.plugin.getRepository().getItemFishActivity(XMaterial.matchXMaterial(caughtItem.getItemStack().getType()));
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
