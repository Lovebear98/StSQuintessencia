package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.stances.CalmStance;
import quintessencia.util.moreutil.Textures;

import static quintessencia.QuintessenciaMod.makeID;

public class TransientBlood extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(TransientBlood.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public TransientBlood(){
    }


    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    @Override
    public String REAGENT_ID() {
        return makeID(TransientBlood.class.getSimpleName());
    }
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    @Override
    public String reagentDesc(int i) {
        return DESC[0];
    }

    @Override
    public String potionDesc(int i) {return DESC[0];}

    @Override
    public Texture Texture() {
        return Textures.TransientBlood;
    }

    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {
        addToBot(new ChangeStanceAction(new CalmStance()));
    }

}
