package quintessencia.util.CustomActions;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LoseTempHPAction extends AbstractGameAction {
    public LoseTempHPAction(AbstractCreature target, AbstractCreature source, int i) {
        this.setValues(target, source, i);// 11
    }// 12

    public void update() {
        TempHPField.tempHp.set(this.target, Math.max(0, (TempHPField.tempHp.get(target)-amount)));// 17
        this.isDone = true;// 18
    }// 19
}
