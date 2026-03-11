package quintessencia.util.CustomActions.customeffects;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class MassReplace {
    public static void ReplaceInDeck(AbstractCard.CardTags TargetTag, AbstractCard NewCard){
        for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if(c.hasTag(TargetTag) && !c.inBottleFlame && !c.inBottleLightning && !c.inBottleTornado && !inOtherBottle(c)){
                boolean Upgrade = c.upgraded;
                AbstractDungeon.topLevelEffects.add(new FastPurgeEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new RemoveCardEffect(c));
                AbstractCard c2 = NewCard.makeCopy();
                if(Upgrade){
                    c2.upgrade();
                }
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(c2, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }
    }


    ///Want to include an external bottle in the safe list? Postfix this method!
    private static boolean inOtherBottle(AbstractCard c) {
        return false;
    }
}
