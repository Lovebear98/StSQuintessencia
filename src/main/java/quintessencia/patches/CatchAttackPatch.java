package quintessencia.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import quintessencia.patches.interfaces.BrewedPotionInterface;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage",
        paramtypez = {
                DamageInfo.class
        }
)
public class CatchAttackPatch {
    @SpireInsertPatch(
            rloc = 72
    )
        public static void Insert(AbstractMonster __instance, DamageInfo __info,  int ___damageAmount) {
            ///811 is where we lose HP based on the damage
            ///739 is the first line of the method

            if(___damageAmount > 0){
                if (__info.type.equals(DamageInfo.DamageType.NORMAL)) {
                    if(!AbstractDungeon.player.potions.isEmpty()){
                        for(AbstractPotion pot : AbstractDungeon.player.potions){
                            if(pot instanceof BrewedPotionInterface){
                                ((BrewedPotionInterface) pot).onAttack(___damageAmount);
                            }
                        }
                    }
                }
            }
        }
    }