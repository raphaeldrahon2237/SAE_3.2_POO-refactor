package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Archer;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.troop.enemy.Wolf;

public class EarlyWaveGeneration implements EnemyGenerationStrategy {

    @Override
    public Enemy createEnemy(int x, int y, int rand, int wave) {
        if (rand < 80) {
            return new Wolf(x, y);
        }
        return new Archer(x, y);
    }
}
