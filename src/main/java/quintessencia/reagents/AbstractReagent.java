package quintessencia.reagents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import quintessencia.QuintessenciaMod;
import quintessencia.patches.interfaces.BrewedPotionInterface;

import java.util.ArrayList;

import static quintessencia.util.moreutil.AlchemyHandler.BasePotency;
import static quintessencia.util.moreutil.Textures.DefaultReagentTexture;

public abstract class AbstractReagent {
    ///The number of the reagent we own at a time
    public int NumberOwned = 0;


    ///Universal constructor to allow patching, and also allow setting a "default" amount.
    public AbstractReagent(int i){
        NumberOwned = i;
    }
    public AbstractReagent(){
        this(0);
    }

    ///It's important to note the base potency for ALL potions is 10! We adjust for what we want out of them from there.
    ///Potency numbers also have a floor at 1, and can't go below that!



    ///The type of reagent it is, which determines which slot it fills
    public abstract ReagentType Type();
    ///The name to identify the reagent. Use makeID()!
    ///We use this to cull duplicates and identify which reagent we're interacting with
    public abstract String REAGENT_ID();

    ///The name displayed to the player
    public abstract String reagentName();

    ///The description of the reagent's effects which passes in a raw potency of 10
    ///When 'Gather' turns the reagent into a card in reagent rewards,
    ///any #y, #b, #g, #r, or #p you use will become * to highlight in card text
    ///You can use [alkahest:PotIcon] to draw the potency icon!
    public abstract String reagentDesc(int i);

    ///The description of the reagent's effects within the potion. You can leave this null for reagents
    ///that only change numbers. Just like above it passes in potency, this time the potion's own potency.
    public String potionDesc(int i){
        return null;
    }

    ///The texture the reagent will show in the UI
    public abstract Texture Texture();

    ///This converts our internal numbers so that they don't get changed by their own potency changes
    ///10 damage listed will be 10 damage added even if the reagent halves potency.
    ///We can feed other numbers through it to get their adjusted amount, too
    public float ScalePot(int amount, int potency){
        ///If we would end up divifing by 0
        if((BasePotency * PotPercent) == 0){
            QuintessenciaMod.logger.info("QUINTESSENCIA ERROR: {"+this.reagentName()+"} has an internal Potency of ZERO. Returning 0 to prevent DIVIDE BY 0.");
            return 0;
        }
        ///If we actually passed in a positive amount
        if(amount > 0){
            ///Return the highest between it and 1 - we have to be AT LEAST 1
            return Math.max((amount * potency) / (BasePotency * PotPercent), 1);
        }
        ///Otherwise just return 0
        return 0;
    }


    ///Apply any non-standard effects of the reagent on potion use!
    public void use(int i, AbstractCreature p, AbstractCreature m){
    }


    ///Draw the texture when it's asked for. They're 32 x 32 px sprites centered in a 45 x 45 px image.
    /// 1.4f * Settings.scale adjusts sizes to match the screen size...not sure why exactly 1.4, but it's public knowledge apparently?
    ///You can also use this to  draw symbols on it if you want - for expansions or reagents in a certain group.
    public void render(SpriteBatch sb, float x, float y){
        ///Make sure we're not trying to draw blanks
        if(Texture() != null){
            sb.draw(Texture(), x, y, Texture().getWidth()*(1.4f * Settings.scale), Texture().getHeight()*(1.4f * Settings.scale));
        }else{
            sb.draw(DefaultReagentTexture, x, y, Texture().getWidth()*(1.4f * Settings.scale), Texture().getHeight()*(1.4f * Settings.scale));
        }
    }

    ///Use a reagent when brewing. You can stick something after the super to
    ///have a reagent do something when it's used
    public void Spend() {
        GainLoseReagent(-1);
    }

    ///When we gain or lose the reagent, do this below - we can animate, adjust the amount, etc.
    public void GainLoseReagent(int i) {
        this.NumberOwned += i;
        if(this.NumberOwned < 0){
            this.NumberOwned = 0;
        }
        if(this.NumberOwned > this.MaximumNum){
            this.NumberOwned = this.MaximumNum;
        }
    }

    /**The variables start here!*/
    ///Reagents can have card tags! It's simpler for outside use than a whole "ReagentTags" system.
    public ArrayList<AbstractCard.CardTags> tags = new ArrayList<>();
    ///The maximum number of this reagent we can own, for special cases
    public int MaximumNum = 99;

    ///The costs to use a potion made from the reagent
    public int HPCost = 0;
    public int EnergyCost = 0;
    public int BlockCost = 0;
    public int DiscardCost = 0;
    public int ExhaustCost = 0;
    public int TempHPCost = 0;
    public int GoldCost = 0;
    ///If the reagent  has a non-combat effect
    public boolean OutOfCombatEffect = false;

    ///Tell the potion if it needs a target or not, and if it's thrown for enemies or not. Default false.
    public boolean isThrown = false;
    public boolean targetRequired = false;

    ///Affects how much the reagent will change the potion's base potency by
    ///1.0f = no change
    protected float PotPercent = 1.0f;

    ///How much to increase the potency CAP by
    protected float PotCapPercent = 1.0f;

    ///How much we react by each time, when we react
    protected float PotReactPercent = 1.0f;

