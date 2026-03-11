package quintessencia;

import basemod.*;
import basemod.abstracts.CustomSavable;
import basemod.devcommands.ConsoleCommand;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import quintessencia.cards.BaseCard;
import quintessencia.patches.interfaces.BrewedPotionInterface;
import quintessencia.patches.rewards.LinkedCardReward;
import quintessencia.patches.rewards.ReagentRewardItem;
import quintessencia.patches.rewards.RewardItemTypeEnumPatch;
import quintessencia.potions.*;
import quintessencia.potions.normalpotion.BloodstoneVial;
import quintessencia.relics.BaseRelic;
import quintessencia.ui.AlchemyMenu;
import quintessencia.ui.AlchemyTopButton;
import quintessencia.ui.OwnedReagentsScreen;
import quintessencia.util.CustomActions.UpdatePotionScaling;
import quintessencia.util.CustomActions.UpdatePotionsAction;
import quintessencia.util.CustomActions.customeffects.RandomizedPotionReagentsEffect;
import quintessencia.util.GeneralUtils;
import quintessencia.util.KeywordInfo;
import quintessencia.util.TextureLoader;
import quintessencia.util.moreutil.CardEnums;
import quintessencia.util.moreutil.ReagentListLoader;
import quintessencia.util.moreutil.ReagentSaveManager;
import quintessencia.util.moreutil.SigilIcon;
import quintessencia.util.moreutil.commands.AddOrLoseReagent;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static basemod.BaseModInit.*;
import static quintessencia.util.moreutil.AlchemyHandler.ClearReactions;
import static quintessencia.util.moreutil.AlchemyHandler.CloseAlchemy;
import static quintessencia.util.moreutil.PotionSlotManager.*;
import static quintessencia.util.moreutil.ReagentSaveManager.LoadReagents;
import static quintessencia.util.moreutil.ReagentSaveManager.SaveReagents;
import static quintessencia.util.moreutil.SozoManager.ChangeSozoProgress;
import static quintessencia.util.moreutil.SozoManager.SozoProgress;
import static quintessencia.util.moreutil.Vars.*;

@SpireInitializer

public class QuintessenciaMod implements
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditRelicsSubscriber,
        PostInitializeSubscriber,
        OnPlayerTurnStartSubscriber,
        PostDrawSubscriber,
        OnStartBattleSubscriber,
        PostEnergyRechargeSubscriber,
        PostPowerApplySubscriber,
        OnPowersModifiedSubscriber,
        StartGameSubscriber,
        PostUpdateSubscriber,
        PostBattleSubscriber,
        PostPotionUseSubscriber,
        OnCardUseSubscriber,
        PotionGetSubscriber,
        PostDeathSubscriber,
        AddAudioSubscriber
{
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }

    public static AlchemyMenu AlchemyPanel;
    public static OwnedReagentsScreen OwnedPanel;

