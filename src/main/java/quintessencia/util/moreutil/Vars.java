package quintessencia.util.moreutil;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import quintessencia.potions.BrewedPotion;
import quintessencia.reagents.AbstractReagent;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.ReagentListLoader.AllReagents;

public class Vars {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("VarsText"));
    ///The ID we'll use to check for potion info to trade them clearly
    public static String TiSIngredientsID = makeID("TiSBrewedPotion");
    public static final String CLINKSOUNDKEY = makeID("ClinkSound");

    public static boolean isInCombat() {
        if(CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            return !AbstractDungeon.getMonsters().areMonstersBasicallyDead();
        }
        return false;
    }
    public static String StarterKitText(){
        return uiStrings.TEXT[0];
    }
    public static String UnlinkReagentsText(){
        return uiStrings.TEXT[1];
    }
    public static String ExtraSlotText(){
        return uiStrings.TEXT[2];
    }
    public static String AlchemizeText(){
        return uiStrings.TEXT[3];
    }
    public static String RelicsText(){return uiStrings.TEXT[4];}
    public static String ShopText(){return uiStrings.TEXT[5];}
    public static String TipsText(){return uiStrings.TEXT[6];}
    public static String ClarityText(){return uiStrings.TEXT[7];}



    public static String CombatClarityText(){return uiStrings.EXTRA_TEXT[1];}
    public static String CombatNoClarityText(){return uiStrings.EXTRA_TEXT[2];}


    public static String TotalReagentsText(){
        int Num = 0;
        if(!AllReagents.isEmpty()){
            Num = AllReagents.size();
        }
        return Num + uiStrings.EXTRA_TEXT[0];}





    ///Gets a potion's ingredients, or nothing if it has none
    public static ArrayList<AbstractReagent> GetReagentList(AbstractPotion p){
        ///Start with nothing
        ArrayList<AbstractReagent> list = new ArrayList<>();
        ///Then if it's one of our potions
        if(p instanceof BrewedPotion){
            ///Return its list of ingredients
            return ((BrewedPotion) p).Ingredients;
        }
        return list;
    }

    public static Color RandomColor(){
        return new Color(MathUtils.random(0f, 255f)/255f, MathUtils.random(0f, 255f)/255f, MathUtils.random(0f, 255f)/255f, 1);
    }
}
