package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Soldier;
import towerdef.model.entity.troop.Troop;
import towerdef.model.entity.troop.Veteran;

public class Barrack extends Tower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.BARRACK;
    public static final int RESPAWN_WAVE_DELAY = 1;
    private static final UpgradeStats UPGRADES = new UpgradeStats(130, 0, 15, 0, 2);

    private int recruitedTroopId;
    private int recruitedTroopDeathWave;

    public Barrack(int x, int y){
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 80, CHARACTERISTICS.getDamage(),
                8, 0, CHARACTERISTICS.getRange(), x, y, AttackType.NORMAL, 0, CHARACTERISTICS.getCost(), 1,
                UPGRADES);
    }


    public String toString() {
        return "Barracks: "+super.toString();
    }

    public void attack(Game game){}

    // Creates the correct troop type based on the barrack's current level.
    public Troop createTroopForCurrentLevel(int x, int y) {
        int currentLevel = levelProperty().get();

        if (currentLevel == 1) {
            return new Recruit(x, y);
        }

        if (currentLevel == 2) {
            return new Soldier(x, y);
        }

        return new Veteran(x, y);
    }

    public boolean hasRecruitedTroop() {
        return recruitedTroopId != -1;
    }

    public int getRecruitedTroopId() {
        return recruitedTroopId;
    }

    public int getRecruitedTroopDeathWave() {
        return recruitedTroopDeathWave;
    }

    public void setRecruitedTroop(Troop troop) {
        recruitedTroopId = troop.getId();
        recruitedTroopDeathWave = -1;
    }

    //stock quand est ce que la recruite est morte
    public void markRecruitedTroopDead(int currentWave) {
        recruitedTroopId = -1;
        recruitedTroopDeathWave = currentWave;
    }
    public boolean canRespawnRecruitedTroop(int currentWave) {
        return isAlive()
                && !hasRecruitedTroop()
                && recruitedTroopDeathWave != -1
                && currentWave - recruitedTroopDeathWave >= RESPAWN_WAVE_DELAY;
    }
}
