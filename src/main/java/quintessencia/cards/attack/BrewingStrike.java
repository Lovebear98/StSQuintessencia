package quintessencia.cards.attack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import quintessencia.cards.BaseCard;
import quintessencia.util.CardInfo;
import quintessencia.util.cardmod.PotionStrikeMod;
import quintessencia.util.moreutil.CardEnums;

import static quintessencia.QuintessenciaMod.makeID;

public class BrewingStrike extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            BrewingStrike.class.getSimpleName(),
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC,
            CardEnums.QUESTCOLOR);

    public static final String ID = makeID(cardInfo.baseId);
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;


    public BrewingStrike() {
        super(cardInfo);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        this.setMagic(MAGIC, UPG_MAGIC);

        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);

        CardModifierManager.addModifier(this, new PotionStrikeMod());
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    public BrewingStrike(CardInfo cardInfo) {
        super(cardInfo);
    }

    @Override
    public void onObtainCard() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new BrewingStrike();
    }
}