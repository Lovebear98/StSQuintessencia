package quintessencia.relics;


import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Sozu;

import static quintessencia.QuintessenciaMod.makeID;

///This relic is blank, and only checked for in the reward generation
public class Sozo extends BaseRelic {
    private static final String NAME = Sozo.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic

    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public Sozo() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public boolean canSpawn() {
        return false;
    }


    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Sozu.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Sozu.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    this.playLandingSFX();
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}