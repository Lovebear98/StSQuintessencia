package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class BottledSlime extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(BottledSlime.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public BottledSlime(){
        this.Regen = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(BottledSlime.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Regen+DESC[1];
    }
    @Override
    public Texture Texture() {
        return Textures.BottledSlime;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }

}
