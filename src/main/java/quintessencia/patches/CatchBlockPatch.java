package quintessencia.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import javassist.CtBehavior;
import quintessencia.patches.interfaces.BrewedPotionInterface;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock",
        paramtypez={
                int.class,
        }

)
// A patch to make MindControlPower trigger when monsters gain block
public class CatchBlockPatch {
    @SpireInsertPatch(locator = CatchBlockPatch.Locator.class, localvars = {"tmp"})
    public static void TriggerOnGainedBlock(AbstractCreature instance, int blockAmount, @ByRef float[] tmp) {
        ///If we're actually gaining block
        if (tmp[0] > 0.0F) {
            ///If we're a player
            if (instance.isPlayer) {
                for(AbstractPotion po: AbstractDungeon.player.potions){
                    if(po instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) po).onPlayerBlock((int) tmp[0]);
                    }
                }
                ///If we're a monster
            }else{
                for(AbstractPotion po: AbstractDungeon.player.potions){
                    if(po instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) po).onMonsterBlock((int) tmp[0]);
                    }
                }
            }
        }

    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}