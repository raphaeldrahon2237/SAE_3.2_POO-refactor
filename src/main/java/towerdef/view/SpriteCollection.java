package towerdef.view;

import towerdef.model.entity.Entity;
import javafx.scene.image.Image;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Artillery;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.tower.Mage;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Soldier;
import towerdef.model.entity.troop.Veteran;
import towerdef.model.entity.troop.enemy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class SpriteCollection {
    public static final Image BUILDING = SpriteSet.load("building");

    private static final Map<Class<? extends Entity>, List<SpriteSet>> SPRITES = new HashMap<>();

    static{
       SPRITES.put(Recruit.class, List.of(SpriteSet.makeSpriteSetOf("recruitInitial","recruitIdle","recruitAttack")));
       SPRITES.put(Soldier.class, List.of(SpriteSet.makeSpriteSetOf("soldierInitial","soldierIdle","soldierAttack")));
       SPRITES.put(Veteran.class, List.of(SpriteSet.makeSpriteSetOf("veteranInitial","veteranIdle","veteranAttack")));

       SPRITES.put(Wolf.class, List.of(SpriteSet.makeSpriteSetOf("wolfInitial","wolfIdle",null)));
       SPRITES.put(Boss.class, List.of(SpriteSet.makeSpriteSetOf("recruitInitial",null,null)));
       SPRITES.put(Archer.class, List.of(SpriteSet.makeSpriteSetOf("archerInitial","archerIdle","archerAttack")));
       SPRITES.put(Catapult.class, List.of(SpriteSet.makeSpriteSetOf("catapultInitial","catapultIdle","catapultAttack")));
       SPRITES.put(ShadowArcher.class, List.of(SpriteSet.makeSpriteSetOf("shadowArcherInitial","shadowArcherIdle","shadowArcherAttack")));
       SPRITES.put(ShadowSoldier.class, List.of(SpriteSet.makeSpriteSetOf("shadowSoldierInitial","shadowSoldierIdle","shadowSoldierAttack")));

       SPRITES.put(ArcherTower.class, List.of(
               SpriteSet.makeSpriteSetOf("archerTower1Initial",null, "archerTower1Attack"),
               SpriteSet.makeSpriteSetOf("archerTower2Initial",null, "archerTower2Attack"),
               SpriteSet.makeSpriteSetOf("archerTower3Initial",null, "archerTower3Attack")
               )
       );
        SPRITES.put(Mage.class, List.of(
                        SpriteSet.makeSpriteSetOf("mageTower1Initial",null, "mageTower1Attack"),
                        SpriteSet.makeSpriteSetOf("mageTower2Initial",null, "mageTower2Attack"),
                        SpriteSet.makeSpriteSetOf("mageTower3Initial",null, "mageTower3Attack")
                )
        );
        SPRITES.put(Mage.class, List.of(
                        SpriteSet.makeSpriteSetOf("earthquakeTower1Initial",null, "earthquakeTower1Attack"),
                        SpriteSet.makeSpriteSetOf("earthquakeTower2Initial",null, "earthquakeTower2Attack"),
                        SpriteSet.makeSpriteSetOf("earthquakeTower3Initial",null, "earthquakeTower3Attack")
                )
        );
        SPRITES.put(Artillery.class, List.of(
                        SpriteSet.makeSpriteSetOf("artilleryTower1Initial",null, "artilleryTower1Attack")
                )
        );
        SPRITES.put(Barrack.class, List.of(
                        SpriteSet.makeSpriteSetOf("barrack1Initial","barrack1Idle", null)
                )
        );


    }

    public static SpriteSet get(Entity entity, int level){
        List<SpriteSet> images = SPRITES.get(entity.getClass());

        int i= Math.min(Math.max(level, 1), images.size())-1;
        return images.get(i);
    }
}
