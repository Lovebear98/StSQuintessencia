package quintessencia.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


public class SkipOrbTipPatch {
    ///We don't mind things upgrading reagents, but the blanket bump of Toxic Egg's a little overkill
    @SpirePatch2(clz = AbstractOrb.class, method = "update")
    public static class TipBGone {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(Hitbox.class.getName()) && m.getFieldName().equals("hovered")) {
                        m.replace("$_ = !quintessencia.ui.AlchemyMenu.AlchemyHovered && $proceed($$);");
                    }
                }
            };
        }
    }
}