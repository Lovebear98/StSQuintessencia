package quintessencia.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.compendium.PotionViewScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import javassist.CtBehavior;
import quintessencia.util.moreutil.CardEnums;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.makeID;


///Thank you Bryan



public class PotionRarityPatch {
    private static ArrayList<AbstractPotion> eventPotionList = new ArrayList<>();
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PotionPatch"));
    private static final String categoryTitle = uiStrings.TEXT[0];
    private static final String categoryDescription = uiStrings.TEXT[1];

    private static ArrayList<String> tempList = new ArrayList<>();

    @SpirePatch2(
            clz = PotionViewScreen.class,
            method = "sortOnOpen"
    )
    public static class PotionsPatchSort {
        @SpirePostfixPatch
        public static void Postfix() {
            eventPotionList = new ArrayList<>();
            eventPotionList = PotionHelper.getPotionsByRarity(CardEnums.CONCOCTION);
        }
    }

    @SpirePatch2(
            clz = PotionViewScreen.class,
            method = "update"
    )
    public static class PotionsPatchUpdate {
        @SpirePostfixPatch
        public static void Postfix(PotionViewScreen __instance) {
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(PotionViewScreen.class, "updateList",
                    ArrayList.class);
            method.invoke(__instance, eventPotionList);
        }
    }

    @SpirePatch2(
            clz = PotionHelper.class,
            method = "getRandomPotion",
            paramtypez = {}
    )
    @SpirePatch2(
            clz = PotionHelper.class,
            method = "getRandomPotion",
            paramtypez = {Random.class}
    )
    public static class NoEventPotionsFromHelper {
        @SpirePrefixPatch
        public static void Prefix() {
            tempList = new ArrayList<>();
            tempList.addAll(PotionHelper.potions);
            tempList.removeIf(str -> (PotionHelper.getPotion(str).rarity != CardEnums.CONCOCTION));
            PotionHelper.potions.removeIf(str -> (PotionHelper.getPotion(str).rarity == CardEnums.CONCOCTION));
        }

        @SpirePostfixPatch
        public static void Postfix() {
            PotionHelper.potions.addAll(tempList);
        }
    }

    @SpirePatch2(
            clz = PotionViewScreen.class,
            method = "render"
    )
    public static class PotionPatchRender {
        @SpireInsertPatch (
                locator = RenderLocator.class,
                localvars = "sb"
        )
        public static void Insert(PotionViewScreen __instance, @ByRef SpriteBatch[] sb) {
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(PotionViewScreen.class,
                    "renderList", SpriteBatch.class, String.class, String.class,
                    ArrayList.class);
            method.invoke(__instance, sb[0], categoryTitle, categoryDescription, eventPotionList);
        }
        private static class RenderLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior behavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(MenuCancelButton.class, "render");
                return LineFinder.findInOrder(behavior, matcher);
            }
        }
    }
}
