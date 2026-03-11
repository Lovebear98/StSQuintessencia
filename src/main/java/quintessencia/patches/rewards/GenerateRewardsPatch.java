package quintessencia.patches.rewards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.MediumChest;
import com.megacrit.cardcrawl.rewards.chests.SmallChest;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.UnlinkReagents;


public class GenerateRewardsPatch {

    @SpirePatch(clz = CombatRewardScreen.class,
            method = "setupItemReward")


    public static class RenderPatch{
        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen __instance){
            if(AbstractDungeon.getCurrRoom() != null){
                if((AbstractDungeon.getCurrRoom().event == null || ///If the room isn't an event room
                        AbstractDungeon.getCurrRoom().event != null && ///OR it IS an event room -
                                !AbstractDungeon.getCurrRoom().event.noCardsInRewards) && //AND it has card rewards
                        !(AbstractDungeon.getCurrRoom() instanceof TreasureRoom) && ///AND it's not a treasure room
                        !(AbstractDungeon.getCurrRoom() instanceof RestRoom) && ///AND it's not a rest room
                        !(AbstractDungeon.getCurrRoom() instanceof ShopRoom) && ///AND it's not a shop room
                        !((AbstractDungeon.getCurrRoom()) instanceof MonsterRoomBoss) &&
                        !__instance.rewards.isEmpty() ///AND rewards aren't empty somehow
                ){


                    ///Then if we had card rewards in there and are in linked reagents mode,
                    // get the LAST entry to link to
                    if(!UnlinkReagents){
                        ///Look at all the rewards
                        ArrayList<RewardItem> roomreward = new ArrayList<>();
                        for(RewardItem r: __instance.rewards){
                            ///Add card rewards to a list
                            if(r.type == RewardItem.RewardType.CARD){
                                roomreward.add(r);
                            }
                        }
                        ///If there's a reward in the list
                        if(!roomreward.isEmpty()){
                            RewardItem reward = roomreward.get(roomreward.size() - 1);

                            ///Get the cards from it
                            ArrayList<AbstractCard> cards = reward.cards;

                            ///Remove the original reward
                            __instance.rewards.remove(reward);
                            ///Create a reward
                            ReagentRewardItem reagents = new ReagentRewardItem();
                            ///Create a linked reward with it and the original cards
                            LinkedCardReward cardReward = new LinkedCardReward(cards,reagents);
                            ///And add them both to the reward list
                            __instance.rewards.add(cardReward);
                            __instance.rewards.add(reagents);
                        }
                    }
                    ///If we're in unlink mode, just make a reagent
                    if(UnlinkReagents){
                        ///Spawn the rewards alone
                        __instance.rewards.add(new ReagentRewardItem());
                    }
                }
                ///Then, if it's a treasure room
                if(AbstractDungeon.getCurrRoom() instanceof TreasureRoom){
                    ///If it has a chest (paranoia~)
                    if(((TreasureRoom) AbstractDungeon.getCurrRoom()).chest != null){
                        ///Check that chest
                        AbstractChest c = ((TreasureRoom) AbstractDungeon.getCurrRoom()).chest;
                        ///Add a reagent
                        __instance.rewards.add(new ReagentRewardItem());
                        ///Add another one for a medium or small chest
                        if(c instanceof MediumChest || c instanceof SmallChest){
                            __instance.rewards.add(new ReagentRewardItem());
                        }
                        ///Add one more for a small chest
                        if(c instanceof SmallChest){
                            __instance.rewards.add(new ReagentRewardItem());
                        }
                    }
                }
                ///Then, if it's a boss room
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                    ///Offer a reagent reward
                    __instance.rewards.add(new ReagentRewardItem());
                    __instance.rewards.add(new ReagentRewardItem());
                }
            }
        }
    }
}