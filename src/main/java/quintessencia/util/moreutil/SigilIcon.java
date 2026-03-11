package quintessencia.util.moreutil;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import java.util.ArrayList;
import java.util.List;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.Textures.SigilTex;

public class SigilIcon extends AbstractCustomIcon {
    public static final String ID = makeID("Pot");
    private static SigilIcon singleton;

    public SigilIcon() {super(ID, SigilTex);}
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        ///retVal.add(new TooltipInfo(BaseMod.getKeywordProper(makeID("Crystallize")), BaseMod.getKeywordDescription(makeID("Crystallize"))));
        return retVal;
    }
    public static SigilIcon get() {
        if (singleton == null) {
            singleton = new SigilIcon();
        }
        return singleton;
    }
}