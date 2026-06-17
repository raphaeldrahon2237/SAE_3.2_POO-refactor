package towerdef.model;

import javafx.collections.ObservableList;
import towerdef.model.entity.Base;
import towerdef.model.entity.Entity;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.quest.MiniQuest;

import java.util.ArrayList;

public class Combat {

    private Game game;
    //private GameEntities gameEntities;
    private Base base;
    private Progression progression;
    private MiniQuestManager miniQuestManager;

    public Combat(Game game, GameEntities gameEntities, Base base, Progression progression, MiniQuestManager miniQuestManager) {
        this.game = game;
        //this.gameEntities = gameEntities;
        this.base = base;
        this.progression = progression;
        this.miniQuestManager = miniQuestManager;
    }

    public void update(ObservableList<Enemy> enemies, ObservableList<Entity> allies, double deltaTime) {
        //updateAlliesAttackTimers(allies,deltaTime);
        //updateEnemiesAttackTimers(enemies,deltaTime);

        alliesAttackEnemies(allies, deltaTime);
        enemiesAttack(enemies, deltaTime);

        if (!game.isGameOver()) {
            updateMovements(enemies,deltaTime);
        }
    }

    public void damageBase(int damage) {
        if (!game.isGameOver()) {
            int baseHpBeforeAttack = base.getHp();
            base.takeDamage(damage);
            progression.addDamageTakenByBase(baseHpBeforeAttack - base.getHp());

            if (base.isDestroyed()) {
                game.setGameOver(true);
            }
        }
    }

    public void progressionReward(ObservableList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                progression.rewardEnemyKill(enemy);
                giveMiniQuestRewardIfCompleted(enemy);
            }
        }


    }

    /*
    private void updateAlliesAttackTimers(ObservableList<Entity> allies, double deltaTime) {
        for (Entity ally : allies) {
            ally.updateAttackTimer(deltaTime);
        }
    }

    private void updateEnemiesAttackTimers(ObservableList<Enemy> enemies,double deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.updateAttackTimer(deltaTime);
        }
    }*/

    private void alliesAttackEnemies(ObservableList<Entity> allies, double deltaTime) {
        for (Entity ally : allies) {
            ally.updateAttackTimer(deltaTime);
            ally.attack(game);
        }
    }

    private void enemiesAttack(ObservableList<Enemy> enemies,double deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.updateAttackTimer(deltaTime);
            enemy.attack(game);
        }
    }

    private void updateMovements(ObservableList<Enemy> enemies,double deltaTime) {
        for (Enemy enemy : enemies) {
            if (!enemy.isInCombat(game)) {
                enemy.updateMovement(deltaTime);
            }
        }
    }

    private void giveMiniQuestRewardIfCompleted(Enemy enemy) {
        MiniQuest completedQuest = miniQuestManager.updateOnEnemyKilled(enemy, enemy.getLastDamagingTower());

        if (completedQuest != null) {
            progression.rewardMiniQuest(completedQuest);
            System.out.println("Mini-quete terminee : " + completedQuest.getTitle());
        }
    }
}
