package quintessencia.util.moreutil;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.cards.skill.Gather;
import quintessencia.patches.interfaces.BrewedPotionInterface;
import quintessencia.potions.*;
import quintessencia.reagents.AbstractReagent;
import quintessencia.ui.AlchemyMenu;

import java.util.ArrayList;
import java.util.Objects;

import static quintessencia.QuintessenciaMod.AlchemyPanel;
import static quintessencia.util.moreutil.ReagentListLoader.*;

public class AlchemyHandler {

    ///Stores key static info on Alchemy, but also handles alchemy-related functions

    public static final int BasePotency = 10;
    public static final int BasePotencyCap = BasePotency;

    ///Tells us if the Alchemy Panel's in display mode or not
    public static boolean DisplayAlchemy = false;
    ///The universal tag that we hsould play refine sounds
    public static boolean PlaySound = false;


    ///Generates the appropriate potion with a given list of reagents.
    public static BrewedPotion GetBrew(ArrayList<AbstractReagent> r){
        ///Return a new brewed potion at random. They're all the same except aesthetics.
        ///It can be postfix patched to return differently, such as for certain reagent combos to make something special
        switch(MathUtils.random(1, 5)){
            case 1:
                return new AlchemiaRegia(r);
            case 2:
                return new Chimerelixis(r);
            case 3:
                return new VolteaRegia(r);
            case 4:
                return new Opthistoria(r);
            case 5:
                return new Titanicider(r);
            default:
                ///Philtered Chaos is our "backup" potion
                return new PhilteredChaos(r);
        }
    }

    public static void ClearReactions(){
        if(AbstractDungeon.player != null){
            for(AbstractPotion p : AbstractDungeon.player.potions){
                if(p instanceof BrewedPotionInterface){
                    ((BrewedPotionInterface)p).ResetReact();
                }
            }
        }
    }

    public static AbstractPotion.PotionSize RandomPotionType() {
        int PotionRNG = MathUtils.random(0, 11);
        AbstractPotion.PotionSize ex;
            switch(PotionRNG){
                case 1: ex = AbstractPotion.PotionSize.SNECKO;
                    break;
                case 2: ex = AbstractPotion.PotionSize.JAR;
                    break;
                case 3: ex = AbstractPotion.PotionSize.HEART;
                    break;
                case 4: ex = AbstractPotion.PotionSize.GHOST;
                    break;
                case 5: ex = AbstractPotion.PotionSize.CARD;
                    break;
                case 6: ex = AbstractPotion.PotionSize.BOTTLE;
                    break;
                case 7: ex = AbstractPotion.PotionSize.BOLT;
                    break;
                case 8: ex = AbstractPotion.PotionSize.H;
                    break;
                case 9: ex = AbstractPotion.PotionSize.M;
                    break;
                case 10: ex = AbstractPotion.PotionSize.S;
                    break;
                case 11: ex = AbstractPotion.PotionSize.T;
                    break;
                default: ex = AbstractPotion.PotionSize.FAIRY;
                    break;
            }
        return ex;
    }


    ///Commands that open, close, or toggle the menu
    public static void OpenAlchemy(){
        AlchemyMenu x = AlchemyPanel;
        x.Toggley(true);
    }
    public static void CloseAlchemy(){
        AlchemyMenu x = AlchemyPanel;
        x.Toggley(false);
    }
    public static void ToggleAlchemy(){
        AlchemyMenu x = AlchemyPanel;
        x.Toggley();
    }

    ///Something that allows easily converting an ID back to a reagent
    public static AbstractReagent IDToReagent(String s){
        for(AbstractReagent r: AllReagents){
            if(Objects.equals(r.REAGENT_ID(), s)){
                return r;
            }
        }
        return new NoReagent();
    }

    public static AbstractReagent GetRandomReagent(ArrayList<AbstractReagent> list) {
        AddAllReagents();
            int e;
            if (AbstractDungeon.miscRng != null) {
                e = AbstractDungeon.miscRng.random(0, list.size() - 1);
            } else {
                e = MathUtils.random(0, list.size() - 1);
            }
            return list.get(e);
    }

    public static AbstractReagent GetTypedReagent(AbstractReagent.ReagentType t){
        if(t == AbstractReagent.ReagentType.Alkahest){
            return GetRandomReagent(AllAlkahests());
        }
        if(t == AbstractReagent.ReagentType.Blas){
            return GetRandomReagent(AllBlas());
        }
        if(t == AbstractReagent.ReagentType.Concrete){
            return GetRandomReagent(AllConcretes());
        }
        if(t == AbstractReagent.ReagentType.Regulus){
            return GetRandomReagent(AllRegulus());
        }
        if(t == AbstractReagent.ReagentType.Humor){
            return GetRandomReagent(AllHumors());
        }
        return GetRandomReagent(AllReagents);
    }

    public static ArrayList<AbstractCard> getReagentCards() {
        AddAllReagents();
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        tmp.add(new Gather(AbstractReagent.ReagentType.Alkahest));
        tmp.add(new Gather(AbstractReagent.ReagentType.Blas));
        tmp.add(new Gather(AbstractReagent.ReagentType.Concrete));
        tmp.add(new Gather(AbstractReagent.ReagentType.Regulus));
        tmp.add(new Gather(AbstractReagent.ReagentType.Humor));

        for(int i = 2; i > 0; i -= 1){
            int e = AbstractDungeon.miscRng.random(0, tmp.size() - 1);
            tmp.remove(e);
        }
        return tmp;
    }

    //Print the number of each we own
    public static int OwnedAlkahestsNum(){
        int i = 0;
        if(!AllReagents.isEmpty()){
            for(AbstractReagent r: AllAlkahests()){
                i += r.NumberOwned;
            }
        }
        return i;
    }
    public static int OwnedBlasNum(){
        int i = 0;
        if(!AllReagents.isEmpty()){
            for(AbstractReagent r: AllBlas()){
                i += r.NumberOwned;
            }
        }
        return i;
    }
    public static int OwnedConcretesNum(){
        int i = 0;
        if(!AllReagents.isEmpty()){
            for(AbstractReagent r: AllConcretes()){
                i += r.NumberOwned;
            }
        }
        return i;
    }
    public static int OwnedRegulusNum(){
        int i = 0;
        if(!AllReagents.isEmpty()){
            for(AbstractReagent r: AllRegulus()){
                i += r.NumberOwned;
            }
        }
        return i;
    }
    public static int OwnedHumorsNum(){
        int i = 0;
        if(!AllReagents.isEmpty()){
            for(AbstractReagent r: AllHumors()){
                i += r.NumberOwned;
            }
        }
        return i;
    }

    public static AbstractReagent.ReagentType RandomReagentType(){
        if(AbstractDungeon.player != null){
            int i = AbstractDungeon.cardRng.random(1, 5);
            if(i == 1){
                return AbstractReagent.ReagentType.Alkahest;
            }
            if(i == 2){
                return AbstractReagent.ReagentType.Blas;
            }
            if(i == 3){
                return AbstractReagent.ReagentType.Concrete;
            }
            if(i == 4){
                return AbstractReagent.ReagentType.Regulus;
            }
            if(i == 5){
                return AbstractReagent.ReagentType.Humor;
            }
        }
        return AbstractReagent.ReagentType.Alkahest;
    }
}
