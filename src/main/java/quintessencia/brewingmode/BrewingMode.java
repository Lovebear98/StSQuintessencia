package quintessencia.brewingmode;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

///A WIP setup to allow modular "brewing modes" that can enable alternate costs for brewing potions
///Need to build the menu for this and hook it onto the existing menu. My fault for making the menu first. :^)
public abstract class BrewingMode {
    ///The Brewing Mode's ID
    public String ID;

    ///If we can afford to brew with this mode
    public boolean CanAfford(){
        return false;
    }
    ///Pay the cost with this mode
    public void PayCost(){

    }
    ///The mode's name to display
    public String ModeName(){
        return "Name Test";
    }
    ///The text describing the mode and what it costs.
    public String ModeDescription(){
        return "Description Test";
    }
    ///The boolean that decides if this condition is unlocked or not
    public boolean ModeCondition(){
        return true;
    }
    ///The text that tells us the unlock condition
    public String ModeUnlockDescription(){
        return "Unlock Test";
    }
    ///This will let us scroll to a mode even if it's locked
    public boolean AlwaysShow() {
        return true;
    }
    ///The mode is a default mode and cannot be removed
    public boolean Default() {
        return false;
    }

    public void addToBot(AbstractGameAction action){
        AbstractDungeon.actionManager.addToBottom(action);
    }
    public void addToTop(AbstractGameAction action){
        AbstractDungeon.actionManager.addToTop(action);
    }
}
