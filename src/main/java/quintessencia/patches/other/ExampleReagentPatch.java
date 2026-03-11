package quintessencia.patches.other;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import quintessencia.util.moreutil.ReagentListLoader;


public class ExampleReagentPatch {

    @SpirePatch(clz = ReagentListLoader.class,
            method = "AddExtraReagents")

///This patch postfixes a method that's set up to allow other reagents to be added
    public static class RenderPatch{
        @SpirePostfixPatch
        public static void Postfix(){
            ///This patch fires while creating our list of reagents
            ///It'll follow all our important protocols like preventing duplicate IDs,
            //printing an error if it wasn't added successfully,
            ///Reporting that it's been registered, and being added to our available list

            ///Add a reagent like this:
            /** AddReagent(new YourReagent()); */
        }
    }
}