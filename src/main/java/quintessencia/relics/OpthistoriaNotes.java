package quintessencia.relics;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static quintessencia.QuintessenciaMod.RelicsSpawn;
import static quintessencia.QuintessenciaMod.makeID;

///This relic is blank, and only checked for in the reward generation
public class OpthistoriaNotes extends BaseRelic {
    private static final String NAME = OpthistoriaNotes.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic

    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    public static final int EffectNum = 5;

    public OpthistoriaNotes() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = 0;
        descriptionUpdate();
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
        descriptionUpdate();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        this.counter = 0;
        descriptionUpdate();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.counter = 0;
        descriptionUpdate();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public boolean canSpawn() {
        return RelicsSpawn;
    }

    @Override
    public void onUsePotion() {
        super.onUsePotion();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.counter += 1;
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, VigAmount())));
        descriptionUpdate();
    }

    private int VigAmount(){
        return this.counter * EffectNum;
    }
    private int NextVigAmount(){
        return ((this.counter+1) * EffectNum);
    }

    public void descriptionUpdate() {
        this.description = getUpdatedDescription();
        tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+EffectNum+DESCRIPTIONS[1]+(VigAmount()+EffectNum);
    }

}