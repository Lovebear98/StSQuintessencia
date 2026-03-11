package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class ConstructOil extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(ConstructOil.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public ConstructOil(){
        this.Artifact = 1;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(ConstructOil.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Artifact+DESC[1];
    }
    @Override
    public Texture Texture() {
        return Textures.ConstructOil;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }

}
