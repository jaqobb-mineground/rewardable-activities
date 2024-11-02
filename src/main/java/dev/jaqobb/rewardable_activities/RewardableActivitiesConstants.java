package dev.jaqobb.rewardable_activities;

import net.md_5.bungee.api.ChatColor;

public final class RewardableActivitiesConstants {
    
    public static final String PREFIX = ChatColor.GRAY + "Rewardable Activities" + ChatColor.GOLD + ChatColor.BOLD + " > ";
    
    public static final String BLOCK_BROKEN_BY_PLAYER_KEY = "rewardableactivities:broken_by_player";
    public static final String BLOCK_PLACED_BY_PLAYER_KEY = "rewardableactivities:placed_by_player";
    
    public static final String ENTITY_BRED_BY_PLAYER_KEY = "rewardableactivities:bred_by_player";
    public static final String ENTITY_SPAWNED_BY_SPAWNER_KEY = "rewardableactivities:spawned_by_spawner";
    
    private RewardableActivitiesConstants() {
        throw new UnsupportedOperationException("Cannot create instance of this class");
    }
}
