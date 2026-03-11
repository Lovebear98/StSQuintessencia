package quintessencia.relics;


import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.cards.AbstractCard;
import quintessencia.cards.attack.BrewingStrike;

import static quintessencia.QuintessenciaMod.RelicsSpawn;
import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.CustomActions.customeffects.MassReplace.ReplaceInDeck;

///This relic is blank, and only checked for in the reward generation
public class ChimerelixisNotes extends BaseRelic {
    private static final String NAME = ChimerelixisNotes.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic

    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    public static final int EffectNum = 2;
    public ChimerelixisNotes() {
        super(ID, NAME, RARITY, SOUND);
        this.tips.add(new CardPowerTip(new BrewingStrike()));
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
    public void onEquip() {
        super.onEquip();
        ReplaceInDeck(AbstractCard.CardTags.STARTER_STRIKE, new BrewingStrike());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}