public static Properties QuintessenciaDefaults = new Properties();
    public static final String UNLINK_REAGENTS = "UnlinkReagents";
    public static boolean UnlinkReagents = false;
    public static final String ENABLE_STARTER_KIT = "EnableStarterKit";
    public static boolean EnableStarterKit = true;
    public static final String EXTRA_POTION_SLOT = "ExtraPotionSlot";
    public static boolean ExtraPotionSlot = false;
    public static final String CHANGE_ALCHEMIZE = "ChangeAlchemize";
    public static boolean ChangeAlchemize = false;
    public static final String RELICS_SPAWN = "RelicsSpawn";
    public static boolean RelicsSpawn = false;
    public static final String SHOP_REAGENTS = "ShopReagents";
    public static boolean ShopReagents = true;
    public static final String CLARITY = "Clarity";
    public static boolean Clarity = false;
    //Settings

    public static final Logger logger = LogManager.getLogger(modID);
    //Used to output to the console.


    public static final boolean hasTogether;
    static{
        hasTogether = Loader.isModLoaded("spireTogether");
        logger.info("Checking for Together in Spire!");
        if(hasTogether){
            logger.info("Found Spire Together!");
        }
    }




    private static final String resourcesFolder = "quintessencia";
    //This is where your resource images go.

    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final String ENERGY_ORB2 = characterPath("cardback/energy_orb2.png");
    private static final String ENERGY_ORB_P2 = characterPath("cardback/energy_orb2_p.png");
    private static final String SMALL_ORB2 = characterPath("cardback/small_orb2.png");
    ///Getting the energy orbs for the cards

    private static final String REAGENT_CARD_BG = characterPath("cardback/Reagent.png");
    private static final String REAGENT_CARD_BG_P = characterPath("cardback/Reagent_p.png");
    private static final Color ReagentHue = new Color(147f/255f, 221f/255f, 113f/255f, 1f);
    ///Setting stuff for the reagent cards

    private static final String QUEST_CARD_BG = characterPath("cardback/Quest.png");
    private static final String QUEST_CARD_BG_P = characterPath("cardback/Quest_p.png");
    private static final Color QuestHue = new Color(132f/255f, 119f/255f, 99f/255f, 1f);
    ///Setting stuff for the quest cards

    public static String makeID(String id) {
        return modID + ":" + id;
    }
    ///The modID


    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {

        new QuintessenciaMod();
        ///Adding the color we'll use for our reagents
        BaseMod.addColor(CardEnums.REAGENTCOLOR, ReagentHue,
                REAGENT_CARD_BG, REAGENT_CARD_BG, REAGENT_CARD_BG, ENERGY_ORB2,
                REAGENT_CARD_BG_P, REAGENT_CARD_BG_P, REAGENT_CARD_BG_P, ENERGY_ORB_P2,
                SMALL_ORB2);
        ///Adding the color we'll use for our quests (They were scrapped, but we use this for non-reagent cards now)
        BaseMod.addColor(CardEnums.QUESTCOLOR, QuestHue,
                QUEST_CARD_BG, QUEST_CARD_BG, QUEST_CARD_BG, ENERGY_ORB,
                QUEST_CARD_BG_P, QUEST_CARD_BG_P, QUEST_CARD_BG_P, ENERGY_ORB_P,
                SMALL_ORB);
    }

    public QuintessenciaMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");


        ///Set up our config
        QuintessenciaDefaults.setProperty(ENABLE_STARTER_KIT, "TRUE");
        QuintessenciaDefaults.setProperty(UNLINK_REAGENTS, "FALSE");
        QuintessenciaDefaults.setProperty(EXTRA_POTION_SLOT, "FALSE");
        QuintessenciaDefaults.setProperty(CHANGE_ALCHEMIZE, "FALSE");
        QuintessenciaDefaults.setProperty(RELICS_SPAWN, "TRUE");
        QuintessenciaDefaults.setProperty(SHOP_REAGENTS, "TRUE");
        QuintessenciaDefaults.setProperty(CLARITY, "FALSE");
        try {
            SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
            config.load();
            EnableStarterKit = config.getBool(ENABLE_STARTER_KIT);
            UnlinkReagents = config.getBool(UNLINK_REAGENTS);
            ExtraPotionSlot = config.getBool(EXTRA_POTION_SLOT);
            ChangeAlchemize = config.getBool(CHANGE_ALCHEMIZE);
            RelicsSpawn = config.getBool(RELICS_SPAWN);
            ShopReagents = config.getBool(SHOP_REAGENTS);
            Clarity = config.getBool(CLARITY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void receivePostInitialize() {
        ///If we have Spire Together
        if(hasTogether){
            ///Register with TiS - this is set up for version 7.x, so it's disabled until the update!
            ///TogetherRegister();
        }
        CustomIconHelper.addCustomIcon(SigilIcon.get());
        ///Create the Alchemy Menu
        AlchemyPanel = new AlchemyMenu();
        ///Create the menu that gives vague information about the number of owned reagents
        OwnedPanel = new OwnedReagentsScreen();
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(resourcePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);





        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        ///Add the button that opens the Alchemy Menu
        BaseMod.addTopPanelItem(new AlchemyTopButton());
        ///Add ALL of our reagents to our list
        ReagentListLoader.AddAllReagents();
        ///Set up our save field, AFTER the list is loaded
            BaseMod.addSaveField(makeID("ReagentList"), new CustomSavable<HashMap<String, Integer>>() {
                @Override
                public HashMap<String, Integer> onSave() {
                    ///If the player exists
                    if(AbstractDungeon.player != null){
                        ///Save Reagents to make sure they're all up to date
                        SaveReagents();
                        ///Return that updated list to save
                        return ReagentSaveManager.PlayerOwnedReagents.get(AbstractDungeon.player);
                    }else{
                        ///Otherwise just toss a blank map in
                        return new HashMap<>();
                    }
                }

                @Override
                public void onLoad(HashMap<String, Integer> list) {
                    ///If the list is valid
                    if(list != null){
                        ///AND the player exists
                        if(AbstractDungeon.player != null){
                            ///Update their reagents with the saved list
                            ReagentSaveManager.PlayerOwnedReagents.set(AbstractDungeon.player, list);
                        }
                    }
                    ///Then, either way, Load reagents since the default will cover other cases
                    LoadReagents(list);
                }
            });
        BaseMod.addSaveField(makeID("PotionSlot"), new CustomSavable<Boolean>() {

            @Override
            public Boolean onSave() {
                return hasSlot();
            }

            @Override
            public void onLoad(Boolean aBoolean) {
                if(aBoolean != null){
                    setHasPotionSlot(aBoolean);
                }
            }
        });
        BaseMod.addSaveField(makeID("SozoProgress"), new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                if(AbstractDungeon.player != null){
                    return SozoProgress.get(AbstractDungeon.player);
                }
                return 0;
            }

            @Override
            public void onLoad(Integer integer) {
                if(integer != null){
                    ChangeSozoProgress(integer);
                }else{
                    ChangeSozoProgress(0);
                }
            }
        });

        BaseMod.addPotion(AlchemiaRegia.class, Color.RED.cpy(), Color.BROWN.cpy(), Color.ORANGE.cpy(), AlchemiaRegia.POTION_ID);
        BaseMod.addPotion(Chimerelixis.class, Color.SALMON.cpy(), Color.FIREBRICK.cpy(), Color.RED.cpy(), Chimerelixis.POTION_ID);
        BaseMod.addPotion(VolteaRegia.class, Color.TEAL.cpy(), Color.BLUE.cpy(), Color.SKY.cpy(), VolteaRegia.POTION_ID);
        BaseMod.addPotion(Titanicider.class, Color.GREEN.cpy(), Color.FOREST.cpy(), Color.BLACK.cpy(), Titanicider.POTION_ID);
        BaseMod.addPotion(Opthistoria.class, Color.VIOLET.cpy(), Color.PURPLE.cpy(), Color.YELLOW.cpy(), Opthistoria.POTION_ID);
        BaseMod.addPotion(PhilteredChaos.class, RandomColor(), RandomColor(), RandomColor(), PhilteredChaos.POTION_ID);


        BaseMod.addPotion(BloodstoneVial.class, Color.SALMON.cpy(), Color.RED.cpy(), Color.FIREBRICK.cpy(), BloodstoneVial.POTION_ID);


        BaseMod.registerCustomReward(
                RewardItemTypeEnumPatch.REAGENT,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    ArrayList<RewardItem> rewards = AbstractDungeon.combatRewardScreen.rewards;
                    RewardItem cardReward = null;
                    for (RewardItem r : rewards) {
                        if (r.type == RewardItem.RewardType.CARD) {
                            cardReward = r;
                        }
                    }
                        ReagentRewardItem reagentReward = new ReagentRewardItem();
                        if (cardReward != null) {
                            rewards.remove(cardReward);
                            LinkedCardReward linkedReward = new LinkedCardReward(cardReward.cards, reagentReward);
                            rewards.add(linkedReward);
                            rewards.add(reagentReward);
                        }
                        return reagentReward;

                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null);
                });




            ///Add our console commands
        ConsoleCommand.addCommand("reagent", AddOrLoseReagent.class);// 8


        ModLabeledToggleButton StarterKitButton = new ModLabeledToggleButton(StarterKitText(),
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableStarterKit, settingsPanel, (label) -> {
        }, (button) -> {
            EnableStarterKit = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(ENABLE_STARTER_KIT, EnableStarterKit);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(StarterKitButton);

        ModLabeledToggleButton UnlinkReagentsButton = new ModLabeledToggleButton(UnlinkReagentsText(),
                350.0f, 500.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                UnlinkReagents, settingsPanel, (label) -> {
        }, (button) -> {
            UnlinkReagents = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(UNLINK_REAGENTS, UnlinkReagents);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(UnlinkReagentsButton);

        ModLabeledToggleButton ExtraSlotButton = new ModLabeledToggleButton(ExtraSlotText(),
                350.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                ExtraPotionSlot, settingsPanel, (label) -> {
        }, (button) -> {
            ExtraPotionSlot = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(EXTRA_POTION_SLOT, ExtraPotionSlot);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(ExtraSlotButton);

        ModLabeledToggleButton AlchemizeButton = new ModLabeledToggleButton(AlchemizeText(),
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                ChangeAlchemize, settingsPanel, (label) -> {
        }, (button) -> {
            ChangeAlchemize = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(CHANGE_ALCHEMIZE, ChangeAlchemize);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(AlchemizeButton);

        ModLabeledToggleButton RelicsButton = new ModLabeledToggleButton(RelicsText(),
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                RelicsSpawn, settingsPanel, (label) -> {
        }, (button) -> {
            RelicsSpawn = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(RELICS_SPAWN, RelicsSpawn);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(RelicsButton);

        ModLabeledToggleButton ShopReagentsButton = new ModLabeledToggleButton(ShopText(),
                350.0f, 450.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                ShopReagents, settingsPanel, (label) -> {
        }, (button) -> {
            ShopReagents = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(SHOP_REAGENTS, ShopReagents);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShopReagentsButton);



        ModLabeledToggleButton ShowTipsButton = new ModLabeledToggleButton(TipsText(),
                350.0f, 400.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                Clarity, settingsPanel, (label) -> {
        }, (button) -> {
            Clarity = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Quintessencia", "QuintessenciaConfig", QuintessenciaDefaults);
                config.setBool(CLARITY, Clarity);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShowTipsButton);


        ModLabel ReagentsOwned = new ModLabel(TotalReagentsText(),
                350.0f, 225.0f, Color.YELLOW.cpy(), FontHelper.charDescFont, settingsPanel, (label) -> {
        });
        settingsPanel.addUIElement(ReagentsOwned);
    }





    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(QuintessenciaMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {

    }

    @Override
    public void receiveEditCards() {



        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        if(AbstractDungeon.player != null){
            if(!AbstractDungeon.player.potions.isEmpty()){
                for(AbstractPotion pot : AbstractDungeon.player.potions){
                    if(pot instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) pot).onTurnStart();
                    }
                }
            }
        }
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        if(!AbstractDungeon.player.potions.isEmpty()){
            for(AbstractPotion pot: AbstractDungeon.player.potions){
                if(pot instanceof BrewedPotionInterface){
                    ((BrewedPotionInterface) pot).onDraw();
                }
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        //If this is the first floor
        if(AbstractDungeon.floorNum == 1) {
            ///And the starter kit is enabled
            if(EnableStarterKit){
                ///Add 2 potions worth of random reagents to the player
                AbstractDungeon.effectsQueue.add(new RandomizedPotionReagentsEffect(2));
            }
        }

        ///Clear our reagents at combat start
        AlchemyPanel.ClearReagents();
        ///Hide Alchemy at the start of combat
        CloseAlchemy();
        ClearReactions();
        ///Poke all potions on battle start
        if(AbstractDungeon.player != null){
            if(!AbstractDungeon.player.potions.isEmpty()){
                for(AbstractPotion pot : AbstractDungeon.player.potions){
                    if(pot instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) pot).onStartCombat();
                    }
                }
            }
        }

        if(AbstractDungeon.floorNum == 1) {
            if(Clarity){
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, CombatClarityText(), 3.0f, 3.0f));
            }else{
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, CombatNoClarityText(), 3.0f, 3.0f));
            }
        }

        AbstractDungeon.actionManager.addToTop(new UpdatePotionsAction());
    }

    @Override
    public void receivePostEnergyRecharge() {

    }


    @Override
    public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(source == AbstractDungeon.player){
            if(!AbstractDungeon.player.potions.isEmpty()){
                for(AbstractPotion pot: AbstractDungeon.player.potions){
                    if(pot instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) pot).onApplyPower();
                    }
                }
            }
        }
    }

    @Override
    public void receivePowersModified() {
        
    }


    @Override
    public void receiveStartGame() {
            LoadReagents(ReagentSaveManager.PlayerOwnedReagents.get(AbstractDungeon.player));
            UpdatePotionSlots();
    }



    @Override
    public void receivePostUpdate() {

    }


    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(!AbstractDungeon.player.potions.isEmpty()){
            for(AbstractPotion pot: AbstractDungeon.player.potions){
                if(pot instanceof BrewedPotionInterface){
                    ((BrewedPotionInterface) pot).onEndCombat();
                }

            }
            if(AbstractDungeon.getCurrRoom().eliteTrigger || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite){
                for(AbstractPotion pot: AbstractDungeon.player.potions){
                    if(pot instanceof BrewedPotionInterface){
                        ((BrewedPotionInterface) pot).onBossEliteKill();
                    }
                }
            }
        }


        ///Clear our reagents at combat end
        AlchemyPanel.ClearReagents();
        ///Hide Alchemy after battle
        CloseAlchemy();

        ClearReactions();
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        if(!AbstractDungeon.player.potions.isEmpty()){
            for(AbstractPotion pot: AbstractDungeon.player.potions){
                if(pot instanceof BrewedPotionInterface){
                    ((BrewedPotionInterface) pot).onUsePotion();
                }
            }
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(!AbstractDungeon.player.potions.isEmpty()){
            for(AbstractPotion pot: AbstractDungeon.player.potions){
                if(pot instanceof BrewedPotionInterface){
                    ((BrewedPotionInterface) pot).onUseCard(abstractCard);
                }
            }
        }
    }

    @Override
    public void receivePotionGet(AbstractPotion abstractPotion) {
        ///Update Fume Veil and Brewing Strike
        if(isInCombat()){
            AbstractDungeon.actionManager.addToTop(new UpdatePotionScaling());
        }
    }

    @Override
    public void receivePostDeath() {
        ///Clear our reagents after getting yeeted
        AlchemyPanel.ClearReagents();
        ///Hide alchemy after getting yeeted
        CloseAlchemy();
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(CLINKSOUNDKEY,"quintessencia/audio/GlassGlink.ogg");
    }
}

