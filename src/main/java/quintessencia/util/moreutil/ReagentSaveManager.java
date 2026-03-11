package quintessencia.util.moreutil;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import quintessencia.reagents.AbstractReagent;

import java.util.HashMap;

import static quintessencia.util.moreutil.ReagentListLoader.AllReagents;


///Handles saving and loading reagents for the player

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS)
public class ReagentSaveManager {
    ///The list we'll save to the player, pairing the reagent's ID and the amount owned
    public static SpireField<HashMap<String, Integer>> PlayerOwnedReagents = new SpireField<>(HashMap::new);

    ///When we ask the reagents to save, we'll save the whole list at once like this
    public static void SaveReagents(){
        ///Get the player's current list
        HashMap<String, Integer> ToSave = ReagentSaveManager.PlayerOwnedReagents.get(AbstractDungeon.player);
        ///Then for each reagent in our list
        for(AbstractReagent r: AllReagents){
            ///Add it to the list. Since we use the ID, it'll update old entries if we change around widgets.
            ToSave.put(r.REAGENT_ID(), r.NumberOwned);
        }
        if(AbstractDungeon.player != null){
            ///Then set the player's list to that to save it, updating it.
            ReagentSaveManager.PlayerOwnedReagents.set(AbstractDungeon.player, ToSave);
        }
    }

    ///When we load the reagents, use the saved list to set things up again
    public static void LoadReagents(HashMap<String, Integer> list){
        ///For each reagent
        for(AbstractReagent r: AllReagents){
            ///Snag its number from the map if it has it, or pass a 0 if it doesn't! Safety.
            r.NumberOwned = list.getOrDefault(r.REAGENT_ID(), 0);
        }
    }

}