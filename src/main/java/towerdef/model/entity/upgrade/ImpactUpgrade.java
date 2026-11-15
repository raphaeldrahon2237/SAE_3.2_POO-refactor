package towerdef.model.entity.upgrade;

import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.tower.Tower;

public class ImpactUpgrade implements Upgrade{
    private ImpactStrategy newImpact;

    public ImpactUpgrade(ImpactStrategy newImpactStrategy){
        this.newImpact= newImpactStrategy;
    }
    @Override
    public void applyUpgradeTo(Tower tower) {
        tower.setImpactStrategy(newImpact);
    }
}
