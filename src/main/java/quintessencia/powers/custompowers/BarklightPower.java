package quintessencia.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import quintessencia.powers.BasePower;
import quintessencia.util.CustomActions.UpdatePotionsAction;

import static quintessencia.QuintessenciaMod.makeID;

public class BarklightPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(BarklightPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public BarklightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToTop(new UpdatePotionsAction());
    }

    @Override
    public void onRemove() {
        super.onRemove();
        addToTop(new UpdatePotionsAction());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new BarklightPower(owner, amount);
    }
}