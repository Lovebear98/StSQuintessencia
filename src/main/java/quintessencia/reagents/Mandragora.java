package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.MandragoraRoot;

public class Mandragora extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(Mandragora.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public Mandragora(){
        this.PotReactPercent = 2.0f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(Mandragora.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }

    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+ConvFloatToPercent(PotReactPercent)+DESC[1];
    }

    @Override
    public String potionDesc(int i) {
        return DESC[2];
    }

    @Override
    public void onUsePotion(AbstractPotion potion) {
        React(potion);
    }

    @Override
    public Texture Texture() {
        return MandragoraRoot;
    }

}
