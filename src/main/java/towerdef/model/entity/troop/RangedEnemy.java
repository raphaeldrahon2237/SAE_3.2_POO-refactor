package towerdef.model.entity.troop;

import towerdef.model.Game;

public class RangedEnemy extends Enemy{

    public RangedEnemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y, reward);
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
