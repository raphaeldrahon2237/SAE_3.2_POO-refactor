package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;

public class Barrack extends Tower {

    public Barrack(int x, int y){
        super(3,"Tour defensive qui bloque les ennemis avec une unite alliee.",80, 0, 8, 0, 0, x, y, AttackType.NORMAL, 0, 180, 1, 130, 0, 15, 0, 2);
    }

    public String toString() {
        return "Barracks: "+super.toString();
    }

    public void attack(Game game){
        System.out.println("");
    }
}
