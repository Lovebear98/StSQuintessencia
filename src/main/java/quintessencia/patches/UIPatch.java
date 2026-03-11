package quintessencia.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static quintessencia.QuintessenciaMod.AlchemyPanel;


@SuppressWarnings("unused")
public class UIPatch{
    public static AbstractCard Preview;
    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
                AlchemyPanel.render(sb);
        }
    }
    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(){
                AlchemyPanel.update();
        }
    }
}