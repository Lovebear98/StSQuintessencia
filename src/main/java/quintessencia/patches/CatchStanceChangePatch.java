package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.patches.interfaces.BrewedPotionInterface;


@SpirePatch(
        clz = AbstractPlayer.class,
        method = "switchedStance"
)
public class CatchStanceChangePatch {
        @SpirePostfixPatch
        public static void Postfix() {
            ///If the action's done
            if(!AbstractDungeon.player.potions.isEmpty()){
                for(AbstractPotion pot: AbstractDungeon.player.potions){
                    if(pot instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) pot).onStanceChange();
                    }
                }
            }
        }
    }