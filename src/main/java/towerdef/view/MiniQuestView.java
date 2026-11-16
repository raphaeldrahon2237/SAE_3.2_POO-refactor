package towerdef.view;

import towerdef.model.quest.MiniQuest;

public class MiniQuestView {

        public static String displayText(MiniQuest quest) {
            String status;
            if (quest.isCompleted()) {
                status = "COMPLETED";
            } else {
                status = quest.getCurrentAmount() + "/" + quest.getTargetAmount();
            }

            return "• " + quest.getTitle() + " : " + status + "(Bonus +" +
                    quest.getRewardGold() + " gold, +" + quest.getRewardScore() + "score)";
        }
}
