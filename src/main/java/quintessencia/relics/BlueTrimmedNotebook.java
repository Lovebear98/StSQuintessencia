package quintessencia.relics;


import static quintessencia.QuintessenciaMod.RelicsSpawn;
import static quintessencia.QuintessenciaMod.makeID;

///This relic is blank, and only checked for in the reward generation
public class BlueTrimmedNotebook extends BaseRelic {
    private static final String NAME = BlueTrimmedNotebook.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic

    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public static final int EffectNum = 2;
    public BlueTrimmedNotebook() {
        super(ID, NAME, RARITY, SOUND);
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
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+EffectNum+DESCRIPTIONS[1];
    }

}