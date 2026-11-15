package towerdef.model.entity.upgrade;

import towerdef.model.entity.tower.Tower;

import java.util.ArrayList;

public class CompositeUpgrade implements Upgrade{
    private ArrayList<Upgrade> composition;

    public CompositeUpgrade(StatsUpgrade statsupgrade, ImpactUpgrade impactUpgrade){
        this.composition= new ArrayList<>();
        composition.add(statsupgrade);
        composition.add(impactUpgrade);
    }

    @Override
    public void applyUpgradeTo(Tower tower) {
        for(Upgrade u : composition){
            u.applyUpgradeTo(tower);
        }

    }
}
