package quintessencia.util.moreutil;

import com.badlogic.gdx.graphics.Texture;
import quintessencia.reagents.AbstractReagent;

import static quintessencia.QuintessenciaMod.makeID;

public class NoReagent extends AbstractReagent {
    ///This literally exists just to act as a reagent "null"
    @Override
    public ReagentType Type() {
        return ReagentType.None;
    }

    @Override
    public String REAGENT_ID() {
        return makeID(NoReagent.class.getSimpleName());
    }

    @Override
    public String reagentName() {
        return "???";
    }

    @Override
    public String reagentDesc(int i) {
        return "???";
    }

    @Override
    public Texture Texture() {
        return Textures.CancelSize;
    }
}
