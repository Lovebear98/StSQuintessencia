package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.VHBlood;

public class VitrihoundBloodstone extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(VitrihoundBloodstone.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public VitrihoundBloodstone(){
        this.PotPercent = 1.0f;
        this.TempHP = 4;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Alkahest;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(VitrihoundBloodstone.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+TempHP+DESC[1];
    }
    @Override
    public Texture Texture() {
        return VHBlood;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {

    }
}
