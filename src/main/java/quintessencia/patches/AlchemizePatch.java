package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import quintessencia.potions.Titanicider;
import quintessencia.reagents.AbstractReagent;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.ChangeAlchemize;
import static quintessencia.util.moreutil.AlchemyHandler.GetRandomReagent;
import static quintessencia.util.moreutil.ReagentListLoader.*;

@SpirePatch(
        clz = Alchemize.class,
        method = "use"
)
public class AlchemizePatch {
    @SpirePrefixPatch
        public static SpireReturn Insert(AbstractCard __instance, AbstractPlayer __p, AbstractMonster ___m) {
        if(ChangeAlchemize){
            Titanicider pot = new Titanicider();
            if (pot.Ingredients != null && pot.Ingredients.isEmpty()) {
                ///Make a new list
                ArrayList<AbstractReagent> newlist = new ArrayList<>();
                ///Add a random reagent of each type to it
                newlist.add(GetRandomReagent(AllRegulus()));
                newlist.add(GetRandomReagent(AllAlkahests()));
                newlist.add(GetRandomReagent(AllBlas()));
                newlist.add(GetRandomReagent(AllConcretes()));
                newlist.add(GetRandomReagent(AllHumors()));

                if(__instance.upgraded){
                    newlist.add(GetRandomReagent(AllReagents));
                    newlist.add(GetRandomReagent(AllReagents));
                }
                ///Then use that as the foundation of a new potion
                pot = new Titanicider(newlist, 0);
            }
            if (pot.Ingredients != null && !pot.Ingredients.isEmpty()) {
                AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(pot));
                return SpireReturn.Return();
            }
        }
        return SpireReturn.Continue();
    }
}