package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Archer;
import towerdef.model.entity.troop.enemy.Catapult;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.troop.enemy.ShadowArcher;
import towerdef.model.entity.troop.enemy.ShadowSoldier;
import towerdef.model.entity.troop.enemy.Wolf;

public class LateWaveGeneration implements EnemyGenerationStrategy {

    @Override
    public Enemy createEnemy(int x, int y, int rand, int wave) {
        if (rand < 30) {
            return new Wolf(x, y);
        }
        if (rand < 55) {
            return new Archer(x, y);
        }
        if (rand < 75) {
            return new ShadowSoldier(x, y);
        }
        if (rand < 90) {
            return new ShadowArcher(x, y);
        }
        return new Catapult(x, y);
    }
}
