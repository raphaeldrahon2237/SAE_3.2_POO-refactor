package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Enemy;

public class WaveGenerationStrategy implements EnemyGenerationStrategy {

    @Override
    public Enemy createEnemy(int x, int y, int rand, int wave) {
        EnemyGenerationStrategy strategy;

        if (wave < 3) {
            strategy = new EarlyWaveGeneration();
        } else if (wave < 5) {
            strategy = new MiddleWaveGeneration();
        } else if (wave < 8) {
            strategy = new AdvancedWaveGeneration();
        } else {
            strategy = new LateWaveGeneration();
        }

        return strategy.createEnemy(x, y, rand, wave);
    }
}
