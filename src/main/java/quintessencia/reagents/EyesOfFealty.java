package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class EyesOfFealty extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(EyesOfFealty.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public EyesOfFealty(){
        this.PotCapPercent = 1.5f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Concrete;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(EyesOfFealty.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotCapPercent);
    }

    @Override
    public Texture Texture() {
        return Textures.EyesOfFealty;
    }


}
