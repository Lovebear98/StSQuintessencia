package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class Hexflames extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(Hexflames.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public Hexflames(){
        this.EnergyCost = 2;
        this.PotPercent = 2.0f;
        this.PotReactPercent = 2.0f;
        this.PotCapPercent = 2.0f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Concrete;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(Hexflames.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+ConvFloatToPercent(PotPercent)+DESC[5]+
                DESC[1]+ConvFloatToPercent(PotCapPercent)+DESC[5]+
                DESC[2]+ConvFloatToPercent(PotReactPercent)+DESC[5]+
                DESC[3]+EnergyCost+DESC[4];
    }

    @Override
    public Texture Texture() {
        return Textures.Hexflames;
    }

}
