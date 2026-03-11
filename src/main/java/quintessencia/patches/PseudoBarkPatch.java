package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.powers.custompowers.BarklightPower;

import static quintessencia.util.moreutil.Vars.isInCombat;


@SpirePatch(
        clz = AbstractPotion.class,
        method = "getPotency",
        paramtypez = {}
)
public class PseudoBarkPatch {
        @SpirePostfixPatch
        public static int Postfix(int i) {
            int e = i;
            if(AbstractDungeon.player != null){
                if(isInCombat()){
                    ///Add potency increasing powers
                    if(AbstractDungeon.player.hasPower(BarklightPower.POWER_ID)){
                        e += AbstractDungeon.player.getPower(BarklightPower.POWER_ID).amount;
                    }
                }
                //////Add potency increasing potions
                ///if(AbstractDungeon.player.hasRelic(SimpleRelic.ID)){
                ///    e += AbstractDungeon.player.getRelic(SimpleRelic.ID).counter;
                ///}
            }
            return e;
        }
    }