package quintessencia.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import quintessencia.cards.skill.Gather;


public class ToxicEggPatch {
    ///We don't mind things upgrading reagents, but the blanket bump of Toxic Egg's a little overkill
    @SpirePatch2(clz = ToxicEgg2.class, method = "onObtainCard")
    public static class Dontupgrademybabies {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getFieldName().equals("upgraded")) {
                        m.replace("$_ = !(c instanceof quintessencia.cards.skill." + Gather.class.getSimpleName() + ") && $proceed($$);");
                    }
                }
            };
        }
    }
}