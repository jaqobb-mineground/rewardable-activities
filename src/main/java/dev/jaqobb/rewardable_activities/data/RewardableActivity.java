package dev.jaqobb.rewardable_activities.data;

import org.bukkit.entity.Player;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public record RewardableActivity(String id, Map<String, List<RewardableActivityReward>> rewards) {
    
    public RewardableActivity {
        rewards = Collections.unmodifiableMap(rewards);
    }
    
    public List<RewardableActivityReward> getRewards(Player player) {
        String groupToUse = "default";
        for (String group : this.rewards.keySet()) {
            if (!player.hasPermission("rewardableactivities.group." + group)) {
                continue;
            }
            groupToUse = group;
        }
        return this.rewards.get(groupToUse);
    }
    
    public RewardableActivityReward getReward(Player player) {
        return this.getRewards(player).stream()
            .filter(RewardableActivityReward::testChance)
            .findFirst()
            .orElse(null);
    }
}
