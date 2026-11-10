package towerdef.model.entity.upgrade;

import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.tower.Tower;

public class impactUpgrade implements Upgrade{
    private ImpactStrategy newImpact;

    @Override
    public void applyUpgradeTo(Tower tower) {
        tower.setImpactStrategy(newImpact);
    }
}
