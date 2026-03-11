package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.WWScales;

public class WidowardenScales extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(WidowardenScales.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public WidowardenScales(){
        this.PotCapPercent = 2.0f;
        this.PotPercent = 0.5f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(WidowardenScales.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotPercent)+DESC[2]+DESC[1]+ConvFloatToPercent(PotCapPercent);
    }
    @Override
    public Texture Texture() {
        return WWScales;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
