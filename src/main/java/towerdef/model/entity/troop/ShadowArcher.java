package towerdef.model.entity.troop;

import towerdef.model.Game;

public class ShadowArcher extends Enemy{
    public ShadowArcher(int x, int y) {
        super(30, 15, 1, 3, 5, 3, x, y, 50);
    }

    @Override
    public void attack(Game game) {
        if (getDamage() > 0 && !shootProjectileAtAlliedTroopInRange(game) && !attackTowerInRange(game)) {
            super.attack(game);
        }
    }

    @Override
    public boolean isInCombat(Game game) {
        return game.isTowerInRange(getX(), getY(), getRange()) || super.isInCombat(game);
    }
}
