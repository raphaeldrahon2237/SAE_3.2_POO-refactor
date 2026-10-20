package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Archer;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.troop.enemy.ShadowSoldier;
import towerdef.model.entity.troop.enemy.Wolf;
import towerdef.model.quest.MiniQuest;

public class MiniQuestManager {

    public static final int MAX_QUESTS_PER_GAME = 3;

    private final ObservableList<MiniQuest> quests;
    private final IntegerProperty completedQuestCount;

    public MiniQuestManager() {
        this.quests = FXCollections.observableArrayList();
        this.completedQuestCount = new SimpleIntegerProperty(0);
        createGameQuests();
    }

    // Creates the mini quests available during the game.
    private void createGameQuests() {
        addQuest(new MiniQuest(
                "Kill 10 wolves with the Archer Tower",
                Wolf.class,
                1,
                "Archer Tower",
                10,
                80,
                50
        ));

        addQuest(new MiniQuest(
                "Kill 8 archers with the Mage Tower",
                Archer.class,
                4,
                "Mage Tower",
                8,
                100,
                70
        ));

        addQuest(new MiniQuest(
                "Kill 6 shadow soldiers with the Artillery Tower",
                ShadowSoldier.class,
                2,
                "Artillery Tower",
                6,
                120,
                90
        ));
    }

    // Adds a quest only if the maximum number of quests has not been reached.
    private void addQuest(MiniQuest quest) {
        if (quests.size() < MAX_QUESTS_PER_GAME) {
            quests.add(quest);
        }
    }

    // Updates the mini quests when an enemy is killed and returns the completed quest if there is one.
    public MiniQuest updateOnEnemyKilled(Enemy enemy, Tower killerTower) {
        for (MiniQuest quest : quests) {
            if (quest.matches(enemy, killerTower)) {
                quest.progress();

                if (quest.isCompleted() && !quest.isRewardClaimed()) {
                    quest.claimReward();
                    completedQuestCount.set(completedQuestCount.get() + 1);
                    return quest;
                }
            }
        }

        return null;
    }

    public ObservableList<MiniQuest> getQuests() {
        return FXCollections.unmodifiableObservableList(quests);
    }

    public int getCompletedQuestCount() {
        return completedQuestCount.get();
    }
}