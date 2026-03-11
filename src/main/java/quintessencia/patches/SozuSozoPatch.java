package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;

import static quintessencia.util.moreutil.SozoManager.ChangeSozoProgress;

public class SozuSozoPatch {

    @SpirePatch(
            clz= AbstractRelic.class,
            method="flash"
    )
    public static class Count {

        ///Since this only serves to render power tips, we can prefix this one
        @SpirePostfixPatch
        public static void CountIt(AbstractRelic __instance){
            if(__instance instanceof Sozu){
                ChangeSozoProgress(1);
            }
        }
    }
}

