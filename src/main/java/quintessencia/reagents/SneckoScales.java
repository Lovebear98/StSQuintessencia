package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class SneckoScales extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(SneckoScales.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public SneckoScales(){
        this.DrawBefore = 2;
        this.DiscardAfter = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(SneckoScales.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+DrawBefore+DESC[1]+DiscardAfter+DESC[2];
    }

    @Override
    public Texture Texture() {
        return Textures.SneckoScales;
    }

}
