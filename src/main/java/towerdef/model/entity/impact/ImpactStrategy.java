package towerdef.model.entity.impact;

import towerdef.model.RangeChecker;
import towerdef.model.entity.Projectile;

public interface ImpactStrategy {
    void applyImpact(Projectile projectile, RangeChecker rangeChecker);
}
