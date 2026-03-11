package quintessencia.util.moreutil.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import quintessencia.reagents.AbstractReagent;

import java.util.ArrayList;

import static quintessencia.util.moreutil.ReagentListLoader.*;

public class DeclareReagent extends ConsoleCommand {
    public DeclareReagent() {
        this.requiresPlayer = true;
        this.minExtraTokens = 1;
        this.maxExtraTokens = 2;
        this.simpleCheck = false;
    }



    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ///Get our list of IDs
        ArrayList<String> options = AllReagentIDs();
        ///If the default level has a reagent name on it
        if(options.contains(tokens[depth])) {
            ///If the level after it exists and is a number
            if(tokens.length > depth + 1 && tokens[depth + 1].matches("\\d*")) {
                        ///We're complete
                        ConsoleCommand.complete = true;
                ///Print small numbers
                return ConsoleCommand.smallNumbers();

                ///Otherwise if we go farther than our limit
            } else if(tokens.length > depth + 1) {
                ///Too many tokens
                tooManyTokensError();
            }
            ///If the level doesn't exist or isn't a number but we're still typing
        } else if(tokens.length > depth + 1) {
            tooManyTokensError();
        }
        return options;
    }

    public void execute(String[] tokens, int depth) {
        int countIndex = countIndex(tokens);

        if (tokens.length > countIndex + 1 && ConvertHelper.tryParseInt(tokens[countIndex + 1]) != null) {
            int count = ConvertHelper.tryParseInt(tokens[countIndex + 1], -1);
            String name = tokens[countIndex];
            String mode = tokens[countIndex-1];
            if(count != -1){
                for(AbstractReagent r: AllReagents){
                    if(r.REAGENT_ID().equals(name)){
                        if(mode.equals("add")){
                            DevConsole.log("Added "+count+" "+r.reagentName());
                            IncreaseReagent(r, count);
                        }else{
                            DevConsole.log("Removed "+count+" "+r.reagentName());
                            DecreaseReagent(r, count);
                        }
                        break;
                    }
                }
            }else{
                DevConsole.log("options are:");
                DevConsole.log("* [reagent] [amt]");
            }
        }else{
            ///No number selected
            DevConsole.log("options are:");
            DevConsole.log("* [reagent] [amt]");
        }
    }

    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
    }


    protected int countIndex(String[] tokens) {
        int countIndex = tokens.length - 1;
        while (ConvertHelper.tryParseInt(tokens[countIndex]) != null) {
            countIndex--;
        }
        return countIndex;
    }
    protected int countIndux(String[] tokens) {
        int countIndex = tokens.length - 1;
        while (ConvertHelper.tryParseInt(tokens[countIndex]) != null) {
            countIndex--;
        }
        return countIndex;
    }

    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
}