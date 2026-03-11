package quintessencia.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import quintessencia.cards.attack.BrewingStrike;
import quintessencia.cards.skill.FumeVeil;

public class UpdatePotionScaling extends AbstractGameAction {
    @Override
    public void update() {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof BrewingStrike || c instanceof FumeVeil){
                c.applyPowers();
            }
        }
        isDone = true;
    }
}
