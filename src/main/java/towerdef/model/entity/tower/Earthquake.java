package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;

import java.util.ArrayList;

public class Earthquake extends Tower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.EARTHQUAKE;

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 60, CHARACTERISTICS.getDamage(),
                3, 1, CHARACTERISTICS.getRange(), x, y, AttackType.EARTHQUAKE, 0, CHARACTERISTICS.getCost(), 1,
                180, 2, 10, 1, 1);
        this.slowSpeed = slowSpeed;
    }


    public void attack(Game game){
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
