package towerdef.model.entity.upgrade;

import towerdef.model.entity.tower.Tower;

public interface Upgrade {

    void applyUpgradeTo(Tower tower);
}
