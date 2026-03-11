package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.VHTooth;

public class VitrihoundTooth extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(VitrihoundTooth.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public VitrihoundTooth(){
        this.PotReactPercent = 0.7f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Regulus;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(VitrihoundTooth.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotReactPercent)+DESC[1];
    }

    @Override
    public String potionDesc(int i) {
        return DESC[2];
    }

    @Override
    public void onHeal(int i, AbstractPotion pot) {
        React(pot);
    }

    @Override
    public Texture Texture() {
        return VHTooth;
    }


    ///Converts a float to a displayable %

}
