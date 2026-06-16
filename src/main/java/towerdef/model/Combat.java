package towerdef.model;

import towerdef.model.entity.Base;
import towerdef.model.entity.Entity;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.quest.MiniQuest;

import java.util.ArrayList;

public class Combat {

    private Game game;
    private GameEntities gameEntities;
    private Base base;
    private Progression progression;
    private ScoreManager.MiniQuestManager miniQuestManager;

    public Combat(Game game, GameEntities gameEntities, Base base, Progression progression, ScoreManager.MiniQuestManager miniQuestManager) {
        this.game = game;
        this.gameEntities = gameEntities;
        this.base = base;
        this.progression = progression;
        this.miniQuestManager = miniQuestManager;
    }

    public void update(double deltaTime) {
        updateAlliesAttackTimers(deltaTime);
        updateEnemiesAttackTimers(deltaTime);

        alliesAttackEnemies();
        enemiesAttack();

        if (!game.isGameOver()) {
            updateMovements(deltaTime);
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

    public void removeDeadEntities() {
        for (Enemy enemy : new ArrayList<>(gameEntities.getEnemies())) {
            if (!enemy.isAlive()) {
                progression.rewardEnemyKill(enemy);
                giveMiniQuestRewardIfCompleted(enemy);
            }
        }

        gameEntities.removeDeadEntities();
    }

    private void updateAlliesAttackTimers(double deltaTime) {
        for (Entity ally : gameEntities.getAllies()) {
            ally.updateAttackTimer(deltaTime);
        }
    }

    private void updateEnemiesAttackTimers(double deltaTime) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.updateAttackTimer(deltaTime);
        }
    }

    private void alliesAttackEnemies() {
        for (Entity ally : gameEntities.getAllies()) {
            ally.attack(game);
        }
    }

    private void enemiesAttack() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.attack(game);
        }
    }

    private void updateMovements(double deltaTime) {
        for (Enemy enemy : gameEntities.getEnemies()) {
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
