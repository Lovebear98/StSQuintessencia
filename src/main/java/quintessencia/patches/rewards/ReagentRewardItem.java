package quintessencia.patches.rewards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import quintessencia.cards.skill.Gather;
import quintessencia.reagents.AbstractReagent;
import quintessencia.relics.BlueTrimmedNotebook;
import quintessencia.util.CustomActions.customeffects.RemoveRewardEffect;
import quintessencia.util.TextureLoader;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.OwnedPanel;
import static quintessencia.util.moreutil.ReagentListLoader.AddAllReagents;

public class ReagentRewardItem extends Reward {

    private static final Texture TEXTURE = TextureLoader.getTexture("quintessencia/ui/ReagentRewardIcon.png");
    private static final String TEXT = rewardStrings.TEXT[1];

    public RewardItem linkedReward;
    public boolean isLinkedRewardTaken;

    public ReagentRewardItem() {
        super(TEXTURE, TEXT, RewardItemTypeEnumPatch.REAGENT);
        this.cards = generateReagents();
    }


    @Override
    public boolean claimReward() {
        if (linkedReward != null && isLinkedRewardTaken) {
            return true;
        }
        AbstractDungeon.cardRewardScreen.open(cards,this, rewardStrings.TEXT[0]);
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        return false;
    }

    private static final int MaxTries = 50;
    public static ArrayList<AbstractCard> generateReagents() {
        ///Verify our list exists and is up to date
        AddAllReagents();
        ///Get a card of each of the 5 reagent types, using randomized numbers
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        tmp.add(new Gather(AbstractReagent.ReagentType.Regulus, AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2)));
        tmp.add(new Gather(AbstractReagent.ReagentType.Alkahest, AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2)));
        tmp.add(new Gather(AbstractReagent.ReagentType.Blas, AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2)));
        tmp.add(new Gather(AbstractReagent.ReagentType.Concrete, AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2)));
        tmp.add(new Gather(AbstractReagent.ReagentType.Humor, AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2)));

        if(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(BlueTrimmedNotebook.ID)){
            for(int i = BlueTrimmedNotebook.EffectNum; i > 0; i -= 1){
                AbstractCard c;
                int Tries = 0;
                do{c = tmp.get(AbstractDungeon.cardRng.random(0, tmp.size()-1)); Tries += 1;}while(c.upgraded && Tries < MaxTries);
                c.upgrade();
            }
        }

        return tmp;
    }

    public static ArrayList<AbstractCard> generateQuests() {
        ///Get the numbers at random for how many we'll get
        int NUM = AbstractDungeon.cardRng.random(1, 3);
        int UPG_NUM = AbstractDungeon.miscRng.random(2, 3);
        ///Get a card of each of the 5 reagent types, then return 3 of them.
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        tmp.add(new Gather(AbstractReagent.ReagentType.Alkahest, NUM, UPG_NUM));
        tmp.add(new Gather(AbstractReagent.ReagentType.Blas, NUM, UPG_NUM));
        tmp.add(new Gather(AbstractReagent.ReagentType.Concrete, NUM, UPG_NUM));
        tmp.add(new Gather(AbstractReagent.ReagentType.Regulus, NUM, UPG_NUM));
        tmp.add(new Gather(AbstractReagent.ReagentType.Humor, NUM, UPG_NUM));

        for(int i = 2; i > 0; i -= 1){
            int e = AbstractDungeon.miscRng.random(0, tmp.size() - 1);
            tmp.remove(e);
        }
        return tmp;
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

    private void renderLink(SpriteBatch sb) {
        sb.setColor(Color.WHITE);// 643
        sb.draw(ImageMaster.RELIC_LINKED, this.hb.cX - 64.0F, this.y - 64.0F + 52.0F * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);// 644
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!isLinkedRewardTaken && linkedReward != null) {
            renderLink(sb);
        }
    }

    private void renderOwnedPanel(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        OwnedPanel.render(sb);
    }
}