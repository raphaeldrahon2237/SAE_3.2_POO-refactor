package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;

import java.util.ArrayList;

public class Earthquake extends Tower {

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(5,"Ralentit les ennemis proches et inflige des degats continus.",60, 4, 3, 1, 4, x, y, AttackType.EARTHQUAKE, 0, 260, 1, 180, 2, 10, 1, 1);
        this.slowSpeed = slowSpeed;
    }


    public void act(Game game){
        ArrayList<Enemy> enemiesInRange;
        this.incrementAttackCounter();
        if (canAttack()){
            enemiesInRange = game.findEnemiesInRange(this.getX(), this.getY(), this.getRange());
            if (!enemiesInRange.isEmpty()){
                for(Enemy enemy: enemiesInRange){
                    enemy.takeDamage(this.getDamage());
                    enemy.setMooveSpeed(slowSpeed);
                }

            }
            this.resetAttackTimer();
        }

    }
}
