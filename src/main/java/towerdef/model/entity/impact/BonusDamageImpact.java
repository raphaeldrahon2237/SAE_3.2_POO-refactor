package towerdef.model.entity.impact;

import towerdef.model.RangeChecker;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;

public class BonusDamageImpact extends ImpactDecorator {

    private int bonusDamage;

    public BonusDamageImpact(ImpactStrategy impactStrategy, int bonusDamage) {
        super(impactStrategy);
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void applyImpact(Projectile projectile, RangeChecker rangeChecker) {
        impactStrategy.applyImpact(projectile, rangeChecker);

        if (projectile.getTarget() instanceof Enemy enemy
                && enemy.isAlive()) {
            if (projectile.getSource() instanceof Tower tower) {
                enemy.setLastDamagingTower(tower);
            }

            enemy.takeMagicDamage(bonusDamage);
        }
    }
}
