package quintessencia.brewingmode;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DefaultMode extends BrewingMode{
    private static final int Cost = 1;
    @Override
    public boolean CanAfford() {
        if(AbstractDungeon.player != null){
            return EnergyPanel.totalCount >= Cost;
        }
        return false;
    }

    @Override
    public void PayCost() {
        addToBot(new LoseEnergyAction(Cost));
    }

    @Override
    public String ModeName() {
        return "Name Test";
    }

    @Override
    public String ModeDescription() {
        return "Default Test";
    }

    @Override
    public boolean ModeCondition() {
        return true;
    }

    @Override
    public String ModeUnlockDescription() {
        return "Unlock Test";
    }

    @Override
    public boolean AlwaysShow() {
        return true;
    }

    @Override
    public boolean Default() {
        return true;
    }
}
