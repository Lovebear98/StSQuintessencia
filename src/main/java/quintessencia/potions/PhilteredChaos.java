package quintessencia.potions;


import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import quintessencia.reagents.AbstractReagent;
import quintessencia.util.CustomActions.LoseGoldAction;
import quintessencia.util.CustomActions.LoseTempHPAction;
import quintessencia.util.CustomActions.ReactionSoundAction;
import quintessencia.util.moreutil.CardEnums;

import java.util.ArrayList;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.*;
import static quintessencia.util.moreutil.ReagentListLoader.*;
import static quintessencia.util.moreutil.Vars.isInCombat;

public class PhilteredChaos extends BrewedPotion {
    public static final String POTION_ID = makeID(PhilteredChaos.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    ///How reactive the potion is in the moment
    private int ReactionLevel = 0;

    ///Make a blank potion for previews
    public PhilteredChaos() {
        this(new ArrayList<>());
    }
    ///Make a potion with a list but no pre-set reactivity
    public PhilteredChaos(ArrayList<AbstractReagent> list){
        this(list, 0);
    }
    ///Pass a potion with a set list and reactivity level - we can brew pre-boosted potions!
    public PhilteredChaos(ArrayList<AbstractReagent> list, int i){
        super(NAME, POTION_ID, CardEnums.CONCOCTION, RandomPotionType(), PotionColor.WHITE);
        this.ReactionLevel = i;
        this.Ingredients = list;
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[51], Stats()));
        initializeData();
    }
    ///Initialize data so the potion updates
    public void initializeData() {
        ///It checks this hyper-early before being made, so we need to do this before fixing it
        if(Ingredients == null){
            Ingredients = new ArrayList<>();
        }
        ///Get our potency
        this.potency = getPotency();
        //Update the stats to their generics
        UpdateInfo();
        UpdateCosts();
        ///Set description
        if(Ingredients.isEmpty()){
            ///If we have nothing, give a general description
            this.description = potionStrings.DESCRIPTIONS[0];
        }else{
            ///Add our string that lists our general effects
            StringBuilder s = new StringBuilder(GeneralEffectsDescription());
            ///Then add each reagent's unique description after a line break
            for(AbstractReagent r: Ingredients){
                if(r.potionDesc(potency) != null){
                    s.append(" NL ").append(r.potionDesc(potency));
                }
            }
            s.append(CostsDescription());
            ///And set our description
            this.description = s.toString();
        }
        ///Clear tips to be written again
        this.tips.clear();
        ///Base potion description
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[51], Stats()));
        ///Add a tooltip for up to 5 reagents used in the potion
        int i = 0;
        for(AbstractReagent r: Ingredients){
            if(r.BonusTooltip() != null){
                this.tips.add(r.BonusTooltip());
                i += 1;
            }
            ///Cap at 5 for screen space reasons. This should only matter for highly non-standard potions.
            if(i >= 5){
                break;
            }
        }
    }

    ///Create a description of our costs to add
    private StringBuilder CostsDescription() {
        ///Lil hacky, but check the CanUse here to force everything to update
        StringBuilder s = new StringBuilder("");
        if(PotHPCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotHPCost).append(DESCRIPTIONS[58]);
        }
        if(PotEnergyCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[68]).append(DisplayEnergy(PotEnergyCost));
        }
        if(PotBlockCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotBlockCost).append(DESCRIPTIONS[60]);
        }
        if(PotDiscardCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotDiscardCost).append(DESCRIPTIONS[61]);
        }
        if(PotExhaustCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotExhaustCost).append(DESCRIPTIONS[62]);
        }
        if(PotTempHPCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotTempHPCost).append(DESCRIPTIONS[63]);
        }
        if(PotGoldCost > 0){
            s.append(DESCRIPTIONS[65]).append(DESCRIPTIONS[57]).append(PotGoldCost).append(DESCRIPTIONS[64]);
        }
        return s;
    }

    @Override
    public boolean canUse() {
        ///If the player exists
        if(AbstractDungeon.player != null){
            ///If we're in combat
            if(isInCombat()){
                ///Return if we can afford it
                return OriginalCanUse() && CanPayHP() && CanPayTempHP() && CanPayEnergy() && CanPayBlock() && CanCardsCost() && CanCombatUse() && CanPayGold();
            }else{
                ///Otherwise return if it's an out of combat potion and we can afford it
                return OriginalOutOfCombatCanUse() && OutOfCombatUse() && CanPayHP() && CanPayGold();
            }
        }
        return false;
    }
    ///Using the potion will fire our action that has a boolean. No real change except we can call it separately.
    public void use(AbstractCreature target) {
        ///Define our target and user
        AbstractCreature p = AbstractDungeon.player;
        AbstractCreature m;
        if(target == null){
            m = AbstractDungeon.getRandomMonster();
        }else{
            m = target;
        }
        ///Pay your costs first
        PayCosts(p);
        ///Then use it
        UseProcess(p, m);
        ///And use any unique use effects from the reagents
        for(AbstractReagent r: Ingredients){
            r.use(potency, AbstractDungeon.player, m);
        }
    }
    ///Firing this 'true' is for passive effects, and doesn't trigger 'on potion use' effects.
    ///Reagents can also decide if they want their effects applied on passive trigger
    private void UseProcess(AbstractCreature p, AbstractCreature m) {
        ///Artifact first
        Artifact(p, m);
        ///Energy comes right behind Artifact
        GainEnergy(p, m);
        ///Then layers of defensive things
        Block(p, m);
        Metallicize(p, m);
        PlatedArmor(p, m);
        Intangible(p, m);
        Thorns(p, m);
        ///Now healing things
        MaxHP(p, m);
        TempHP(p, m);
        Heal(p, m);
        Regen(p, m);
        ///Then damage
        DealDamage(p, m);
        DealAllDamage(p, m);
        Poison(p, m);
        ///Then debuffs
        Weak(p, m);
        Vulnerable(p, m);
        ///And buffs
        Vigor(p, m);
        Strength(p, m);
        TempStrength(p, m);
        Dexterity(p, m);
        TempDexterity(p, m);
        Focus(p, m);
        Ritual(p, m);
        ///Add scripted cards to our hand
        AddCards(p, m);
        ///Manipulate our hand for the first round
        DrawBefore(p, m);
        DiscardBefore(p, m);
        ExhaustBefore(p, m);
        ///Apply Snecko in between so we can Snecko-draw or draw-Snecko
        Snecko(p, m);
        ///Then again, so that we can easily pull off effects like Draw->Discard or vice versa
        DrawAfter(p, m);
        DiscardAfter(p, m);
        ExhaustAfter(p, m);
        ///Apply other card-based effects now that we've changed our hand
        AddFromDeck(p, m);
        AddFromDiscard(p, m);
        ///Upgrade our hand now that cards are done moving
        UpgradeHand(p, m);
        ///Then play the top card
        PlayTopCard(p, m);
        ///Apply card doubling
        DoubleAttack(p, m);
        DoubleSkill(p, m);
        DoublePower(p, m);
        DoubleAll(p, m);
        ///Do orbs. Sorry, stances are a lil more finnicky
        AddOrbSlots(p, m);
        ChannelOrbs(p, m);

        ///Add potions to our belt
        AddPotions(p, m);
    }
    ///Make a copy with the exact same ingredients and that maintains our level of reactiveness
    public CustomPotion makeCopy() {
        ///If we're in combat
        if(isInCombat()){
            ///And ingredients isn't null AND is empty
            if(Ingredients != null && Ingredients.isEmpty()){
                ///Make a new list
                ArrayList<AbstractReagent> newlist = new ArrayList<>();
                ///Add a random reagent of each type to it
                newlist.add(GetRandomReagent(AllRegulus()));
                newlist.add(GetRandomReagent(AllAlkahests()));
                newlist.add(GetRandomReagent(AllBlas()));
                newlist.add(GetRandomReagent(AllConcretes()));
                newlist.add(GetRandomReagent(AllHumors()));
                ///Then use that as the foundation of a new potion
                return new PhilteredChaos(newlist, ReactionLevel);
            }
        }
        return new PhilteredChaos(Ingredients, ReactionLevel);
    }
    ///Return the potency somehow
    public int getPotency(int ascensionLevel) {
        float i = (BasePotency);
        for(AbstractReagent r: Ingredients){
            i = r.PotencyBoost(i);
        }
        i += ReactionLevel();
        return (int) Math.min(i, maxPotency());
    }
    /**Get the description for all the general effects*/
    private String GeneralEffectsDescription() {
        boolean WordInFront = false;
        String s = "";
        if(TotalArtifact > 0){
            s += potionStrings.DESCRIPTIONS[1] + TotalArtifact +DESCRIPTIONS[2];
            WordInFront = true;
        }
        if(TotalEnergyGain > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[50] + DisplayEnergy(TotalEnergyGain);
            WordInFront = true;
        }
        if(TotalBlock > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalBlock +DESCRIPTIONS[3];
            WordInFront = true;
        }
        if(TotalMetallicize > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalMetallicize +DESCRIPTIONS[4];
            WordInFront = true;
        }
        if(TotalPlatedArmor > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalPlatedArmor +DESCRIPTIONS[5];
            WordInFront = true;
        }
        if(TotalIntangible > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalIntangible +DESCRIPTIONS[6];
            WordInFront = true;
        }
        if(TotalThorns > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalThorns +DESCRIPTIONS[7];
            WordInFront = true;
        }

        if(TotalMaxHP > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalMaxHP +DESCRIPTIONS[8];
            WordInFront = true;
        }
        if(TotalTempHP > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalTempHP +DESCRIPTIONS[9];
            WordInFront = true;
        }
        if(TotalHeal > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalHeal +DESCRIPTIONS[10];
            WordInFront = true;
        }
        if(TotalRegen > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalRegen +DESCRIPTIONS[11];
            WordInFront = true;
        }

        if(TotalDamage > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[12] + TotalDamage +DESCRIPTIONS[13];
            WordInFront = true;
        }
        if(TotalAllDamage > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[12] + TotalAllDamage +DESCRIPTIONS[14];
            WordInFront = true;
        }
        if(TotalPoison > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[15] + TotalPoison +DESCRIPTIONS[16];
            WordInFront = true;
        }

        if(TotalWeak > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[15] + TotalWeak +DESCRIPTIONS[17];
            WordInFront = true;
        }
        if(TotalVulnerable > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[15] + TotalVulnerable +DESCRIPTIONS[18];
            WordInFront = true;
        }
        if(TotalVigor > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalVigor +DESCRIPTIONS[66];
            WordInFront = true;
        }
        if(TotalStrength > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalStrength +DESCRIPTIONS[19]+DESCRIPTIONS[21];
            WordInFront = true;
        }
        if(TotalTempStrength > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalTempStrength +DESCRIPTIONS[19]+DESCRIPTIONS[20]+DESCRIPTIONS[21];
            WordInFront = true;
        }
        if(TotalDexterity > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalDexterity +DESCRIPTIONS[22]+DESCRIPTIONS[21];
            WordInFront = true;
        }
        if(TotalTempDexterity > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalTempDexterity +DESCRIPTIONS[22]+DESCRIPTIONS[23]+DESCRIPTIONS[24];
            WordInFront = true;
        }
        if(TotalFocus > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalFocus +DESCRIPTIONS[25];
            WordInFront = true;
        }
        if(TotalRitual > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += potionStrings.DESCRIPTIONS[1] + TotalRitual +DESCRIPTIONS[26];
            WordInFront = true;
        }

        ///if(!TotalCardsToAdd.isEmpty()){
        ///
        ///    s += potionStrings.DESCRIPTIONS[27] + CardNames(TotalCardsToAdd) +DESCRIPTIONS[28];
        ///}
        if((TotalDrawAfter + TotalDrawBefore) > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[29] + TotalDrawBefore + "+" + TotalDrawAfter + DESCRIPTIONS[30];
            WordInFront = true;
        }
        if((TotalDiscardBefore + TotalDiscardAfter) > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[31] + TotalDiscardBefore + "+" + TotalDiscardAfter + DESCRIPTIONS[30];
            WordInFront = true;
        }
        if((TotalExhaustBefore + TotalExhaustAfter) > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[32] + TotalExhaustBefore + "+" + TotalExhaustAfter + DESCRIPTIONS[30];
            WordInFront = true;
        }
        if(TotalUpgrade > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[69]+TotalUpgrade+DESCRIPTIONS[70];
            WordInFront = true;
        }
        if(TotalSnecko > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[33];
            WordInFront = true;
        }
        if(TotalAddFromDeck > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[34]+TotalAddFromDeck+DESCRIPTIONS[35]+DESCRIPTIONS[36];
            WordInFront = true;
        }
        if(TotalAddFromDiscard > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[34]+TotalAddFromDiscard+DESCRIPTIONS[35]+DESCRIPTIONS[37];
            WordInFront = true;
        }

        if(TotalPlayTopCard > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[39]+TotalPlayTopCard+DESCRIPTIONS[40];
            WordInFront = true;
        }

        if(TotalDoubleAttack > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[41]+TotalDoubleAttack+DESCRIPTIONS[42]+DESCRIPTIONS[46];
            WordInFront = true;
        }

        if(TotalDoublePower > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[41]+TotalDoubleAttack+DESCRIPTIONS[43]+DESCRIPTIONS[46];
            WordInFront = true;
        }
        if(TotalDoubleSkill > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[41]+TotalDoubleAttack+DESCRIPTIONS[44]+DESCRIPTIONS[46];
            WordInFront = true;
        }
        if(TotalDoubleAll > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[41]+TotalDoubleAttack+DESCRIPTIONS[45]+DESCRIPTIONS[46];
            WordInFront = true;
        }

        if(TotalOrbSlots > 0){
            if(WordInFront){
                s += " NL ";
            }
            s += DESCRIPTIONS[1]+TotalOrbSlots+DESCRIPTIONS[47];
            WordInFront = true;
        }
        ///if(!TotalOrbList.isEmpty()){
        ///    s += DESCRIPTIONS[50]+OrbNames(TotalOrbList)+DESCRIPTIONS[49];
        ///}

        ///if(!TotalPotionsToAdd.isEmpty()){
        ///    s += DESCRIPTIONS[50]+PotionNames(TotalPotionsToAdd)+DESCRIPTIONS[49];
        ///}


        return s;
    }
    private String PotionNames(ArrayList<AbstractPotion> Cards) {
        StringBuilder s = new StringBuilder();
        if(Cards != null){
            if(!Cards.isEmpty()){
                ///If we only have one card
                if(Cards.size() == 1){
                    ///Add the first entry's name

                    s.append(HighlightName(Cards.get(0).name));
                }
                ///If we have exactly two cards
                if(Cards.size() == 2){
                    ///Add the first two entries' names plus an "and"
                    s.append(HighlightName(Cards.get(0).name)).append(DESCRIPTIONS[1]).append(HighlightName(Cards.get(1).name));
                }
                ///If we have 3 or more cards
                if(Cards.size() > 2){
                    ///For each card in the group
                    for(int i = 0; i < (Cards.size()); i+= 1){
                        ///If it's not the last card in the list
                        if(i != Cards.size()-1){
                            ///Add its name plus a ", "
                            s.append(HighlightName(Cards.get(i).name)).append(DESCRIPTIONS[2]);
                        }else{
                            ///otherwise add "and " plus the last card's name
                            s.append(DESCRIPTIONS[3]).append(HighlightName(Cards.get(i).name));
                        }
                    }
                }
            }
        }
        return s.toString();
    }
    private String OrbNames(ArrayList<AbstractOrb> Cards) {
        StringBuilder s = new StringBuilder();
        if(Cards != null){
            if(!Cards.isEmpty()){
                ///If we only have one card
                if(Cards.size() == 1){
                    ///Add the first entry's name

                    s.append(HighlightName(Cards.get(0).name));
                }
                ///If we have exactly two cards
                if(Cards.size() == 2){
                    ///Add the first two entries' names plus an "and"
                    s.append(HighlightName(Cards.get(0).name)).append(DESCRIPTIONS[1]).append(HighlightName(Cards.get(1).name));
                }
                ///If we have 3 or more cards
                if(Cards.size() > 2){
                    ///For each card in the group
                    for(int i = 0; i < (Cards.size()); i+= 1){
                        ///If it's not the last card in the list
                        if(i != Cards.size()-1){
                            ///Add its name plus a ", "
                            s.append(HighlightName(Cards.get(i).name)).append(DESCRIPTIONS[2]);
                        }else{
                            ///otherwise add "and " plus the last card's name
                            s.append(DESCRIPTIONS[3]).append(HighlightName(Cards.get(i).name));
                        }
                    }
                }
            }
        }
        return s.toString();
    }
    private String CardNames(ArrayList<AbstractCard> Cards) {
        StringBuilder s = new StringBuilder();
        if(Cards != null){
            if(!Cards.isEmpty()){
                ///If we only have one card
                if(Cards.size() == 1){
                    ///Add the first entry's name

                    s.append(HighlightName(Cards.get(0).name));
                }
                ///If we have exactly two cards
                if(Cards.size() == 2){
                    ///Add the first two entries' names plus an "and"
                    s.append(HighlightName(Cards.get(0).name)).append(DESCRIPTIONS[1]).append(HighlightName(Cards.get(1).name));
                }
                ///If we have 3 or more cards
                if(Cards.size() > 2){
                    ///For each card in the group
                    for(int i = 0; i < (Cards.size()); i+= 1){
                        ///If it's not the last card in the list
                        if(i != Cards.size()-1){
                            ///Add its name plus a ", "
                            s.append(HighlightName(Cards.get(i).name)).append(DESCRIPTIONS[2]);
                        }else{
                            ///otherwise add "and " plus the last card's name
                            s.append(DESCRIPTIONS[3]).append(HighlightName(Cards.get(i).name));
                        }
                    }
                }
            }
        }
        return s.toString();
    }
    public static String HighlightName(String e){
        ///Checking for the first word
        boolean FirstWord = true;
        ///Getting the card's name
        String s = e;
        ///Prepping to build a string
        StringBuilder s2 = new StringBuilder();
        ///Split the incoming string at spaces
        String[] arr = s.split(" ");
        ///For each word in the name
        for(String str: arr){
            ///If it's the first
            if(FirstWord){
                ///Skip the space
                s2.append("#y").append(str);
                ///And we're no longer getting first words
                FirstWord = false;
                ///otherwise
            }else{
                ///add a space
                s2.append(" #y").append(str);
            }
        }
        return s2.toString();
    }
    public String DisplayEnergy(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e -= 1){
            s.append(DESCRIPTIONS[67]);
        }
        s.append(DESCRIPTIONS[49]);
        return s.toString();
    }
    /**Check all of our reagents to see if we can afford things.*/
    ///Set the variable as a constant so we can access it.
    int PotHPCost = 0;
    protected boolean CanPayHP(){
        if(AbstractDungeon.player != null){
            int TempHPOwned = 0;
            if(isInCombat()){
                TempHPOwned = TempHPField.tempHp.get(AbstractDungeon.player);
            }
            return ((AbstractDungeon.player.currentHealth)+TempHPOwned) >= PotHPCost;
        }
        return false;
    }
    int PotTempHPCost = 0;
    protected boolean CanPayTempHP(){
        if(AbstractDungeon.player != null){
            return TempHPField.tempHp.get(AbstractDungeon.player) >= PotTempHPCost;
        }
        return false;
    }
    int PotGoldCost = 0;
    protected boolean CanPayGold(){
        if(AbstractDungeon.player != null){
            return AbstractDungeon.player.gold >= PotGoldCost;
        }
        return false;
    }
    int PotEnergyCost = 0;
    protected boolean CanPayEnergy(){
        if(AbstractDungeon.player != null){
            return EnergyPanel.totalCount >= PotEnergyCost;
        }
        return false;
    }
    int PotBlockCost = 0;
    protected boolean CanPayBlock(){
        if(AbstractDungeon.player != null){
            return AbstractDungeon.player.currentBlock >= PotBlockCost;
        }
        return false;
    }
    int PotDiscardCost = 0;
    int PotExhaustCost = 0;
    protected boolean CanCardsCost(){
        if(AbstractDungeon.player != null){
            return (AbstractDungeon.player.hand.size() >= (PotDiscardCost + PotExhaustCost));
        }
        return false;
    }


    int i = 0;
    @Override
    public void update() {
        super.update();
        i += 1;
        if(i >= 180){
            UpdateCosts();
            boolean Pie = canUse();
        }
    }

    private void UpdateCosts(){
        ///Reset the costs
        PotHPCost = 0;
        PotBlockCost = 0;
        PotEnergyCost = 0;
        PotGoldCost = 0;
        PotExhaustCost = 0;
        PotDiscardCost = 0;
        PotTempHPCost = 0;
        ///Update them with our list
        if(Ingredients != null){
            for(AbstractReagent r: Ingredients){
                PotHPCost += r.HPCost;
                PotBlockCost += r.BlockCost;
                PotEnergyCost += r.EnergyCost;
                PotGoldCost += r.GoldCost;
                PotExhaustCost += r.ExhaustCost;
                PotDiscardCost += r.DiscardCost;
                PotTempHPCost += r.TempHPCost;
            }
        }
    }


    protected boolean CanCombatUse(){
        if(AbstractDungeon.player != null){
            return isInCombat();
        }
        return false;
    }
    protected boolean OutOfCombatUse(){
        for(AbstractReagent r: Ingredients){
            if(!r.OutOfCombatEffect){
                return false;
            }
        }
        return true;
    }

    ///Pay all the associated costs for this potion
    private void PayCosts(AbstractCreature p){
        if(PotHPCost > 0){
            addToBot(new LoseHPAction(p, p, PotHPCost));
        }
        if(PotTempHPCost > 0){
            addToBot(new LoseTempHPAction(p, p, PotTempHPCost));
        }
        if(PotBlockCost > 0){
            addToBot(new LoseBlockAction(p, p, PotBlockCost));
        }
        if(p instanceof AbstractPlayer){
            if(PotEnergyCost > 0){
                addToBot(new LoseEnergyAction(PotEnergyCost));
            }

            if(PotGoldCost > 0){
                addToBot(new LoseGoldAction(PotGoldCost));
            }

            if(PotDiscardCost > 0){
                addToBot(new DiscardAction(p, p, PotDiscardCost, false));
            }
            if(PotExhaustCost > 0){
                addToBot(new ExhaustAction(p, p, PotExhaustCost, false));
            }
        }
    }

    ///We use the original canUse methods as a basis for usability
    public boolean OriginalCanUse(){
        if (AbstractDungeon.getCurrRoom().event != null && AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain) {// 393 394
            return false;// 395
        } else {
            return AbstractDungeon.getCurrRoom().monsters != null && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && !AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;// 400 401 402
        }
    }
    private boolean OriginalOutOfCombatCanUse(){
        if (AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {// 38 39
            return false;// 40
        } else {
            return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);// 42 43
        }
    }

    /**How reactive the potion is right now, up to a given cap. Each 100 points is 1 potency.*/
    private int ReactionLevel(){
        ///Get how many times the reaction level reaches 100
        float i = ((float) ReactionLevel / 100);
        return (int) i;
    }
    ///Our absolute max potency if we grow to a cap in a turn
    @Override
    public int maxPotency(){
        ///It has a base cap of 5
        float e = BasePotencyCap;
        ///The cap goes up based on the reagents
        for(AbstractReagent r:Ingredients){
            e = r.PotencyCapBoost(e);
        }
        ///Total it
        float b = (BasePotency+e);

        ///Double it for Bark!
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {// 948
            b *= 2;
        }
        ///Return the total
        return (int) b;
    }
    ///Reaction level with every excess 100% shaved off
    private int ReactionLevelRounded(){
        ///If we're at max potency, just say 0%
        if(atMaxPotency()){
            return 0;
        }
        ///Otherwise, return the Reaction Level's remainder after being divided by 100.
        return ReactionLevel%100;
    }
    ///Stats turned into a string for us to be able to read
    @Override
    public String Stats(){
        String s = "";
        s += DESCRIPTIONS[52] + potency + DESCRIPTIONS[53] + maxPotency();
        s += DESCRIPTIONS[54] + ReactionLevelRounded() + DESCRIPTIONS[55];
        s += DESCRIPTIONS[56] + ReactivityGrowth() + DESCRIPTIONS[55];
        return s;
    }
    /**How much each reaction increases potency, at base 1-per-reaction.*/
    private int ReactivityGrowth(){
        float e = ReactivityGrowth;
        for(AbstractReagent r:Ingredients){
            e = r.ReactivityGrowth(e);
        }
        ///We have a FLOOR of 15% to prevent certain reagent combinations from perma-nuking our ability to gain any
        if(e < 20){
            e = 20;
        }
        return (int) e;
    }
    /**What to do when we get a reaction.*/
    public void React(){
        if(isInCombat()){
            ///Asssume we're invalid for on cap effects
            boolean StartedUnderMax = false;
            ///Then if we're not at max potency
            if(!atMaxPotency()){
                ///We become valid
                StartedUnderMax = true;

                this.flash();
                this.ReactionLevel += ReactivityGrowth();
                for(AbstractReagent r:Ingredients){
                    r.OnReact(this);
                }
                ///And correct our info after
                PlaySound();
            }

            ///And if we started under the max AND hit max potency just now
            if(StartedUnderMax && atMaxPotency()){
                for(AbstractReagent r:Ingredients){
                    r.OnPotencyCap(this);
                }
            }
            initializeData();
        }
    }
    public boolean atMaxPotency(){
        return maxPotency() <= potency;
    }
    public void PlaySound(){
        PlaySound = true;
        addToTop(new ReactionSoundAction());
    }
    private boolean isReagentThrown(){
        for(AbstractReagent r: Ingredients){
            if(r.isThrown){
                return true;
            }
        }
        return false;
    }
    private boolean isTargetRequired(){
        for(AbstractReagent r: Ingredients){
            if(r.targetRequired){
                return true;
            }
        }
        return false;
    }
    /**A list of methods we use to standardize the majority of the effects from base-game potions and condense
     * descriptions or effects, and those used to update theme*/
    ///Update all our info first
    private void UpdateInfo(){
        ///Safety~
        if(Ingredients == null){
            Ingredients = new ArrayList<>();
        }
        ///Update our targeting
        isThrown = isReagentThrown();
        targetRequired = isTargetRequired();
        ///Update all our info

        ///Reset them all to 0
        TotalDamage = 0;
        TotalAllDamage = 0;
        TotalHeal = 0;
        TotalRegen = 0;
        TotalTempHP = 0;
        TotalMaxHP = 0;
        TotalBlock = 0;
        TotalMetallicize = 0;
        TotalPlatedArmor = 0;
        TotalArtifact = 0;
        TotalEnergyGain = 0;
        TotalIntangible = 0;
        TotalThorns = 0;
        TotalWeak = 0;
        TotalVulnerable = 0;
        TotalVigor = 0;
        TotalStrength = 0;
        TotalTempStrength = 0;
        TotalDexterity = 0;
        TotalTempDexterity = 0;
        TotalFocus = 0;
        TotalRitual = 0;
        TotalPoison = 0;
        TotalDrawBefore = 0;
        TotalDiscardBefore = 0;
        TotalExhaustBefore = 0;
        TotalDrawAfter = 0;
        TotalDiscardAfter = 0;
        TotalExhaustAfter = 0;
        TotalUpgrade = 0;
        TotalPlayTopCard = 0;
        TotalAddFromDeck = 0;
        TotalAddFromDiscard = 0;
        TotalDoubleSkill = 0;
        TotalDoubleAttack = 0;
        TotalDoublePower = 0;
        TotalDoubleAll = 0;
        TotalSnecko = 0;
        TotalOrbSlots = 0;
        TotalCardsToAdd = new ArrayList<>();
        TotalOrbList = new ArrayList<>();
        TotalPotionsToAdd = new ArrayList<>();

        ///Get the effective amounts based on our potency from our reagents
        for(AbstractReagent e: Ingredients){
            TotalDamage += e.ScalePot(e.Damage, potency);
            TotalAllDamage += e.ScalePot(e.DamageAll, potency);
            TotalHeal += e.ScalePot(e.Heal, potency);
            TotalRegen += e.ScalePot(e.Regen, potency);
            TotalTempHP += e.ScalePot(e.TempHP, potency);
            TotalMaxHP += e.ScalePot(e.MaxHP, potency);
            TotalBlock += e.ScalePot(e.Block, potency);
            TotalMetallicize += e.ScalePot(e.Metallicize, potency);
            TotalPlatedArmor += e.ScalePot(e.PlatedArmor, potency);
            TotalEnergyGain += e.ScalePot(e.EnergyGain, potency);
            TotalArtifact += e.ScalePot(e.Artifact, potency);
            TotalIntangible += e.ScalePot(e.Intangible, potency);
            TotalThorns += e.ScalePot(e.Thorns, potency);
            TotalWeak += e.ScalePot(e.Weak, potency);
            TotalVulnerable += e.ScalePot(e.Vulnerable, potency);
            TotalVigor += e.ScalePot(e.Vigor, potency);
            TotalStrength += e.ScalePot(e.Strength, potency);
            TotalTempStrength += e.ScalePot(e.TempStrength, potency);
            TotalDexterity += e.ScalePot(e.Dexterity, potency);
            TotalTempDexterity += e.ScalePot(e.TempDexterity, potency);
            TotalFocus += e.ScalePot(e.Focus, potency);
            TotalRitual += e.ScalePot(e.Ritual, potency);
            TotalPoison += e.ScalePot(e.Poison, potency);
            TotalDrawBefore += e.ScalePot(e.DrawBefore, potency);
            TotalDiscardBefore += e.ScalePot(e.DiscardBefore, potency);
            TotalExhaustBefore += e.ScalePot(e.ExhaustBefore, potency);
            TotalDrawAfter += e.ScalePot(e.DrawAfter, potency);
            TotalDiscardAfter += e.ScalePot(e.DiscardAfter, potency);
            TotalExhaustAfter += e.ScalePot(e.ExhaustAfter, potency);
            TotalCardsToAdd.addAll(e.AddCards(potency));
            TotalUpgrade += e.ScalePot(e.UpgradeInHand, potency);
            TotalPlayTopCard += e.ScalePot(e.PlayTopCard, potency);
            TotalAddFromDeck += e.ScalePot(e.AddFromDeck, potency);
            TotalAddFromDiscard += e.ScalePot(e.AddFromDiscard, potency);
            TotalDoubleSkill += e.ScalePot(e.DoubleSkill, potency);
            TotalDoubleAttack += e.ScalePot(e.DoubleAttack, potency);
            TotalDoublePower += e.ScalePot(e.DoublePower, potency);
            TotalDoubleAll += e.ScalePot(e.DoubleAll, potency);
            TotalSnecko += e.ScalePot(e.Snecko, potency);
            TotalOrbSlots += e.ScalePot(e.OrbSlots, potency);
            TotalOrbList.addAll(e.Orbs(potency));
            TotalPotionsToAdd.addAll(e.AddPotions(potency));
        }
    }

    int TotalDamage = 0;
    private void DealDamage(AbstractCreature p, AbstractCreature m){
        UpdateInfo();
        if(TotalDamage > 0){
            addToBot(new DamageAction(m, new DamageInfo(p, TotalDamage, DamageInfo.DamageType.THORNS)));
        }
    }
    int TotalAllDamage = 0;
    private void DealAllDamage(AbstractCreature p, AbstractCreature m){
        if(TotalAllDamage > 0){
            addToBot(new DamageAllEnemiesAction((AbstractPlayer) p, TotalAllDamage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    int TotalHeal = 0;
    private void Heal(AbstractCreature p, AbstractCreature m){
        if(TotalHeal > 0){
            addToBot(new HealAction(p, p, TotalHeal));
        }
    }
    int TotalRegen = 0;
    private void Regen(AbstractCreature p, AbstractCreature m){
        if(TotalRegen > 0){
            addToBot(new ApplyPowerAction(p, p, new RegenPower(p, TotalRegen)));
        }
    }
    int TotalTempHP = 0;
    private void TempHP(AbstractCreature p, AbstractCreature m){
        if(TotalTempHP > 0){
            addToBot(new AddTemporaryHPAction(p, p, TotalTempHP));
        }
    }
    int TotalMaxHP = 0;
    private void MaxHP(AbstractCreature p, AbstractCreature m){
        if(TotalMaxHP > 0){
            p.increaseMaxHp(TotalMaxHP, true);
        }
    }
    int TotalBlock = 0;
    private void Block(AbstractCreature p, AbstractCreature m){
        if(TotalBlock > 0){
            addToBot(new GainBlockAction(p, TotalBlock));
        }
    }
    int TotalMetallicize = 0;
    private void Metallicize(AbstractCreature p, AbstractCreature m){
        if(TotalMetallicize > 0){
            addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, TotalMetallicize)));
        }
    }
    int TotalPlatedArmor = 0;
    private void PlatedArmor(AbstractCreature p, AbstractCreature m){
        if(TotalPlatedArmor > 0){
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, TotalPlatedArmor)));
        }
    }
    int TotalArtifact = 0;
    private void Artifact(AbstractCreature p, AbstractCreature m){
        if(TotalArtifact > 0){
            addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, TotalArtifact)));
        }
    }
    int TotalEnergyGain = 0;
    private void GainEnergy(AbstractCreature p, AbstractCreature m){
        if(TotalEnergyGain > 0){
            addToBot(new GainEnergyAction(TotalEnergyGain));
        }
    }
    int TotalIntangible = 0;
    private void Intangible(AbstractCreature p, AbstractCreature m){
        if(TotalIntangible > 0){
            if(p instanceof AbstractPlayer){
                addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, TotalIntangible)));
            }else{
                addToBot(new ApplyPowerAction(p, p, new IntangiblePower(p, TotalIntangible)));
            }
        }
    }
    int TotalThorns = 0;
    private void Thorns(AbstractCreature p, AbstractCreature m){
        if(TotalThorns > 0){
            addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, TotalThorns)));
        }
    }
    int TotalWeak = 0;
    private void Weak(AbstractCreature p, AbstractCreature m){
        if(TotalWeak > 0){
            boolean SourceMonster = !(p instanceof AbstractPlayer);
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, TotalWeak, SourceMonster)));
        }
    }
    int TotalVulnerable = 0;
    private void Vulnerable(AbstractCreature p, AbstractCreature m){
        if(TotalVulnerable > 0){
            boolean SourceMonster = !(p instanceof AbstractPlayer);
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, TotalVulnerable, SourceMonster)));
        }
    }
    int TotalVigor = 0;
    private void Vigor(AbstractCreature p, AbstractCreature m){
        if(TotalVigor > 0){
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, TotalVigor)));
        }
    }
    int TotalStrength = 0;
    private void Strength(AbstractCreature p, AbstractCreature m){
        if(TotalStrength > 0){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, TotalStrength)));
        }
    }
    int TotalTempStrength = 0;
    private void TempStrength(AbstractCreature p, AbstractCreature m){
        if(TotalTempStrength > 0){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, TotalTempStrength)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, TotalTempStrength)));
        }
    }
    int TotalDexterity = 0;
    private void Dexterity(AbstractCreature p, AbstractCreature m){
        if(TotalDexterity > 0){
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, TotalDexterity)));
        }
    }
    int TotalTempDexterity = 0;
    private void TempDexterity(AbstractCreature p, AbstractCreature m){
        if(TotalTempDexterity > 0){
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, TotalTempDexterity)));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, TotalTempDexterity)));
        }
    }
    int TotalFocus = 0;
    private void Focus(AbstractCreature p, AbstractCreature m){
        if(TotalFocus > 0){
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, TotalFocus)));
        }
    }
    int TotalRitual = 0;
    private void Ritual(AbstractCreature p, AbstractCreature m){
        if(TotalRitual > 0){
            boolean PlayerControlled = (p instanceof AbstractPlayer);
            addToBot(new ApplyPowerAction(p, p, new RitualPower(p, TotalRitual, PlayerControlled)));
        }
    }
    int TotalPoison = 0;
    private void Poison(AbstractCreature p, AbstractCreature m){
        if(TotalPoison > 0){
            addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, TotalPoison)));
        }
    }
    int TotalDrawBefore = 0;
    private void DrawBefore(AbstractCreature p, AbstractCreature m){
        if(TotalDrawBefore > 0){
            addToBot(new DrawCardAction(TotalDrawBefore));
        }
    }
    int TotalDiscardBefore = 0;
    private void DiscardBefore(AbstractCreature p, AbstractCreature m){
        if(TotalDiscardBefore > 0){
            addToBot(new DiscardAction(p, p, TotalDiscardBefore, false));
        }
    }
    int TotalExhaustBefore = 0;
    private void ExhaustBefore(AbstractCreature p, AbstractCreature m){
        if(TotalExhaustBefore > 0){
            addToBot(new ExhaustAction(p, p, TotalExhaustBefore, false));
        }
    }
    int TotalDrawAfter = 0;
    private void DrawAfter(AbstractCreature p, AbstractCreature m){
        if(TotalDrawAfter > 0){
            addToBot(new DrawCardAction(TotalDrawAfter));
        }
    }
    int TotalDiscardAfter = 0;
    private void DiscardAfter(AbstractCreature p, AbstractCreature m){
        if(TotalDiscardAfter > 0){
            addToBot(new DiscardAction(p, p, TotalDiscardAfter, false));
        }
    }
    int TotalExhaustAfter = 0;
    private void ExhaustAfter(AbstractCreature p, AbstractCreature m){
        if(TotalExhaustAfter > 0){
            addToBot(new ExhaustAction(p, p, TotalExhaustAfter, false));
        }
    }
    ArrayList<AbstractCard> TotalCardsToAdd = new ArrayList<>();
    private void AddCards(AbstractCreature p, AbstractCreature m){
        if(!TotalCardsToAdd.isEmpty()){
            for(AbstractCard c: TotalCardsToAdd){
                addToBot(new MakeTempCardInHandAction(c));
            }
        }
    }
    int TotalUpgrade = 0;
    private void UpgradeHand(AbstractCreature p, AbstractCreature m){
        if(TotalUpgrade > 0){
            for(int i = TotalUpgrade; i > 0; i --){
                addToBot(new UpgradeRandomCardAction());
            }
        }
    }
    int TotalPlayTopCard = 0;
    private void PlayTopCard(AbstractCreature p, AbstractCreature m){
        if(TotalPlayTopCard > 0){
            for(int i = TotalPlayTopCard; i > 0; i --){
                addToBot(new PlayTopCardAction(m, false));
            }
        }
    }
    int TotalAddFromDeck = 0;
    private void AddFromDeck(AbstractCreature p, AbstractCreature m){
        if(TotalAddFromDeck > 0){
            addToBot(new SeekAction(TotalAddFromDeck));
        }
    }
    int TotalAddFromDiscard = 0;
    private void AddFromDiscard(AbstractCreature p, AbstractCreature m){
        if(TotalAddFromDiscard > 0){
            addToBot(new BetterDiscardPileToHandAction(TotalAddFromDiscard));
        }
    }
    int TotalDoubleSkill = 0;
    private void DoubleSkill(AbstractCreature p, AbstractCreature m){
        if(TotalDoubleSkill > 0){
            addToBot(new ApplyPowerAction(p, p, new BurstPower(p, TotalDoubleSkill)));
        }
    }
    int TotalDoubleAttack = 0;
    private void DoubleAttack(AbstractCreature p, AbstractCreature m){
        if(TotalDoubleAttack > 0){
            addToBot(new ApplyPowerAction(p, p, new DoubleTapPower(p, TotalDoubleAttack)));
        }
    }
    int TotalDoublePower = 0;
    private void DoublePower(AbstractCreature p, AbstractCreature m){
        if(TotalDoublePower > 0){
            addToBot(new ApplyPowerAction(p, p, new AmplifyPower(p, TotalDoublePower)));
        }
    }
    int TotalDoubleAll = 0;
    private void DoubleAll(AbstractCreature p, AbstractCreature m){
        if(TotalDoubleAll > 0){
            addToBot(new ApplyPowerAction(p, p, new DuplicationPower(p, TotalDoubleAll)));
        }
    }
    int TotalSnecko = 0;
    private void Snecko(AbstractCreature p, AbstractCreature m){
        if(TotalSnecko > 0){
            addToBot(new ApplyPowerAction(p, p, new ConfusionPower(p)));
        }
    }
    int TotalOrbSlots = 0;
    private void AddOrbSlots(AbstractCreature p, AbstractCreature m){
        if(TotalOrbSlots > 0){
            int TempOrbSlots = TotalOrbSlots;
            if(AbstractDungeon.player.orbs.size() == 0){
                ///If we have no orb slots, give one for free so that it operates "as expected"
                ///It'll give Defect a janky out to zeroing out his orb slots, but that's fine.
                TempOrbSlots += 1;
            }
            addToBot(new IncreaseMaxOrbAction(TempOrbSlots));
        }
    }
    ArrayList<AbstractOrb> TotalOrbList = new ArrayList<>();
    private void ChannelOrbs(AbstractCreature p, AbstractCreature m){
        if(!TotalOrbList.isEmpty()){
            for(AbstractOrb o: TotalOrbList){
                addToBot(new ChannelAction(o));
            }
        }
    }
    ArrayList<AbstractPotion> TotalPotionsToAdd = new ArrayList<>();
    private void AddPotions(AbstractCreature p, AbstractCreature m){
        if(!TotalPotionsToAdd.isEmpty()){
            for(AbstractPotion p2: TotalPotionsToAdd){
                addToBot(new ObtainPotionAction(p2));
            }
        }
    }

    /**These will tell all of our reagents when something happened so they can tell us what to do*/
    public void onTurnStart(){
        for(AbstractReagent r: Ingredients){
            r.onTurnStart(this);
        }
    }
    public void onUseCard(AbstractCard card){
        for(AbstractReagent r: Ingredients){
            r.onUseCard(card, this);
        }
    }
    public void onTurnEnd(){
        for(AbstractReagent r: Ingredients){
            r.onTurnEnd(this);
        }
    }
    public void onAttack(int i){
        for(AbstractReagent r: Ingredients){
            r.onAttack(i, this);
        }
    }
    public void onLoseHP(int i){
        for(AbstractReagent r: Ingredients){
            r.onLoseHP(i, this);
        }
    }
    public void onUsePotion(){
        for(AbstractReagent r: Ingredients){
            r.onUsePotion(this);
        }
    }
    public void onStanceChange(){
        for(AbstractReagent r: Ingredients){
            r.onStanceChange(this);
        }
    }
    public void onDraw(){
        for(AbstractReagent r: Ingredients){
            r.onDraw(this);
        }
    }
    public void onEndCombat(){
        for(AbstractReagent r: Ingredients){
            r.onEndCombat(this);
        }
    }
    public void onApplyPower(){
        for(AbstractReagent r: Ingredients){
            r.onApplyPower(this);
        }
    }
    public void onBossEliteKill(){
        for(AbstractReagent r: Ingredients){
            r.onBossEliteKill(this);
        }
    }
    public void onEvoke(){
        for(AbstractReagent r: Ingredients){
            r.onEvoke(this);
        }
    }
    public void onHeal(int i){
        for(AbstractReagent r: Ingredients){
            r.onHeal(i, this);
        }
    }

    public void onStartCombat(){
        for(AbstractReagent r: Ingredients){
            r.onStartCombat(this);
        }
    }

    @Override
    public void onPlayerBlock(int i) {
        for(AbstractReagent r: Ingredients){
            r.onPlayerBlock(i, this);
        }
    }

    @Override
    public void onMonsterBlock(int i) {
        for(AbstractReagent r: Ingredients){
            r.onMonsterBlock(i, this);
        }
    }

    @Override
    public void ResetReact() {
        ReactionLevel = 0;
        initializeData();
    }

    @Override
    public void HookReact() {
        React();
    }
}