package towerdef.model.entity.impact;

import towerdef.model.RangeChecker;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.troop.enemy.Enemy;

public class NormalImpact implements ImpactStrategy {

    @Override
    public void applyImpact(Projectile projectile, RangeChecker rangeChecker) {
        if (projectile.getTarget() instanceof Enemy enemy){
               projectile.registerDamagingTower(enemy);
        }
        projectile.getTarget().takeDamage(projectile.getDamage());
    }
}
