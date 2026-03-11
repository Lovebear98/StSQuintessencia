package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SWNeedletooth;

public class SoulwinderNeedletooth extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(SoulwinderNeedletooth.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public SoulwinderNeedletooth(){
        this.PotPercent = 0.5f;
        this.PotReactPercent = 2.0f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(SoulwinderNeedletooth.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotPercent)+DESC[2]+DESC[1]+ConvFloatToPercent(PotReactPercent);
    }

    @Override
    public void onHeal(int i, AbstractPotion pot) {
    }

    @Override
    public Texture Texture() {
        return SWNeedletooth;
    }


    ///Converts a float to a displayable %

}
