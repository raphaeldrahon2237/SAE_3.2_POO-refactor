package towerdef.model.spawn;

import towerdef.model.entity.troop.enemy.Enemy;

public interface EnemyGenerationStrategy {
    Enemy createEnemy(int x, int y, int rand, int wave);
}
