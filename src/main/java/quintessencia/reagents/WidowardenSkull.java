package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.WWSkull;

public class WidowardenSkull extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(WidowardenSkull.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public WidowardenSkull(){
        ///We want to use the full potency value
        this.PotCapPercent = 1.75f;
        this.PotReactPercent = 1.75f;
        this.BlockCost = 12;
    }

    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(WidowardenSkull.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotReactPercent)+DESC[3]
                +DESC[1]+ConvFloatToPercent(PotCapPercent)+DESC[3]
                +DESC[4]+BlockCost+DESC[5];
    }
    @Override
    public Texture Texture() {
        return WWSkull;
    }
    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {
    }
}
