package towerdef.model.entity.troop;

import towerdef.model.Game;

public class Archer extends Enemy {

    public Archer(int x, int y) {
        super(30, 8, 0, 2, 3, 2, x, y, 30);
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
