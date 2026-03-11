package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.GBolts;

public class GolemBolts extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(GolemBolts.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public GolemBolts(){
        this.UpgradeInHand = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Regulus;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(GolemBolts.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+UpgradeInHand+DESC[1];
    }

    @Override
    public Texture Texture() {
        return GBolts;
    }

}
