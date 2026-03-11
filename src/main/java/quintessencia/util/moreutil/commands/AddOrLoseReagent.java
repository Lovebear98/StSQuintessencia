package quintessencia.util.moreutil.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;

public class AddOrLoseReagent extends ConsoleCommand {
    public AddOrLoseReagent() {
        this.requiresPlayer = true;
        this.minExtraTokens = 3;
        this.maxExtraTokens = 3;
        this.simpleCheck = false;

        followup.put("add", DeclareReagent.class);
        followup.put("lose", DeclareReagent.class);
    }

    public void execute(String[] tokens, int depth) {
        errorMsg();
    }

    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
    }
}