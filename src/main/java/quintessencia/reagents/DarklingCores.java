package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class DarklingCores extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(DarklingCores.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public DarklingCores(){
        this.TempDexterity = 3;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(DarklingCores.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+TempDexterity+DESC[1];
    }

    @Override
    public Texture Texture() {
        return Textures.DCores;
    }

}
