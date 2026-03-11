package quintessencia.util.moreutil;

import quintessencia.QuintessenciaMod;
import quintessencia.reagents.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

///Handles creating and returning the reagent lists

public class ReagentListLoader {
    ///The list is public and static, so can be easily referenced without patching
    public static final ArrayList<AbstractReagent> AllReagents = new ArrayList<>();
    public static void AddAllReagents(){
        ///We can do this as many times as we need before accessing the list to make sure it's filled.
        if(AllReagents.isEmpty()){
            ///Add our reagents to the list using methods to batch them
            AddReagent(new VitrihoundBloodstone());
            AddReagent(new VitrihoundClaw());
            AddReagent(new VitrihoundFur());
            AddReagent(new VitrihoundTooth());
            AddReagent(new VitrihoundHeart());

            AddReagent(new WidowardenSkull());
            AddReagent(new WidowardenMembrane());
            AddReagent(new WidowardenFur());
            AddReagent(new WidowardenScales());
            AddReagent(new WidowardenStinger());

            AddReagent(new FloragonTalon());
            AddReagent(new FloragonBlood());
            AddReagent(new FloragonMoss());
            AddReagent(new FloragonTailsprout());
            AddReagent(new FloragonToxin());

            AddReagent(new SoulwinderNeedletooth());
            AddReagent(new SoulwinderSnakeoil());
            AddReagent(new SoulwinderGallstone());
            AddReagent(new SoulwinderShedtail());
            AddReagent(new SoulwinderRotdrool());

            AddReagent(new AwakenedLayerskull());
            AddReagent(new BottledSlime());
            AddReagent(new TransientBlood());
            AddReagent(new ConstructOil());

            AddReagent(new ChargedCore());
            AddReagent(new MawTeeth());
            AddReagent(new NemesisSkull());
            AddReagent(new ChampionsChalice());

            AddReagent(new EyesOfFealty());
            AddReagent(new Hexflames());
            AddReagent(new ScrapMetal());
            AddReagent(new HumanTeeth());

            AddReagent(new LouseBile());
            AddReagent(new GremlinTooth());
            AddReagent(new WrithingTobacco());
            AddReagent(new SneckoScales());

            AddReagent(new Saltpetre());
            AddReagent(new CultistHands());
            AddReagent(new ByrdFeather());
            AddReagent(new DarklingCores());

            AddReagent(new Mandragora());
            AddReagent(new GolemBolts());
            AddReagent(new Snakebud());
            AddReagent(new FingerOfAvarice());
            AddReagent(new WormJaw());

            ///Fire this patchable method to easily create expansions,
            /// or modify the contents of the list such as unlockables
            AddExtraReagents();

            ///Sort them all alphabetically
            AllReagents.sort(Comparator.comparing(AbstractReagent::reagentName));
        }
    }

    ///Add a list of reagents to our list
    public static void AddReagents(ArrayList<AbstractReagent> list){
        for(AbstractReagent r: list){
            AddReagent(r);
        }
    }

    ///Add a given reagent to the list
    public static void AddReagent(AbstractReagent r){
        boolean Exists = false;
        ///For each reagent
        for(AbstractReagent r2: AllReagents){
            ///If we already have one with the same ID,
            if(Objects.equals(r2.REAGENT_ID(), r.REAGENT_ID())){
                ///It exists, so we should exist
                Exists = true;
                ///Report that a reagent got skipped over
                QuintessenciaMod.logger.info("QUINTESSENCIA ERROR: Skipped adding ["+r.reagentName()+"], as [" + r2.reagentName() + "] already exists with an identical Reagent_ID() of {" + r.REAGENT_ID() + "}.");
                ///We don't need to check farther, so break out
                break;
            }
        }
        ///If it doesn't exist
        if(!Exists){
            ///Log tht we registered it, so that it can be easily reviewed
            QuintessenciaMod.logger.info("QUINTESSENCIA: Registered reagent named ["+r.reagentName()+"].");
            ///Add it to the list
            AllReagents.add(r);
        }
    }


    ///Get all reagents of a specific type from our list, always sorted alphabetically
    public static ArrayList<AbstractReagent> ReagentList(AbstractReagent.ReagentType type){
        AddAllReagents();
        ArrayList<AbstractReagent> List = new ArrayList<>();
        if(type == AbstractReagent.ReagentType.Alkahest){
            return AllAlkahests();
        }
        if(type == AbstractReagent.ReagentType.Blas){
            return AllBlas();
        }
        if(type == AbstractReagent.ReagentType.Concrete){
            return AllConcretes();
        }
        if(type == AbstractReagent.ReagentType.Regulus){
            return AllRegulus();
        }
        if(type == AbstractReagent.ReagentType.Humor){
            return AllHumors();
        }
        return List;
    }

    ///The methods the above one uses to collect lists
    public static ArrayList<AbstractReagent> AllAlkahests(){
        ArrayList<AbstractReagent> List = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            if(r.Type() == AbstractReagent.ReagentType.Alkahest){
                List.add(r);
            }
        }
        ///Sort the list by name
        List.sort(Comparator.comparing(AbstractReagent::reagentName));
        return List;
    }
    public static ArrayList<AbstractReagent> AllBlas(){
        ArrayList<AbstractReagent> List = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            if(r.Type() == AbstractReagent.ReagentType.Blas){
                List.add(r);
            }
        }
        ///Sort the list by name
        List.sort(Comparator.comparing(AbstractReagent::reagentName));
        return List;
    }
    public static ArrayList<AbstractReagent> AllConcretes(){
        ArrayList<AbstractReagent> List = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            if(r.Type() == AbstractReagent.ReagentType.Concrete){
                List.add(r);
            }
        }
        ///Sort the list by name
        List.sort(Comparator.comparing(AbstractReagent::reagentName));
        return List;
    }
    public static ArrayList<AbstractReagent> AllRegulus(){
        ArrayList<AbstractReagent> List = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            if(r.Type() == AbstractReagent.ReagentType.Regulus){
                List.add(r);
            }
        }
        ///Sort the list by name
        List.sort(Comparator.comparing(AbstractReagent::reagentName));
        return List;
    }
    public static ArrayList<AbstractReagent> AllHumors(){
        ArrayList<AbstractReagent> List = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            if(r.Type() == AbstractReagent.ReagentType.Humor){
                List.add(r);
            }
        }
        ///Sort the list by name
        List.sort(Comparator.comparing(AbstractReagent::reagentName));
        return List;
    }

    ///Lists the reagents by ID names
    public static ArrayList<String> AllReagentIDs(){
        ArrayList<String> result = new ArrayList<>();
        for(AbstractReagent r: AllReagents){
            result.add(r.REAGENT_ID());
        }
        return result;
    }

    ///A place to patch in any other things that we want to add to this process easily!
    private static void AddExtraReagents() {
        QuintessenciaMod.logger.info("QUINTESSENCIA: Beginning to register external, bonus, and optional reagents.");
    }


    public static void IncreaseReagent(AbstractReagent r, int i){
        r.GainLoseReagent(i);
    }
    public static void DecreaseReagent(AbstractReagent r, int i){
        r.GainLoseReagent(-i);
    }
}
