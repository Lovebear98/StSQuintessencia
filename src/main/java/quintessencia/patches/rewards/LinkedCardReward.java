package quintessencia.patches.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import quintessencia.util.CustomActions.customeffects.RemoveRewardEffect;
import quintessencia.util.TextureLoader;

import java.util.ArrayList;

public class LinkedCardReward extends CustomReward {


    private static final Texture ICON = TextureLoader.getTexture("quintessencia/ui/NormalCardReward.png");
    public RewardItem linkedReward;
    public boolean isLinkedRewardTaken;
    public static final UIStrings strings = CardCrawlGame.languagePack.getUIString("RewardItem");

    ///Pass in a list of cards and our basic reward to link
    public LinkedCardReward(ArrayList<AbstractCard> cards, ReagentRewardItem reward) {
        super(ICON,strings.TEXT[2], RewardItemTypeEnumPatch.LINKED_REAGENT);
        linkedReward = reward;
        this.cards = cards;
        isLinkedRewardTaken = false;
        if (reward != null) {
            reward.linkedReward = this;
        }
    }

    @Override
    public boolean claimReward() {
        if (linkedReward != null && isLinkedRewardTaken) {
            return true;
        }
        AbstractDungeon.cardRewardScreen.open(cards,this, strings.TEXT[4]);
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (linkedReward != null && !AbstractDungeon.combatRewardScreen.rewards.contains(linkedReward) && AbstractDungeon.combatRewardScreen.rewards.contains(this)) {
            this.isLinkedRewardTaken = true;
            this.ignoreReward = true;
            AbstractDungeon.effectList.add(new RemoveRewardEffect(this)); //you get a crash if you remove the reward directly in here
        }
    }

}