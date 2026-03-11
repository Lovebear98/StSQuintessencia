package quintessencia.patches.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static quintessencia.QuintessenciaMod.makeID;


public abstract class Reward extends CustomReward {
    protected static final UIStrings rewardStrings = CardCrawlGame.languagePack.getUIString(makeID("RewardItems"));

    public Reward(Texture texture, String text, RewardType type){
        super(texture, text, type);
    }
}