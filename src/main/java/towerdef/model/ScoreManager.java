package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Archer;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.entity.troop.ShadowSoldier;
import towerdef.model.entity.troop.Wolf;
import towerdef.model.quest.MiniQuest;

import java.io.*;

public class ScoreManager {

    private static final String FILE_NAME = "score.txt";

    public static void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            int oldScore = loadScore(playerName);

            if(score > oldScore){
                saveScore(playerName, score);
            };
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde score : " + e.getMessage());
        }
    }

    public static int loadScore(String playerName) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();

            if (line != null) {
                String[] parts = line.split(";");

                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    if (name.equals(playerName)) {
                        return score;
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur lecture score : " + e.getMessage());
        }

        return 0;
    }

    public static class MiniQuestManager {

        public static final int MAX_QUESTS_PER_GAME = 3;

        private final ObservableList<MiniQuest> quests;
        private final IntegerProperty completedQuestCount;

        public MiniQuestManager() {
            this.quests = FXCollections.observableArrayList();
            this.completedQuestCount = new SimpleIntegerProperty(0);
            createGameQuests();
        }

        private void createGameQuests() {
            addQuest(new MiniQuest(
                    "Tuer 10 loups avec l'Archer Tower",
                    Wolf.class,
                    1,
                    "Archer Tower",
                    10,
                    80,
                    50
            ));

            addQuest(new MiniQuest(
                    "Tuer 8 archers avec la Mage Tower",
                    Archer.class,
                    4,
                    "Mage Tower",
                    8,
                    100,
                    70
            ));

            addQuest(new MiniQuest(
                    "Tuer 6 soldats de l'ombre avec l'Artillery Tower",
                    ShadowSoldier.class,
                    2,
                    "Artillery Tower",
                    6,
                    120,
                    90
            ));
        }

        private void addQuest(MiniQuest quest) {
            if (quests.size() < MAX_QUESTS_PER_GAME) {
                quests.add(quest);
            }
        }

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

        public IntegerProperty completedQuestCountProperty() {
            return completedQuestCount;
        }

        public String getDisplayText() {
            StringBuilder builder = new StringBuilder("Mini-quêtes bonus (max 3)\n");

            for (MiniQuest quest : quests) {
                builder.append(quest.getDisplayText()).append('\n');
            }

            return builder.toString().trim();
        }
    }
}