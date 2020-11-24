/*
 * MIT License
 *
 * Copyright (c) 2020 Jakub Zagórski (jaqobb)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.jaqobb.rewardableactivities.listener.block;

import com.cryptomorin.xseries.XMaterial;
import dev.jaqobb.rewardableactivities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardableactivities.data.RewardableActivity;
import dev.jaqobb.rewardableactivities.data.RewardableActivityReward;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public final class BlockBreakListener implements Listener {

    private final RewardableActivitiesPlugin plugin;

    public BlockBreakListener(final RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        RewardableActivity rewardableActivity = this.plugin.getRepository().getBlockBreakRewardableActivity(XMaterial.matchXMaterial(block.getType()));
        if (rewardableActivity == null) {
            return;
        }
        RewardableActivityReward rewardableActivityReward = rewardableActivity.getReward(player);
        if (rewardableActivityReward == null) {
            return;
        }
        if (!rewardableActivityReward.testChance()) {
            return;
        }
        Economy economy = this.plugin.getEconomy();
        if (economy != null && rewardableActivityReward.getMinimumEconomy() >= 0.0D && rewardableActivityReward.getMaximumEconomy() > 0.0D && rewardableActivityReward.getMaximumEconomy() > rewardableActivityReward.getMinimumEconomy()) {
            rewardableActivityReward.depositEconomy(economy, player, rewardableActivityReward.getRandomEconomy());
        }
        rewardableActivityReward.executeCommands(player);
    }
}