package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.stances.WrathStance;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SWRotdrool;

public class SoulwinderRotdrool extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(SoulwinderRotdrool.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public SoulwinderRotdrool(){
    }

    ///This reagent is a Humor, so we use it in the Humor slot to add quirky effects
    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    ///The reagent's ID
    @Override
    public String REAGENT_ID() {
        return makeID(SoulwinderRotdrool.class.getSimpleName());
    }
    ///The reagent's name
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The description in the UI
    @Override
    public String reagentDesc(int i) {
        return DESC[0];
    }
    ///The descriptoin this attaches to the potion
    @Override
    public String potionDesc(int i) {
        return DESC[0];
    }

    @Override
    public Texture Texture() {
        return SWRotdrool;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {
        addToBot(new ChangeStanceAction(new WrathStance()));
    }
}
