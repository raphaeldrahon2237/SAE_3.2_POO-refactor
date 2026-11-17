package towerdef.model.entity.target;

import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;

import java.util.ArrayList;

public interface TargetStrategy {
    Enemy chooseTarget(ArrayList<Enemy> enemiesInRange, Tower tower);
}
