package quintessencia.patches.rewards;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RewardItemTypeEnumPatch {
    @SpireEnum
    public static RewardItem.RewardType LINKED_REAGENT;
    @SpireEnum
    public static RewardItem.RewardType REAGENT;
    @SpireEnum
    public static RewardItem.RewardType QUEST_CARD;

}