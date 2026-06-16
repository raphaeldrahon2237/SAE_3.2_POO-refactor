package towerdef.model.quest;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;

public class MiniQuest {

    private final String title;
    private final Class<? extends Enemy> enemyType;
    private final int towerTypeId;
    private final String towerName;
    private final int targetAmount;
    private final int rewardGold;
    private final int rewardScore;
    private final IntegerProperty currentAmount;
    private final BooleanProperty completed;
    private boolean rewardClaimed;

    public MiniQuest(String title, Class<? extends Enemy> enemyType, int towerTypeId, String towerName, int targetAmount, int rewardGold,
                     int rewardScore) {
        this.title = title;
        this.enemyType = enemyType;
        this.towerTypeId = towerTypeId;
        this.towerName = towerName;
        this.targetAmount = targetAmount;
        this.rewardGold = rewardGold;
        this.rewardScore = rewardScore;
        this.currentAmount = new SimpleIntegerProperty(0);
        this.completed = new SimpleBooleanProperty(false);
        this.rewardClaimed = false;
    }

    public boolean matches(Enemy enemy, Tower tower) {
        return !completed.get()
                && enemyType.isInstance(enemy)
                && tower != null
                && tower.getTowerTypeId() == towerTypeId;
    }

    public void progress() {
        if (completed.get()) {
            return;
        }

        int nextAmount = Math.min(targetAmount, currentAmount.get() + 1);
        currentAmount.set(nextAmount);

        if (nextAmount >= targetAmount) {
            completed.set(true);
        }
    }

    public String getDisplayText() {
        String status = completed.get() ? "TERMINEE" : currentAmount.get() + "/" + targetAmount;
        return "• " + title + " : " + status
                + " (Bonus +" + rewardGold + " gold, +" + rewardScore + " score)";
    }

    public String getTitle() {
        return title;
    }

    public String getTowerName() {
        return towerName;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public int getRewardGold() {
        return rewardGold;
    }

    public int getRewardScore() {
        return rewardScore;
    }

    public int getCurrentAmount() {
        return currentAmount.get();
    }

    public IntegerProperty currentAmountProperty() {
        return currentAmount;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public boolean isRewardClaimed() {
        return rewardClaimed;
    }

    public void claimReward() {
        rewardClaimed = true;
    }
}