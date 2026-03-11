package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.WWMembrane;

public class WidowardenMembrane extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(WidowardenMembrane.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public WidowardenMembrane(){
        this.Block = 6;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(WidowardenMembrane.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Block+DESC[1];
    }
    @Override
    public Texture Texture() {
        return WWMembrane;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
