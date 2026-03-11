package quintessencia.cards.skill;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import quintessencia.QuintessenciaMod;
import quintessencia.cards.BaseCard;
import quintessencia.reagents.AbstractReagent;
import quintessencia.util.CardInfo;
import quintessencia.util.CustomActions.customeffects.RemoveCardEffect;
import quintessencia.util.TextureLoader;
import quintessencia.util.moreutil.CardEnums;

import static quintessencia.QuintessenciaMod.Clarity;
import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.*;
import static quintessencia.util.moreutil.ReagentListLoader.AllReagents;

public class Gather extends BaseCard implements OnObtainCard {
    private final static CardInfo cardInfo = new CardInfo(
            Gather.class.getSimpleName(),
            -2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.SPECIAL,
            CardEnums.REAGENTCOLOR);

    public static final String ID = makeID(cardInfo.baseId);
    public static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;
    private AbstractReagent MyReagent;


    private static final String GDefault = ("quintessencia/cards/skill/Gather.png");
    private static final String GDefaultP = ("quintessencia/cards/skill/Gather_p.png");
    private static final String GAlkahest = ("quintessencia/cards/skill/GatherAlkahest.png");
    private static final String GAlkahestP = ("quintessencia/cards/skill/GatherAlkahest_p.png");
    private static final String GBlas = ("quintessencia/cards/skill/GatherBlas.png");
    private static final String GBlasP = ("quintessencia/cards/skill/GatherBlas_p.png");
    private static final String GConcrete = ("quintessencia/cards/skill/GatherConcrete.png");
    private static final String GConcreteP = ("quintessencia/cards/skill/GatherConcrete_p.png");
    private static final String GRegulus = ("quintessencia/cards/skill/GatherRegulus.png");
    private static final String GRegulusP = ("quintessencia/cards/skill/GatherRegulus_p.png");
    private static final String GHumor = ("quintessencia/cards/skill/GatherHumor.png");
    private static final Texture GHumorP = TextureLoader.getTexture("quintessencia/cards/skill/GatherHumor_p.png");


    private int BaseNum;
    private int UpgNum;

    public Gather(){
        this(AbstractReagent.ReagentType.None, MAGIC, UPG_MAGIC);
    }
    public Gather(AbstractReagent.ReagentType t, int NUM, int UPG_NUM) {
        this(GetTypedReagent(t), NUM, UPG_NUM);
    }
    public Gather(AbstractReagent.ReagentType t) {
        this(t, 2, 1);
    }
    public Gather(AbstractReagent r, int NUM, int UPG_NUM) {
        super(cardInfo);
        MyReagent = r;
        if(MyReagent == null){
            MyReagent = GetRandomReagent(AllReagents);
        }
        BaseNum = NUM;
        UpgNum = UPG_NUM;
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(NUM, UPG_NUM);

        UpdateText();
    }

    private void UpdateText() {
        if(MyReagent != null){
            this.name = MyReagent.reagentName();
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0]+" NL "+Adapt(MyReagent.reagentDesc(BasePotency) + " NL "+OwnedNum(MyReagent));
        }
        initializeDescription();
    }

    private String OwnedNum(AbstractReagent myReagent) {
        String s = "";
        s = EXTENDED_DESCRIPTION[1]+myReagent.NumberOwned+EXTENDED_DESCRIPTION[2];

        return s;
    }


    @Override
    public void upgrade() {
        super.upgrade();
        UpdateText();
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        UpdateImage();
    }

    private void UpdateImage() {
        if(MyReagent != null){
            if(MyReagent.Type() == AbstractReagent.ReagentType.Alkahest){
                loadCardImage(GAlkahest);
                return;
            }
            if(MyReagent.Type() == AbstractReagent.ReagentType.Blas){
                loadCardImage(GBlas);
                return;
            }
            if(MyReagent.Type() == AbstractReagent.ReagentType.Concrete){
                loadCardImage(GConcrete);
                return;
            }
            if(MyReagent.Type() == AbstractReagent.ReagentType.Regulus){
                loadCardImage(GRegulus);
                return;
            }
            if(MyReagent.Type() == AbstractReagent.ReagentType.Humor){
                loadCardImage(GHumor);
                return;
            }
        }
        loadCardImage(GDefault);
    }


    public Gather(CardInfo cardInfo) {
        super(cardInfo);
    }

    @Override
    public void onObtainCard() {
        if(MyReagent != null){
            MyReagent.NumberOwned += magicNumber;
        }else{
            QuintessenciaMod.logger.info("ALKAHEST ERROR: COULDN'T ADD REAGENT.");
        }
        AbstractDungeon.effectsQueue.add(new RemoveCardEffect(this));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new Gather(MyReagent, BaseNum, UpgNum);
    }

    public static String Adapt(String e){
        //Replace all #x colorations with * to highlight text
        String s = e;
        if(Clarity){
            s = s.replace("#yPotency #yCap", "quintessencia:Potency_Cap");
            s = s.replace("#yPotency #yGrowth", "quintessencia:Potency_Growth");
            s = s.replace("#yReacts", "quintessencia:Reacts");
            s = s.replace("#yReact", "quintessencia:React");
            s = s.replace("#yPotency", "quintessencia:Potency");
        }

        s = s.replace("#y", "*");
        s = s.replace("#r", "*");
        s = s.replace("#b", "*");
        s = s.replace("#g", "*");
        s = s.replace("#p", "*");
        return s;
    }
}