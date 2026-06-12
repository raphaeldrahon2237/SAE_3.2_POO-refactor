package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;
import javafx.geometry.Point2D;


public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(1,"Tir rapide monocible pour eliminer les ennemis faibles a distance.",35, 8, 2, 4, 4, x, y,
                AttackType.NORMAL, 0, 120, 1,
                80, 3, 5, 5, 1);
    }

    public void act(Game game){
        if (canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, 1);
            this.resetAttackTimer();
        }
    }


    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
