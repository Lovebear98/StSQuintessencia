package quintessencia.potions;

import basemod.abstracts.CustomPotion;
import basemod.abstracts.CustomSavable;
import com.google.gson.reflect.TypeToken;
import quintessencia.patches.interfaces.BrewedPotionInterface;
import quintessencia.reagents.AbstractReagent;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static quintessencia.util.moreutil.AlchemyHandler.IDToReagent;

///Brewed potions inherit from this class so that they can be easily identified and their
///ingredients easily referenced
public abstract class BrewedPotion extends CustomPotion  implements CustomSavable<ArrayList<String>>, BrewedPotionInterface {
    ///The ingredients and hook to the interface are innate to this "other" potion class so it's easy to reference them globally
    ///We're still doing basically everything separately inside each potion, but it means we can make a potion
    ///work entirely differently, if we wanted
    public ArrayList<AbstractReagent> Ingredients = new ArrayList<>();
    ///The potion's innate Reactivity growth before reagents
    protected static final int ReactivityGrowth = 100;
    public BrewedPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
        super(name, id, rarity, size, color);
    }


    public String Stats(){
        return "";
    }

    public int maxPotency(){
        return 10;
    }




    /**Save our ingredients when we save*/
    @Override
    public ArrayList<String> onSave() {
        ArrayList<String> list = new ArrayList<>();
        if(!Ingredients.isEmpty()){
            for(AbstractReagent r: Ingredients){
                list.add(r.REAGENT_ID());
            }
        }
        return list;
    }
    ///Load our saved list, then update ourselves
    @Override
    public void onLoad(ArrayList<String> list) {
        ///If we have a list
        if(list != null){
            ///And it isn't empty
            ///Clear the list to new for safety
            this.Ingredients = new ArrayList<>();
            ///For each ID in that list
            for(String s: list){
                ///Convert the ID to a reagent and add it to ourselves

                this.Ingredients.add(IDToReagent(s));
            }
        }else{
            ///Otherwise, use an empty one
            Ingredients = new ArrayList<>();
        }
        ///Then update!
        initializeData();

    }

    @Override
    public Type savedType() {
        return new TypeToken<ArrayList<String>>(){}.getType();
    }
}
