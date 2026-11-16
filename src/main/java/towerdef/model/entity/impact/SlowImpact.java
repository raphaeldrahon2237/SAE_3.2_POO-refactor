package towerdef.model.entity.impact;

import towerdef.model.RangeChecker;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.Earthquake;
import towerdef.model.entity.troop.enemy.Enemy;

public class SlowImpact extends ImpactDecorator {

    public SlowImpact(ImpactStrategy impactStrategy) {
        super(impactStrategy);
    }

    @Override
    public void applyImpact(Projectile projectile, RangeChecker rangeChecker) {
        impactStrategy.applyImpact(projectile, rangeChecker);

        if (projectile.getTarget() instanceof Enemy enemy
                && enemy.isAlive()) {
            enemy.setMooveSpeed(Earthquake.SLOW_SPEED);
        }
    }
}
