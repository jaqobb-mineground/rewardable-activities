package dev.jaqobb.rewardable_activities.data;

import java.time.Instant;

public class RewardLimiterData {
    
    private int rewardsReceived;
    private Instant rewardsReceivedResetTime;
    private boolean messageSent;
    
    public RewardLimiterData() {
        this(0, null, false);
    }
    
    public RewardLimiterData(int rewardsReceived, Instant rewardsReceivedResetTime, boolean messageSent) {
        this.rewardsReceived = rewardsReceived;
        this.rewardsReceivedResetTime = rewardsReceivedResetTime;
        this.messageSent = messageSent;
    }
    
    public int getRewardsReceived() {
        return this.rewardsReceived;
    }
    
    public void setRewardsReceived(int rewardsReceived) {
        this.rewardsReceived = rewardsReceived;
    }
    
    public Instant getRewardsReceivedResetTime() {
        return this.rewardsReceivedResetTime;
    }
    
    public void setRewardsReceivedResetTime(Instant rewardsReceivedResetTime) {
        this.rewardsReceivedResetTime = rewardsReceivedResetTime;
    }
    
    public boolean isMessageSent() {
        return this.messageSent;
    }
    
    public void setMessageSent(boolean messageSent) {
        this.messageSent = messageSent;
    }
}
