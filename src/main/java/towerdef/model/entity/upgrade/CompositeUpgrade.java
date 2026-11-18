package towerdef.model.entity.upgrade;

import towerdef.model.entity.tower.Tower;

import java.util.ArrayList;

public class CompositeUpgrade implements Upgrade{
    private ArrayList<Upgrade> composition;

    public CompositeUpgrade(Upgrade upgrade1, Upgrade upgrade2){
        this.composition= new ArrayList<>();
        composition.add(upgrade1);
        composition.add(upgrade2);
    }

    @Override
    public void applyUpgradeTo(Tower tower) {
        for(Upgrade u : composition){
            u.applyUpgradeTo(tower);
        }

    }
}
