package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.WJaw;

public class WormJaw extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(WormJaw.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public WormJaw(){
        this.PotPercent = 0.1f;
        this.PotReactPercent = 3.5f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(WormJaw.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotPercent)+DESC[2]+DESC[1]+ConvFloatToPercent(PotReactPercent);
    }

    @Override
    public Texture Texture() {
        return WJaw;
    }

}
