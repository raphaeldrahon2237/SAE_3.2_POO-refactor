package towerdef.model.entity.troop.enemy;

import towerdef.model.Game;
import towerdef.model.entity.tower.Tower;

public class RangedEnemy extends Enemy {

    public RangedEnemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y, reward);
    }

    @Override
    public void attack(Game game) {
        if (getDamage() > 0 && !shootProjectileAtAlliedTroopInRange(game) && !attackTowerInRange(game)) {
            if (!game.isGameOver() && getDamage() > 0 && !attackAlliedTroopInRange(game)) {
                attackBaseInRange(game);
            }
        }
    }

    protected boolean attackTowerInRange(Game game) {
        boolean hasTarget = false;
        Tower targetTower = game.findTowerInRange(getX(), getY(), getRange());

        if (targetTower != null) {
            hasTarget = true;

            if (canAttack()) {
                shootProjectileAtTarget(targetTower, game, getAttackType());
                resetAttackTimer();
            }
        }

        return hasTarget;
    }

    @Override
    public boolean isInCombat(Game game) {
        return game.isTowerInRange(getX(), getY(), getRange()) || (game.isAlliedTroopInRange(getX(), getY(), getRange()) || game.isBaseInRange(this));
    }
}
