package towerdef.model.power;

import towerdef.model.Game;
import towerdef.model.entity.troop.enemy.Enemy;

public class DamagePower implements Power {

    @Override
    public void activate(Game game) {
        for (Enemy enemy : game.getEnemies()) {
            if (enemy.isAlive()) {
                enemy.takeMagicDamage(enemy.getHp() / 2);
            }
        }
    }
}
