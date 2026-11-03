package towerdef.model.entity.impact;

import javafx.geometry.Point2D;
import towerdef.model.RangeChecker;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;

public class AreaImpact implements ImpactStrategy {

    @Override
    public void applyImpact(Projectile projectile, RangeChecker rangeChecker) {
        Point2D impactTile = projectile.getImpactTile();

        for (Enemy enemy : rangeChecker.findEnemiesInRange(
                impactTile.getX(), impactTile.getY(), 2)) {

            if (projectile.getSource() instanceof Tower tower) {
                enemy.setLastDamagingTower(tower);
            }

            enemy.takeDamage(projectile.getDamage());
        }
    }
}
