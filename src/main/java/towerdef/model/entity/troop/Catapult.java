package towerdef.model.entity.troop;

import towerdef.model.Game;

public class Catapult extends Enemy {

    public Catapult(int x, int y) {
        super(120, 25, 2, 1, 7, 1, x, y, 40);
    }

    @Override
    public void attack(Game game) {
        if (getDamage() > 0 && !attackTowerInRange(game)) {
            super.attack(game);
        }
    }

    @Override
    public boolean isInCombat(Game game) {
        return game.isTowerInRange(getX(), getY(), getRange()) || super.isInCombat(game);
    }
}
