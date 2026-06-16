package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.quest.MiniQuest;

public class Progression {

    private String playerName;
    private IntegerProperty score;
    private double scoreValue;
    private IntegerProperty gold;
    private int scoreRate;
    private GameStats gameStats;

    public Progression(String playerName) {
        this.playerName = playerName;
        scoreValue = 0;
        score = new SimpleIntegerProperty((int) scoreValue);
        gold = new SimpleIntegerProperty(1000);
        scoreRate = 1;
        gameStats = new GameStats();
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void addScore(int amount) {
        scoreValue += amount;
        score.set((int) scoreValue);
    }

    public void updateScore(double deltaTime) {
        scoreValue += deltaTime * scoreRate;
        score.set((int) scoreValue);
    }

    public void increaseScoreRate(int amount) {
        scoreRate += amount;
    }

    public void resetScore() {
        scoreValue = 0;
        score.set(0);
        ScoreManager.saveScore(playerName, score.get());
    }

    public int getGold() {
        return gold.get();
    }

    public IntegerProperty goldProperty() {
        return gold;
    }

    public void addGold(int amount) {
        gold.set(gold.get() + amount);
    }

    public void spendGold(int amount) {
        gold.set(gold.get() - amount);
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void addSurvivalTime(double deltaTime) {
        gameStats.addSurvivalTime(deltaTime);
    }

    public void enemySpawned() {
        gameStats.enemySpawned();
    }

    public void towerPlaced() {
        gameStats.towerPlaced();
    }

    public void projectileFired() {
        gameStats.projectileFired();
    }

    public void addDamageTakenByBase(int damage) {
        gameStats.addDamageTakenByBase(damage);
    }

    public void updateMaxWaveReached(int wave) {
        gameStats.updateMaxWaveReached(wave);
    }

    public void rewardEnemyKill(Enemy enemy) {
        addGold(enemy.getReward());
        addScore(10);
        gameStats.enemyKilled();
    }

    public void rewardMiniQuest(MiniQuest miniQuest) {
        addGold(miniQuest.getRewardGold());
        addScore(miniQuest.getRewardScore());
    }
}
