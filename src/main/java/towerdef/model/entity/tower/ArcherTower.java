package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;


public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(1,"Rapid fire tower, ideal for eliminating many weaker enemies",35, 8, 2, 3, 4, x, y,
                AttackType.NORMAL, 0, 120, 1,
                80, 3, 5, 3, 1);
    }

    public void attack(Game game){
        if (canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }


    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
