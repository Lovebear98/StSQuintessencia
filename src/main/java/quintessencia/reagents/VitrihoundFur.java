package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.VHFur;

public class VitrihoundFur extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(VitrihoundFur.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public VitrihoundFur(){
        this.PotCapPercent = 0.5f;
        this.PotReactPercent = 2.0f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Concrete;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(VitrihoundFur.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotCapPercent)+DESC[3]+DESC[1]+ConvFloatToPercent(PotReactPercent);
    }
    @Override
    public Texture Texture() {
        return VHFur;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
