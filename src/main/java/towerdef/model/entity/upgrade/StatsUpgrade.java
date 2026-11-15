package towerdef.model.entity.upgrade;

import towerdef.model.entity.tower.Tower;

public class StatsUpgrade implements Upgrade{

    private int hp;
    private int damage;
    private int armor;
    private int attackSpeed;


    public StatsUpgrade( int hp, int damage, int armor, int attackSpeed){
        this.hp=hp;
        this.damage=damage;
        this.armor=armor;
        this.attackSpeed=attackSpeed;
    }

    public void applyUpgradeTo(Tower tower){
        tower.setHp(tower.getHp() + hp);
        tower.setDamage(tower.getDamage() + damage);
        tower.setArmor(tower.getArmor() + armor);
        tower.setAttackSpeed(tower.getAttackSpeed() + attackSpeed);
    }

}
