package towerdef.model.power;

import towerdef.model.Game;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.Tower;

public class HealingPower implements Power {

    @Override
    public void activate(Game game) {
        for (Entity ally : game.getAllies()) {
            if (ally instanceof Tower tower && tower.isAlive()) {
                tower.setHp((int) tower.getMaxHp());
            }
        }
    }
}
