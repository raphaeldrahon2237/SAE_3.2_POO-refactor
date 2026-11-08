package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Archer;
import towerdef.model.entity.troop.enemy.Catapult;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.troop.enemy.ShadowArcher;
import towerdef.model.entity.troop.enemy.ShadowSoldier;
import towerdef.model.entity.troop.enemy.Wolf;

public class AdvancedWaveGeneration implements EnemyGenerationStrategy {

    @Override
    public Enemy createEnemy(int x, int y, int rand, int wave) {
        if (rand < 35) {
            return new Wolf(x, y);
        }
        if (rand < 65) {
            return new Archer(x, y);
        }
        if (rand < 85) {
            return new ShadowSoldier(x, y);
        }
        if (rand < 95) {
            return new ShadowArcher(x, y);
        }
        return new Catapult(x, y);
    }
}
