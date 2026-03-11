package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.patches.interfaces.BrewedPotionInterface;

public class CatchDamaged {

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="damage"
    )
    public static class Damagi {
        @SpireInsertPatch(
                rloc = 89
        )
 public static void Damagii(AbstractPlayer __instance, DamageInfo info, int ___damageAmount){
            ///At 1813/1814, damageAmount is final and is the HP loss happening
            ///At 1725, the line's first method

            ///If we actually take damage
            if(___damageAmount > 0){
                if(!AbstractDungeon.player.potions.isEmpty()){
                    for(AbstractPotion pot: AbstractDungeon.player.potions){
                        if(pot instanceof BrewedPotionInterface){
                            ((BrewedPotionInterface) pot).onLoseHP(___damageAmount);
                        }
                    }
                }
                ///checkDemonPurge(___damageAmount);
            }
        }


    }
}

