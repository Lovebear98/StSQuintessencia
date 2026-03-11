package quintessencia.util.moreutil;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.Sozu;
import quintessencia.relics.Sozo;

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS
)
public class SozoManager {

    public static SpireField<Integer> SozoProgress = new SpireField<>(() -> 0);
    private static final int SozoEndpoint = 7;

    public static void ChangeSozoProgress(int i){
        if(AbstractDungeon.player != null){
            SozoProgress.set(AbstractDungeon.player, SozoProgress.get(AbstractDungeon.player)+i);

            if(AbstractDungeon.player.hasRelic(Sozu.ID)){
                if(SozoProgress.get(AbstractDungeon.player) >= SozoEndpoint){
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), RelicLibrary.getRelic(Sozo.ID).makeCopy());// 23 24
                }
            }
        }
    }
}