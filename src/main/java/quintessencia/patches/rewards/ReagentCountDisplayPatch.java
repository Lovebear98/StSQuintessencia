package quintessencia.patches.rewards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import static quintessencia.QuintessenciaMod.OwnedPanel;


public class ReagentCountDisplayPatch {

    @SpirePatch(clz = CombatRewardScreen.class,
            method = "render")


    public static class RenderPatch{
        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen __instance, SpriteBatch sb){
            if(__instance.rewards != null){
                if(!__instance.rewards.isEmpty()){
                    for(RewardItem r : __instance.rewards){
                        if(r instanceof ReagentRewardItem){
                            ///Prevent the weird flashy-fady
                            sb.setColor(Color.WHITE.cpy());
                            OwnedPanel.render(sb);
                        }
                    }
                }
            }
        }
    }



    @SpirePatch(clz = CombatRewardScreen.class,
            method = "update")


    public static class UpdatePatch{
        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen __instance){
            if(__instance.rewards != null){
                if(!__instance.rewards.isEmpty()){
                    for(RewardItem r : __instance.rewards){
                        if(r instanceof ReagentRewardItem){
                            OwnedPanel.update();
                        }
                    }
                }
            }
        }
    }
}