package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.patches.interfaces.BrewedPotionInterface;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "heal" 
)
public class CatchHealPatch {
    @SpireInsertPatch(
            rloc = 8
    )
        public static void Insert(AbstractPlayer __instance, int __healAmount) {
            ///811 is where we lose HP based on the damage
            ///739 is the first line of the method

            if(__healAmount > 0){
                    if(!AbstractDungeon.player.potions.isEmpty()){
                        for(AbstractPotion pot : AbstractDungeon.player.potions){
                            if(pot instanceof BrewedPotionInterface){
                                ((BrewedPotionInterface) pot).onHeal(__healAmount);
                            }
                        }
                    }
                    ///checkWomanInBlueR(___damageAmount);
            }
        }
    }