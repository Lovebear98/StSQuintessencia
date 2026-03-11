package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.patches.interfaces.BrewedPotionInterface;


@SuppressWarnings("unused")
public class CatchTurnEndPatch {
    public static AbstractCard Preview;
    @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")

    public static class RenderPatch{
        public static void Prefix(){
            for(AbstractPotion pot: AbstractDungeon.player.potions){
                if(pot instanceof BrewedPotionInterface){
                    ///Because something in here checks against energy, queue these as actions
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            ((BrewedPotionInterface) pot).onTurnEnd();
                            isDone = true;
                        }
                    });
                }
            }
        }
    }
}