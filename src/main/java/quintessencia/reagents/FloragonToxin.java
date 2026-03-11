package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.FGToxin;

public class FloragonToxin extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(FloragonToxin.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public FloragonToxin(){
        this.isThrown = true;
        this.targetRequired = true;
        this.Poison = 4;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(FloragonToxin.class.getSimpleName());
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
        return FGToxin;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
