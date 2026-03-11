package quintessencia.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class UpdatePotionsAction extends AbstractGameAction {
    @Override
    public void update() {
        if(AbstractDungeon.player != null){
            for(AbstractPotion p:AbstractDungeon.player.potions){
                p.initializeData();
            }
        }
        isDone = true;
    }
}
