package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.Layerskull;

public class AwakenedLayerskull extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(AwakenedLayerskull.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public AwakenedLayerskull(){
        this.Ritual = 1;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(AwakenedLayerskull.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Ritual+DESC[1];
    }
    @Override
    public Texture Texture() {
        return Layerskull;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }

}
