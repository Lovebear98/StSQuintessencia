package quintessencia.potions.normalpotion;

import basemod.abstracts.CustomPotion;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.BasePotency;
import static quintessencia.util.moreutil.CardEnums.CONCOCTION;

public class BloodstoneVial extends CustomPotion implements CustomSavable<Integer> {
    public static final String POTION_ID = makeID(BloodstoneVial.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    private int StoredBlood = 0;

    public BloodstoneVial(){
        this(BasePotency);
    }
    public BloodstoneVial(int i) {
        super(NAME, POTION_ID, CONCOCTION, PotionSize.T, PotionColor.WHITE);
        this.StoredBlood = i;
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.SCARLET.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeData();
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, potency));
    }




    public CustomPotion makeCopy() {
        return new BloodstoneVial(StoredBlood);
    }

    public int getPotency(int ascensionLevel) {
        return StoredBlood;
    }

    @Override
    public Integer onSave() {
        return StoredBlood;
    }

    @Override
    public void onLoad(Integer i) {
        if(i != null){
            StoredBlood = i;
        }else{
            StoredBlood = 0;
        }
    }
}
