package towerdef.model.power;

import towerdef.model.Game;
import towerdef.model.entity.tower.Earthquake;
import towerdef.model.entity.troop.enemy.Enemy;

public class SlowPower implements Power {

    @Override
    public void activate(Game game) {
        for (Enemy enemy : game.getEnemies()) {
            if (enemy.isAlive()) {
                enemy.setMoveSpeed(Earthquake.SLOW_SPEED);
            }
        }
    }
}
