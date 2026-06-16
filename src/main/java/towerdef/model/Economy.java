package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Economy {

    private static final int GOLD_PER_KILL = 12;
    private static final int SCORE_PER_KILL = 15;

    private String playerName;
    private IntegerProperty score;
    private double scoreValue;
    private IntegerProperty gold;
    private int scoreRate;

    public Economy(String playerName) {
        this.playerName = playerName;
        scoreValue = 0;
        score = new SimpleIntegerProperty((int) scoreValue);
        gold = new SimpleIntegerProperty(1000);
        scoreRate = 1;
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
}
