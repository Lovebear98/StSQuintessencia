package quintessencia.util.cardmod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import quintessencia.potions.BrewedPotion;

import static quintessencia.QuintessenciaMod.makeID;

public class StimulantMod extends AbstractCardModifier {

    @Override
    public String identifier(AbstractCard card) {
        return makeID(StimulantMod.class.getSimpleName());
    }

    @Override
    public void onInitialApplication(AbstractCard card) {


    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        int MaxPot = 0;
        ///If the player exists
        if(AbstractDungeon.player != null){
            ///For each potion they have
            for(AbstractPotion p: AbstractDungeon.player.potions){
                ///If it's brewed
                if(p instanceof BrewedPotion){
                    ///+1 if the potency is maxed
                    if(p.getPotency() >= ((BrewedPotion) p).maxPotency()){
                        MaxPot += 1;
                    }
                    ///otherwise
                }else{
                    ///+1 if it's not a potion slot
                    if(!(p instanceof PotionSlot)){
                        MaxPot += 1;
                    }
                }
            }
            return magic += MaxPot;
        }
        return super.modifyBaseMagic(magic, card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new StimulantMod();
    }
}
