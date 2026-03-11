package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.ArrayList;
import java.util.Collections;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.WTobacco;

public class WrithingTobacco extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(WrithingTobacco.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public WrithingTobacco(){
        this.Focus = 2;
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Blas;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(WrithingTobacco.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The number this gets is ALREADY the number you want for potency!
    @Override
    public String reagentDesc(int i) {
        return  DESC[0]+Focus+DESC[1];
    }

    @Override
    public String potionDesc(int i) {
        return DESC[2];
    }

    @Override
    public ArrayList<AbstractOrb> Orbs(int i) {
        return new ArrayList<>(Collections.singleton(new Lightning()));
    }

    @Override
    public Texture Texture() {
        return WTobacco;
    }

}
