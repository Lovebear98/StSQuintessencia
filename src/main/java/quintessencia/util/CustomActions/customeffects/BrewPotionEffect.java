package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import quintessencia.patches.interfaces.OnBrewInterface;
import quintessencia.reagents.AbstractReagent;

import java.util.ArrayList;

import static quintessencia.util.moreutil.AlchemyHandler.GetBrew;

public class BrewPotionEffect extends AbstractGameEffect {

    ArrayList<AbstractReagent> ReagentsForPotion = new ArrayList<>();
    public BrewPotionEffect(ArrayList<AbstractReagent> List) {
    this.ReagentsForPotion = List;
    }


    @Override
    public void update() {
        if(!ReagentsForPotion.isEmpty()){
            ///Generate the potion in a method, so it can be more easily modified via patch.
            AbstractPotion p = GetBrew(ReagentsForPotion);
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(p));
            ///Then trigger any on-Brew effects
            for(AbstractRelic r: AbstractDungeon.player.relics){
                if(r instanceof OnBrewInterface){
                    ((OnBrewInterface)r).onBrew(p);
                }
            }
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c instanceof OnBrewInterface){
                    ((OnBrewInterface) c).onBrew(p);
                }
            }
            ///Remember to fire any on-brew effects of those reagents!
            for(AbstractReagent r: ReagentsForPotion){
                r.onBrew(p);
            }
        }
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
