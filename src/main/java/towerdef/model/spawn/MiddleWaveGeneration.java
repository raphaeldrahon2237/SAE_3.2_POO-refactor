package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Archer;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.troop.enemy.ShadowSoldier;
import towerdef.model.entity.troop.enemy.Wolf;

public class MiddleWaveGeneration implements EnemyGenerationStrategy {

    @Override
    public Enemy createEnemy(int x, int y, int rand, int wave) {
        if (rand < 55) {
            return new Wolf(x, y);
        }
        if (rand < 90) {
            return new Archer(x, y);
        }
        return new ShadowSoldier(x, y);
    }
}
