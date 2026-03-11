package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SBud;

public class Snakebud extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(Snakebud.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public Snakebud(){
        this.EnergyGain = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Concrete;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(Snakebud.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        StringBuilder s = new StringBuilder(DESC[0]);
        for(int e = EnergyGain; e > 0; e -= 1){
            s.append(DESC[1]);
        }
        s.append(DESC[2]);
        return s.toString();
    }

    @Override
    public Texture Texture() {
        return SBud;
    }

}
