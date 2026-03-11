package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.FGMoss;

public class FloragonMoss extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(FloragonMoss.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public FloragonMoss(){
        this.PotPercent = 1.35f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Concrete;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(FloragonMoss.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotPercent);
    }
    @Override
    public Texture Texture() {
        return FGMoss;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
