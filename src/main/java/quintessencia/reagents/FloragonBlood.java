package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.FGBlood;

public class FloragonBlood extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(FloragonBlood.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public FloragonBlood(){
        this.isThrown = true;
        this.targetRequired = true;
        this.Poison = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(FloragonBlood.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Poison+DESC[1];
    }
    @Override
    public Texture Texture() {
        return FGBlood;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }

}
