package quintessencia.relics;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import quintessencia.powers.custompowers.BarklightPower;

import static quintessencia.QuintessenciaMod.makeID;


public class RitesOfZapis extends BaseRelic {
    private static final String NAME = RitesOfZapis.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic

    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int EffectNum = 2;


    public RitesOfZapis() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarklightPower(AbstractDungeon.player, EffectNum)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+EffectNum+DESCRIPTIONS[1];
    }

}