package towerdef.model.entity.troop.enemy;

import javafx.geometry.Point2D;
import towerdef.model.Game;
import towerdef.model.Utils;
import towerdef.model.entity.Entity;
import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.impact.NormalImpact;
import towerdef.model.entity.tower.Tower;

public class RangedEnemy extends Enemy {

    public RangedEnemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y, reward);
    }

    @Override
    public void attack(Game game) {
        if (!game.isGameOver() && getDamage() > 0 && !attackAlliedTroopInRange(game) && !attackTowerInRange(game)) {
            attackBaseInRange(game);
        }
    }

    protected boolean attackTowerInRange(Game game) {
        Tower targetTower = game.findTowerInRange(getX(), getY(), getRange());
        return attackTarget(targetTower, game);
    }

    @Override
    public boolean isInCombat(Game game) {
        return game.isTowerInRange(getX(), getY(), getRange()) || (game.isAlliedTroopInRange(getX(), getY(), getRange()) || game.isBaseInRange(this));
    }

    @Override
    protected void hitTarget(Entity target, Game game){
        Point2D targetTile = Utils.toTilePosition(target.getX(), target.getY());
        incrementAttackCounter();
        game.createProjectile(this, target, targetTile, new NormalImpact());
    }
}
