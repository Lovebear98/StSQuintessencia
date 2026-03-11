package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class LouseBile extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(LouseBile.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public LouseBile(){
        this.Dexterity = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(LouseBile.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+Dexterity+DESC[1];
    }

    @Override
    public Texture Texture() {
        return Textures.LouseBile;
    }

}
