package quintessencia.cards.skill;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import quintessencia.cards.BaseCard;
import quintessencia.reagents.AbstractReagent;
import quintessencia.util.CardInfo;
import quintessencia.util.moreutil.CardEnums;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.ReagentListLoader.AllReagents;

@NoCompendium
public class TestCollection extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            TestCollection.class.getSimpleName(),
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.SPECIAL,
            CardEnums.REAGENTCOLOR);

    public static final String ID = makeID(cardInfo.baseId);
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;

    private static final int MAGIC = 198;
    private static final int UPG_MAGIC = -297;


    public TestCollection() {
        super(cardInfo);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        this.setMagic(MAGIC, UPG_MAGIC);

    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    public TestCollection(CardInfo cardInfo) {
        super(cardInfo);
    }

    @Override
    public void onObtainCard() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractReagent r: AllReagents){
            r.GainLoseReagent(magicNumber);
        }
    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new TestCollection();
    }
}