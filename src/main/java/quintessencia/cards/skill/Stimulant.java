package quintessencia.cards.skill;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import quintessencia.cards.BaseCard;
import quintessencia.util.CardInfo;
import quintessencia.util.cardmod.StimulantMod;
import quintessencia.util.moreutil.CardEnums;

import static quintessencia.QuintessenciaMod.makeID;

@NoCompendium
public class Stimulant extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            Stimulant.class.getSimpleName(),
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.SPECIAL,
            CardEnums.QUESTCOLOR);

    public static final String ID = makeID(cardInfo.baseId);
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;

    private boolean Plural = true;

    public Stimulant() {
        super(cardInfo);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        this.setMagic(MAGIC, UPG_MAGIC);

        CardModifierManager.addModifier(this, new StimulantMod());
        UpdateText();
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    public Stimulant(CardInfo cardInfo) {
        super(cardInfo);
    }

    @Override
    public void onObtainCard() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void applyPowers() {
        if(Plural){
            if(magicNumber == 1){
                UpdateText();
                Plural = false;
            }
        }else{
            if(magicNumber != 1){
                UpdateText();
                Plural = true;
            }
        }
        super.applyPowers();
    }

    private void UpdateText() {
        if(magicNumber != 1){
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Stimulant();
    }
}