package towerdef.model.entity.troop.enemy;

import towerdef.model.Game;

public class MeleeEnemy extends Enemy {

    public MeleeEnemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y, reward);
    }

    @Override
    public void attack(Game game){
        if (!game.isGameOver() && getDamage() > 0 && !attackAlliedTroopInRange(game)) {
            attackBaseInRange(game);
        }
    }

    @Override
    public boolean isInCombat(Game game) {
        return game.isAlliedTroopInRange(getX(), getY(), getRange()) || game.isBaseInRange(this);
    }


}
