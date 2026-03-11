package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.FGTalon;

public class FloragonTalon extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(FloragonTalon.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public FloragonTalon(){
        this.PotPercent = 1.35f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(FloragonTalon.class.getSimpleName());
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
        return FGTalon;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
