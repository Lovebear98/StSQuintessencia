package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.VHClaw;

public class VitrihoundClaw extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(VitrihoundClaw.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public VitrihoundClaw(){
        this.PotCapPercent = 1.4f;
        this.PotReactPercent = 1.2f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(VitrihoundClaw.class.getSimpleName());
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
        return VHClaw;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
