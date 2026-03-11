package quintessencia.util.cardmod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;

public class FumeVeilMod extends AbstractCardModifier {

    @Override
    public String identifier(AbstractCard card) {
        return makeID(FumeVeilMod.class.getSimpleName());
    }

    @Override
    public void onInitialApplication(AbstractCard card) {


    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        int MaxPot = 0;
        ///If the player exists
        if(AbstractDungeon.player != null){
            ///For each potion they have
            for(AbstractPotion p: AbstractDungeon.player.potions){
                ///Try and get the highest potency among them
                if(p.getPotency() > MaxPot){
                    MaxPot = p.getPotency();
                }
            }
                return block += (MaxPot * 0.5f);
        }
        return super.modifyBlock(block, card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FumeVeilMod();
    }
}
