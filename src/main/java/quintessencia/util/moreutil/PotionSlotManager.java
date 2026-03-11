package quintessencia.util.moreutil;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;

import static quintessencia.QuintessenciaMod.ExtraPotionSlot;

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS
)
public class PotionSlotManager {
    public static SpireField<Boolean> HasPotionSlot = new SpireField<>(() -> false);

    public static boolean hasSlot(){
        if(AbstractDungeon.player != null){
            return HasPotionSlot.get(AbstractDungeon.player);
        }
        return false;
    }
    public static void setHasPotionSlot(boolean b){
        if(AbstractDungeon.player != null){
            HasPotionSlot.set(AbstractDungeon.player, b);
        }
    }


    public static void UpdatePotionSlots(){
        if(ExtraPotionSlot && !hasSlot()){
            AbstractPlayer var10000 = AbstractDungeon.player;
            var10000.potionSlots += 1;
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
            setHasPotionSlot(true);
        }
        if(!ExtraPotionSlot && hasSlot()){
            AbstractPlayer var10000 = AbstractDungeon.player;
            AbstractDungeon.player.potions.remove((AbstractDungeon.player.potionSlots - 1));
            var10000.potionSlots -= 1;
            setHasPotionSlot(false);
        }
    }
}
