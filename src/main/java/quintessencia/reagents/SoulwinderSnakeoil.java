package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SWnakeoil;

public class SoulwinderSnakeoil extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(SoulwinderSnakeoil.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public SoulwinderSnakeoil(){
        this.PotPercent = 0.5f;
        this.PotCapPercent = 1.5f;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Regulus;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(SoulwinderSnakeoil.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+ConvFloatToPercent(PotPercent)+DESC[2]+DESC[1]+ConvFloatToPercent(PotCapPercent);
    }


    @Override
    public void onHeal(int i, AbstractPotion pot) {
    }

    @Override
    public Texture Texture() {
        return SWnakeoil;
    }


    ///Converts a float to a displayable %

}