    ///These variables simplify adding generic basegame functions to the potion
    ///The potion handles the conversion based on potency, so add the exact amount you want!
    public int EnergyGain = 0;
    public int Damage = 0;
    public int DamageAll = 0;
    public int Heal = 0;
    public int Regen = 0;
    public int TempHP = 0;
    public int MaxHP = 0;
    public int Block = 0;
    public int Metallicize = 0;
    public int PlatedArmor = 0;
    public int Artifact = 0;
    public int Intangible = 0;
    public int Thorns = 0;
    public int Weak = 0;
    public int Vulnerable = 0;
    public int Vigor = 0;
    public int Strength = 0;
    public int TempStrength = 0;
    public int Dexterity = 0;
    public int TempDexterity = 0;
    public int Focus = 0;
    public int Ritual = 0;
    public int Poison = 0;
    ///Draw/Discard/Exhaust(in hand) are done in two waves, so that effects can do Draw/Discard or
    ///Snecko+Draw and Draw+Snecko
    public int DrawBefore = 0;
    public int DiscardBefore = 0;
    public int ExhaustBefore = 0;
    public int DrawAfter = 0;
    public int DiscardAfter = 0;
    public int ExhaustAfter = 0;
    public int UpgradeInHand = 0;
    public int PlayTopCard = 0;
    public int AddFromDeck = 0;
    public int AddFromDiscard = 0;
    public int DoubleSkill = 0;
    public int DoubleAttack = 0;
    public int DoublePower = 0;
    public int DoubleAll = 0;
    public int Snecko = 0;
    public int OrbSlots = 0;


    ///These specific methods pass in the potion's raw potency, so that the cards/potions/orbs you make
    ///can scale based on potency if you want. You'll need to do some internal math!
    public ArrayList<AbstractCard> AddCards(int i){return new ArrayList<>();}///28
    public ArrayList<AbstractPotion> AddPotions(int i){return new ArrayList<>();}///41
    public ArrayList<AbstractOrb> Orbs(int i){return new ArrayList<>();}///41






    ///These methods allow you to tell the potion to do things at a given time via the potion passed.
    ///The potion we pass in is ALWAYS an AlchemyPotionInterface, but you need to cast it to call those functions.
    ///If you want a reaction, just call Reaction(Object) - if the object is valid, the reaction will happen;

    public void onTurnStart(AbstractPotion potion){

    }
    public void onUseCard(AbstractCard card, AbstractPotion potion){

    }
    public void onTurnEnd(AbstractPotion potion){

    }
    public void onAttack(int i, AbstractPotion potion){

    }
    public void onLoseHP(int i, AbstractPotion potion){

    }
    public void onUsePotion(AbstractPotion potion){

    }
    public void onStanceChange(AbstractPotion potion){

    }

    public void onDraw(AbstractPotion potion){

    }

    public void onEndCombat(AbstractPotion potion){

    }

    public void onApplyPower(AbstractPotion potion){

    }

    public void onBossEliteKill(AbstractPotion potion){

    }

    public void onEvoke(AbstractPotion potion){

    }
    public void onMonsterBlock(int i, AbstractPotion pot){

    }
    public void onPlayerBlock(int i, AbstractPotion pot){

    }
    public void onStartCombat(AbstractPotion pot){

    }
    public void OnPotencyCap(AbstractPotion pot){

    }
    public void onHeal(int i, AbstractPotion potion) {
    }
    ///Modify the personal potency of the potion this reagent is in
    public float PotencyBoost(float i) {
        return i * PotPercent;
    }
    ///Modify the potency cap of the potion this reagent is in
    public float PotencyCapBoost(float i){
        return i * PotCapPercent;
    }
    ///Modify the potency growth of the potion this reagent is in
    public float ReactivityGrowth(float i) {
        return i * PotReactPercent;
    }
    //We do this whenever something launches a reaction for this potion
    public void OnReact(AbstractPotion pot) {
    }
    ///This is what happens when the potion is brewed! It passes in the finished potion
    // so you can check for other reagents, check for potency, scale effects with potency, etc.
    public void onBrew(AbstractPotion p){

    }

    ///Add a tooltip if you need one here!
    public PowerTip BonusTooltip() {
        return null;
    }




    ///Our list of reagent categories, and their traditional use cases
    public enum ReagentType {
        Alkahest, ///Determines the basic, potency-scaling effect of the potion
        Blas,  ///Affects potency numbers
        Concrete, ///Affects potency numbers
        Regulus,  ///Regulates what causes a Reaction and by how much.
        Humor, ///Wildcard, usually unique effects
        None; ///Used only in Gather to prevent the possibility of passing nulls around
        private ReagentType(){

        }
    }

    ///This is just a quick way to force the potion to react without typing the whole process out.
    protected void React(Object o){
        if(o != null){
            if(o instanceof BrewedPotionInterface){
                ((BrewedPotionInterface) o).HookReact();
            }
        }
    }

    ///Just turning a float into the appropriate readable percent
    protected String ConvFloatToPercent(float f){
        int i = (int) (f * 100);
        return i + "%";
    }

    ///Lets us simplify adding to queue! Add to top, bottom, and action queue
    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    ///Use this one for out of combat!
    protected void addToQueue(AbstractGameEffect effect) {
        AbstractDungeon.effectsQueue.add(effect);
    }
}
