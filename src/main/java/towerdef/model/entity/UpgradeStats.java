package towerdef.model.entity;

import towerdef.model.entity.tower.Tower;

public class UpgradeStats {

    private int cost;
    private int hp;
    private int damage;
    private int armor;
    private int attackSpeed;


    public UpgradeStats(int cost, int hp, int damage, int armor, int attackSpeed){
        this.cost=cost;
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

    public int getCost(){
        return cost;
    }
}
