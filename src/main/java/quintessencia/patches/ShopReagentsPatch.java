package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import quintessencia.cards.skill.Gather;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.ShopReagents;
import static quintessencia.util.moreutil.AlchemyHandler.RandomReagentType;


public class ShopReagentsPatch {

    @SpirePatch(clz = ShopScreen.class,
            method = "init")

///Buy reagents!
    public static class RenderPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Postfix(ShopScreen __instance){

            ///If the setting is on
            if(ShopReagents){
                ///Make a tepmorary list
                ArrayList<AbstractCard> tmp = new ArrayList<>();

                ///And for each colorless card in the shop
                for(AbstractCard c: __instance.colorlessCards){
                    ///If it's ACTUALLY colorless
                    if(c.color == AbstractCard.CardColor.COLORLESS){
                        ///Make a reagent to replace it
                        AbstractCard rc = new Gather(RandomReagentType(), AbstractDungeon.cardRng.random(1, 3), AbstractDungeon.miscRng.random(1, 2));
                        ///The reagent inherits its price/rarity from what it replaces
                        rc.price = c.price/2;
                        rc.rarity = c.rarity;
                        ///Upgrade it if it's rare
                        if(rc.rarity == AbstractCard.CardRarity.RARE){
                            rc.upgrade();
                        }
                        ///Add the new card to our temp list
                        tmp.add(rc);
                    }
                }
                ///Clear the list of the original cards
                __instance.colorlessCards.clear();
                ///Then replace it with our temp list
                __instance.colorlessCards = tmp;
            }
        }
    }




    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ShopScreen.class, "initCards");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}