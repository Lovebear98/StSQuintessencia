package quintessencia.util.cardmod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;

public class PotionStrikeMod extends AbstractCardModifier {

    @Override
    public String identifier(AbstractCard card) {
        return makeID(PotionStrikeMod.class.getSimpleName());
    }

    @Override
    public void onInitialApplication(AbstractCard card) {


    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
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
            if(card.upgraded){
                return damage += MaxPot;
            }else{
                return damage += (MaxPot * 0.5f);
            }
        }
        return super.modifyDamage(damage, type, card, target);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PotionStrikeMod();
    }
}
