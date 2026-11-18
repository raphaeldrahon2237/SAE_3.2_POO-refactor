package towerdef.view;

import towerdef.model.entity.Entity;

import towerdef.model.entity.tower.*;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Soldier;
import towerdef.model.entity.troop.Veteran;
import towerdef.model.entity.troop.enemy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class SpriteCollection {


    private static final Map<Class<? extends Entity>, List<SpriteSet>> SPRITES = new HashMap<>();

    static{
       SPRITES.put(Recruit.class, List.of(SpriteSet.makeSpriteSetOf("recruitInitial","recruitIdle","recruitAttack")));
       SPRITES.put(Soldier.class, List.of(SpriteSet.makeSpriteSetOf("soldierInitial","soldierIdle","soldierAttack")));
       SPRITES.put(Veteran.class, List.of(SpriteSet.makeSpriteSetOf("veteranInitial","veteranIdle","veteranAttack")));

       SPRITES.put(Wolf.class, List.of(SpriteSet.makeSpriteSetOf("wolfInitial","wolfIdle","wolfInitial")));
       SPRITES.put(Boss.class, List.of(SpriteSet.makeSpriteSetOf("recruitInitial","recruitInitial","recruitInitial")));
       SPRITES.put(Archer.class, List.of(SpriteSet.makeSpriteSetOf("archerInitial","archerIdle","archerAttack")));
       SPRITES.put(Catapult.class, List.of(SpriteSet.makeSpriteSetOf("catapultInitial","catapultIdle","catapultAttack")));
       SPRITES.put(ShadowArcher.class, List.of(SpriteSet.makeSpriteSetOf("shadowArcherInitial","shadowArcherIdle","shadowArcherAttack")));
       SPRITES.put(ShadowSoldier.class, List.of(SpriteSet.makeSpriteSetOf("shadowSoldierInitial","shadowSoldierIdle","shadowSoldierAttack")));

       SPRITES.put(ArcherTower.class, List.of(
               SpriteSet.makeSpriteSetOf("archerTower1Initial","archerTower1Initial", "archerTower1Attack"),
               SpriteSet.makeSpriteSetOf("archerTower2Initial","archerTower2Initial", "archerTower2Attack"),
               SpriteSet.makeSpriteSetOf("archerTower3Initial","archerTower3Initial", "archerTower3Attack")
               )
       );
        SPRITES.put(Mage.class, List.of(
                        SpriteSet.makeSpriteSetOf("mageTower1Initial","mageTower1Initial", "mageTower1Attack"),
                        SpriteSet.makeSpriteSetOf("mageTower2Initial","mageTower2Initial", "mageTower2Attack"),
                        SpriteSet.makeSpriteSetOf("mageTower3Initial","mageTower3Initial", "mageTower3Attack")
                )
        );
        SPRITES.put(Earthquake.class, List.of(
                        SpriteSet.makeSpriteSetOf("earthquakeTower1Initial","earthquakeTower1Initial", "earthquakeTower1Attack"),
                        SpriteSet.makeSpriteSetOf("earthquakeTower2Initial","earthquakeTower2Initial", "earthquakeTower2Attack"),
                        SpriteSet.makeSpriteSetOf("earthquakeTower3Initial","earthquakeTower3Initial", "earthquakeTower3Attack")
                )
        );
        SPRITES.put(Artillery.class, List.of(
                        SpriteSet.makeSpriteSetOf("artilleryTower1Initial","artilleryTower1Initial", "artilleryTower1Attack")
                )
        );
        SPRITES.put(Barrack.class, List.of(
                        SpriteSet.makeSpriteSetOf("barrack1Initial","barrack1Idle", "barrack1Initial")
                )
        );


    }

    public static SpriteSet get(Entity entity, int level){
        List<SpriteSet> images = SPRITES.get(entity.getClass());

        int i= Math.min(Math.max(level, 1), images.size())-1;
        return images.get(i);
    }
}
