package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.potions.normalpotion.BloodstoneVial;

import java.util.ArrayList;
import java.util.Collections;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.VHHeart;

public class VitrihoundHeart extends AbstractReagent {
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(makeID(VitrihoundHeart.class.getSimpleName()));
    private static final String[] DESC = potionStrings.DESCRIPTIONS;

    public VitrihoundHeart(){
        ///We want to use the full potency value
        this.PotPercent = 1.0f;
    }

    ///This reagent is a Humor, so we use it in the Humor slot to add quirky effects
    @Override
    public ReagentType Type() {
        return ReagentType.Humor;
    }
    ///The reagent's ID
    @Override
    public String REAGENT_ID() {
        return makeID(VitrihoundHeart.class.getSimpleName());
    }
    ///The reagent's name
    @Override
    public String reagentName() {
        return potionStrings.NAME;
    }
    ///The description in the UI
    @Override
    public String reagentDesc(int i) {
        return DESC[0]+i+DESC[1];
    }
    ///The descriptoin this attaches to the potion
    @Override
    public String potionDesc(int i) {
        return DESC[0]+i+DESC[1];
    }
    ///The image of this reagent
    @Override
    public Texture Texture() {
        return VHHeart;
    }
    ///What potion are we having this one provide?
    @Override
    public ArrayList<AbstractPotion> AddPotions(int i) {
        ///The potion is made using the potency we've passed in
        return new ArrayList<>(Collections.singletonList(new BloodstoneVial(i)));
    }
    ///What extra steps do we take?
    @Override
    public void use(int i, AbstractCreature p, AbstractCreature m) {
        ///We handle the HP loss here since the amount is dynamic, not static
        int e = Math.min(i, (p.currentHealth-1));
        ///We lose the HP so we can store it for later
        addToBot(new LoseHPAction(p, p, e));
    }
}
