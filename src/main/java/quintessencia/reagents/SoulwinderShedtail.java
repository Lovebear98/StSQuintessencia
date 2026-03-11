package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SWShedtail;

public class SoulwinderShedtail extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(SoulwinderShedtail.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public SoulwinderShedtail(){
        this.PotPercent = 1.0f;
        this.Vigor = 4;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(SoulwinderShedtail.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+Vigor+DESC[1];
    }
    @Override
    public Texture Texture() {
        return SWShedtail;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
