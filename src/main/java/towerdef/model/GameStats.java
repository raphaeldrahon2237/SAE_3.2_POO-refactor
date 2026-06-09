package towerdef.model;

public class GameStats {
    private double survivalTime;
    private int enemiesKilled;
    private int enemiesSpawned;
    private int towersPlaced;
    private int projectilesFired;
    private int damageTakenByBase;
    private int maxWaveReached;

    public void addSurvivalTime(double deltaTime) {
        survivalTime += deltaTime;
    }

    public void enemyKilled() {
        enemiesKilled++;
    }

    public void enemySpawned() {
        enemiesSpawned++;
    }

    public void towerPlaced() {
        towersPlaced++;
    }

    public void projectileFired() {
        projectilesFired++;
    }

    public void addDamageTakenByBase(int damage) {
        damageTakenByBase += damage;
    }

    public void updateMaxWaveReached(int wave) {
        maxWaveReached = Math.max(maxWaveReached, wave);
    }

    public double getSurvivalTime() {
        return survivalTime;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public int getTowersPlaced() {
        return towersPlaced;
    }

    public int getProjectilesFired() {
        return projectilesFired;
    }

    public int getDamageTakenByBase() {
        return damageTakenByBase;
    }

    public int getMaxWaveReached() {
        return maxWaveReached;
    }
}
