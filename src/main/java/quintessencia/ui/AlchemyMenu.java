package quintessencia.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import quintessencia.potions.BrewedPotion;
import quintessencia.reagents.AbstractReagent;
import quintessencia.relics.FieldAlchemyNotes;
import quintessencia.util.CustomActions.customeffects.BrewPotionEffect;
import quintessencia.util.CustomActions.customeffects.BrewSmokeEffect;

import java.util.ArrayList;
import java.util.Objects;

import static quintessencia.QuintessenciaMod.Clarity;
import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.*;
import static quintessencia.util.moreutil.ReagentListLoader.AllReagents;
import static quintessencia.util.moreutil.ReagentListLoader.ReagentList;
import static quintessencia.util.moreutil.Textures.*;
import static quintessencia.util.moreutil.Vars.isInCombat;


@SuppressWarnings("unused")
public class AlchemyMenu extends ClickableUIElement{
    ///Set our UI strings
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(AlchemyMenu.class.getSimpleName()));
    UIStrings tipStrings = CardCrawlGame.languagePack.getUIString(makeID("Tips"));
    ///Start in Alkahest mode
    private AbstractReagent.ReagentType MenuMode = AbstractReagent.ReagentType.Regulus;

    private AbstractReagent MyAlkahest = null;
    private AbstractReagent MyBlas = null;
    private AbstractReagent MyConcrete = null;
    private AbstractReagent MyRegulus = null;
    private AbstractReagent MyHumor = null;
    ///Whether or not we're hovering the UI and should nullify tips
    public static boolean AlchemyHovered = false;
    private BrewedPotion PreviewPotion = null;


    public AlchemyMenu(){
        super(AlchMenu,x,y,w,h);
        setClickable(true);



        ///Update the page when we make the menu
        ChangePage(0, MenuMode);
        ///Make sure all hitboxes are updated properly
        AdjustAllHitboxes();
    }

    @Override
    public void render(SpriteBatch sb,Color color){

        ///Start rendering
        sb.end();
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();

        ///Update our TempX and GoalX relation
        Tempy = Interpolation.bounce.apply(Tempy, Goaly, 0.2f);



        ///Draw the main UI
        if(MyAlkahest != null && MyBlas != null && MyConcrete != null && MyRegulus != null && MyHumor != null){
            sb.draw(AlchMenu, x, Tempy, w, h);
        }else{
            if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                sb.draw(AlkahestMenu, x, Tempy, w, h);
            }
            if(MenuMode == AbstractReagent.ReagentType.Blas){
                sb.draw(BlasMenu, x, Tempy, w, h);
            }
            if(MenuMode == AbstractReagent.ReagentType.Concrete){
                sb.draw(ConcreteMenu, x, Tempy, w, h);
            }
            if(MenuMode == AbstractReagent.ReagentType.Regulus){
                sb.draw(RegulusMenu, x, Tempy, w, h);
            }
            if(MenuMode == AbstractReagent.ReagentType.Humor){
                sb.draw(HumorMenu, x, Tempy, w, h);
            }
        }

        ///Draw the components
        if(GetReagent(1) != null){
            Objects.requireNonNull(GetReagent(1)).render(sb, SlotColumn1, SlotRow1);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(1)).NumberOwned, SlotColumn1, SlotRow1+(BetweenSlotsw * 2));
        }
        if(GetReagent(2) != null){
            Objects.requireNonNull(GetReagent(2)).render(sb, SlotColumn2, SlotRow1);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(2)).NumberOwned, SlotColumn2, SlotRow1+(BetweenSlotsw * 2));
        }
        if(GetReagent(3) != null){
            Objects.requireNonNull(GetReagent(3)).render(sb, SlotColumn3, SlotRow1);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(3)).NumberOwned, SlotColumn3, SlotRow1+(BetweenSlotsw * 2));
        }
        if(GetReagent(4) != null){
            Objects.requireNonNull(GetReagent(4)).render(sb, SlotColumn1, SlotRow2);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(4)).NumberOwned, SlotColumn1, SlotRow2+(BetweenSlotsw * 2));
        }
        if(GetReagent(5) != null){
            Objects.requireNonNull(GetReagent(5)).render(sb, SlotColumn2, SlotRow2);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(5)).NumberOwned, SlotColumn2, SlotRow2+(BetweenSlotsw * 2));
        }
        if(GetReagent(6) != null){
            Objects.requireNonNull(GetReagent(6)).render(sb, SlotColumn3, SlotRow2);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(6)).NumberOwned, SlotColumn3, SlotRow2+(BetweenSlotsw * 2));
        }
        if(GetReagent(7) != null){
            Objects.requireNonNull(GetReagent(7)).render(sb, SlotColumn1, SlotRow3);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(7)).NumberOwned, SlotColumn1, SlotRow3+(BetweenSlotsw * 2));
        }
        if(GetReagent(8) != null){
            Objects.requireNonNull(GetReagent(8)).render(sb, SlotColumn2, SlotRow3);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(8)).NumberOwned, SlotColumn2, SlotRow3+(BetweenSlotsw * 2));
        }
        if(GetReagent(9) != null){
            Objects.requireNonNull(GetReagent(9)).render(sb, SlotColumn3, SlotRow3);
            ShowReagentAmount(sb, Objects.requireNonNull(GetReagent(9)).NumberOwned, SlotColumn3, SlotRow3+(BetweenSlotsw * 2));
        }

        ///Draw the selected components
        if(MyAlkahest != null){
            MyAlkahest.render(sb, ReagentColumn1, SlotRow1);
        }
        if(MyBlas != null){
            MyBlas.render(sb, ReagentColumn2, SlotRow1);
        }
        if(MyConcrete != null){
            MyConcrete.render(sb, ReagentColumn3, SlotRow1);
        }
        if(MyRegulus != null){
            MyRegulus.render(sb, ReagentColumn1, SlotRow2);
        }
        if(MyHumor != null){
            MyHumor.render(sb, ReagentColumn3, SlotRow2);
        }

        if(Clarity) {
            sb.draw(Tips, ReagentColumn1, Buttonsy);
            FontHelper.renderFont(sb, FontHelper.blockInfoFont, "?", ReagentColumn1 + (Tipw * 0.1f), Buttonsy + (Tiph * 0.9f), Color.YELLOW.cpy());
        }

        ///Draw the potion bottle on the finished menu if we're at full reagents
        if(ReagentsFull()){
            sb.draw(FinishedPot, ReagentColumn2, SlotRow2, Slotw, Sloth);
        }

        ///Write the page number
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, PageCapString(), PageNumx, PageNumy, Color.WHITE.cpy());


        ///Update our x/y positions
        UpdatePositions();
        ///Draw the hitbox
        AdjustAllHitboxes();
        RenderAllHitboxes(sb);
    }

    private void ShowReagentAmount(SpriteBatch sb, int i, float mx, float my) {
        Color MyColor;
        if(i > 0){
            MyColor = Color.LIME.cpy();
        }else{
            MyColor = Color.RED.cpy();
        }
        FontHelper.renderFont(sb, FontHelper.blockInfoFont, String.valueOf(i), mx, my, MyColor);
    }

    @Override
    protected void onHover(){
        if(!AbstractDungeon.isScreenUp && DisplayAlchemy){
            AlchemyHovered = true;

            boolean FoundTooltip = false;
            if(Slot1.hovered){
                if(GetReagent(1) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(1)).reagentName(), Objects.requireNonNull(GetReagent(1)).reagentDesc(BasePotency));

                    FoundTooltip = true;
                }
            }
            if(Slot2.hovered){
                if(GetReagent(2) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(2)).reagentName(), Objects.requireNonNull(GetReagent(2)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot3.hovered){
                if(GetReagent(3) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(3)).reagentName(), Objects.requireNonNull(GetReagent(3)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot4.hovered){
                if(GetReagent(4) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(4)).reagentName(), Objects.requireNonNull(GetReagent(4)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot5.hovered){
                if(GetReagent(5) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(5)).reagentName(), Objects.requireNonNull(GetReagent(5)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot6.hovered){
                if(GetReagent(6) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(6)).reagentName(), Objects.requireNonNull(GetReagent(6)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot7.hovered){
                if(GetReagent(7) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(7)).reagentName(), Objects.requireNonNull(GetReagent(7)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot8.hovered){
                if(GetReagent(8) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(8)).reagentName(), Objects.requireNonNull(GetReagent(8)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Slot9.hovered){
                if(GetReagent(9) != null){
                    RenderTooltip(Objects.requireNonNull(GetReagent(9)).reagentName(), Objects.requireNonNull(GetReagent(9)).reagentDesc(BasePotency));
                    FoundTooltip = true;
                }
            }
            if(Clarity && TipBox.hovered){
                RenderTooltip(TipTitle(), TipDesc());
            }
            if(Reagent1.hovered){
                if(MyAlkahest != null){
                    RenderTooltip(MyAlkahest.reagentName(), MyAlkahest.reagentDesc(BasePotency));
                    FoundTooltip = true;
                }else{
                    RenderTooltip(uiStrings.TEXT[10], uiStrings.TEXT[11]);
                }
            }
            if(Reagent2.hovered){
                if(MyBlas != null){
                    RenderTooltip(MyBlas.reagentName(), MyBlas.reagentDesc(BasePotency));
                    FoundTooltip = true;
                }else{
                    RenderTooltip(uiStrings.TEXT[12], uiStrings.TEXT[13]);
                }
            }
            if(Reagent3.hovered){
                if(MyConcrete != null){
                    RenderTooltip(MyConcrete.reagentName(), MyConcrete.reagentDesc(BasePotency));
                    FoundTooltip = true;
                }else{
                    RenderTooltip(uiStrings.TEXT[14], uiStrings.TEXT[15]);
                }
            }
            if(Reagent4.hovered){
                if(MyRegulus != null){
                    RenderTooltip(MyRegulus.reagentName(), MyRegulus.reagentDesc(BasePotency));
                    FoundTooltip = true;
                }else{
                    RenderTooltip(uiStrings.TEXT[16], uiStrings.TEXT[17]);
                }
            }
            if(Reagent5.hovered){
                if(MyHumor != null){
                    RenderTooltip(MyHumor.reagentName(), MyHumor.reagentDesc(BasePotency));
                    FoundTooltip = true;
                }else{
                    RenderTooltip(uiStrings.TEXT[18], uiStrings.TEXT[19]);
                }
            }
            if(Result.hovered){
                ///If our reagents are full while we're hovering this
                if(ReagentsFull()){
                    //Update the potion
                    if(PreviewPotion == null){
                        PreviewPotion = CurrentPotion();
                    }
                    ///~Paranoia~
                    //And it it's not null
                    if(PreviewPotion != null){
                        ///Show its tooltip
                        RenderTooltip(uiStrings.TEXT[20], PreviewPotion.description + " NL " + PreviewPotion.Stats());
                    }
                }
            }
            if(LeftArrow.hovered){
                RenderTooltip(uiStrings.TEXT[0], uiStrings.TEXT[1]);
                FoundTooltip = true;
            }
            if(Cancel.hovered){
                RenderTooltip(uiStrings.TEXT[2], uiStrings.TEXT[3]);
                FoundTooltip = true;
            }
            if(RightArrow.hovered){
                RenderTooltip(uiStrings.TEXT[4], uiStrings.TEXT[5]);
                FoundTooltip = true;
            }
            if(Confirm.hovered){
                RenderTooltip(uiStrings.TEXT[6], BrewText());
                FoundTooltip = true;
            }
            ///if(!FoundTooltip){
            ///    RenderTooltip(uiStrings.TEXT[8], uiStrings.TEXT[9]);
            ///}
            if(!Result.hovered){
                PreviewPotion = null;
            }
        }
    }

    private String BrewText() {
        if(AbstractDungeon.player != null){
            if(AbstractDungeon.player.hasRelic(FieldAlchemyNotes.ID)){
                return uiStrings.EXTRA_TEXT[2];
            }
        }
        return uiStrings.EXTRA_TEXT[1];
    }


    @Override
    public void update() {
        super.update();

    }

    @Override
    protected void onUnhover(){
        AlchemyHovered = false;
    }
    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(x + (x*0.1f), y-(y * 0.1f), Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
        UpdateAllHitboxes();
    }

    @Override
    protected void onClick(){
        ///If we're not in a screen AND in visible menu mode, for safety
        if(!AbstractDungeon.isScreenUp && DisplayAlchemy){
            if(LeftArrow.hovered){
                ChangePage(-1, MenuMode);
            }
            if(Cancel.hovered){
                Cancel();
            }
            if(RightArrow.hovered){
                ChangePage(1, MenuMode);
            }
            if(Slot1.hovered){
                ///If the thing EXISTS
                if(GetReagent(1) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(1)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(1);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(1);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(1);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(1);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(1);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot2.hovered){
                if(GetReagent(2) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(2)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(2);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(2);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(2);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(2);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(2);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot3.hovered){
                if(GetReagent(3) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(3)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(3);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(3);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(3);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(3);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(3);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot4.hovered){
                if(GetReagent(4) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(4)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(4);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(4);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(4);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(4);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(4);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot5.hovered){
                if(GetReagent(5) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(5)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(5);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(5);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(5);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(5);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(5);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot6.hovered){
                if(GetReagent(6) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(6)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(6);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(6);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(6);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(6);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(6);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot7.hovered){
                if(GetReagent(7) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(7)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(7);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(7);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(7);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(7);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(7);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot8.hovered){
                if(GetReagent(8) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(8)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(8);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(8);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(8);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(8);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(8);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Slot9.hovered){
                if(GetReagent(9) != null){
                    ///If we have enough
                    if(Objects.requireNonNull(GetReagent(9)).NumberOwned > 0){
                        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
                            MyAlkahest = GetReagent(9);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Blas){
                            MyBlas = GetReagent(9);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Concrete){
                            MyConcrete = GetReagent(9);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Regulus){
                            MyRegulus = GetReagent(9);
                        }
                        if(MenuMode == AbstractReagent.ReagentType.Humor){
                            MyHumor = GetReagent(9);
                        }
                        PlayReagentSound();
                        ModeAdvance(false);
                    }
                }
            }
            if(Clarity && TipBox.hovered){
                if(TipNum == TipCap){
                    TipNum = 0;
                }else{
                    TipNum += 1;
                }
            }
            if(Reagent1.hovered){
                ///If we're not already on the matching mode
                if(MenuMode != AbstractReagent.ReagentType.Alkahest){
                    ///Change modes and play the map sound
                    MenuMode = AbstractReagent.ReagentType.Alkahest;
                    PlayPageSound();
                    ///Update the page when we change mode
                    ChangePage(0, MenuMode);
                }
            }
            if(Reagent2.hovered){
                if(MenuMode != AbstractReagent.ReagentType.Blas){
                    MenuMode = AbstractReagent.ReagentType.Blas;
                    PlayPageSound();
                    ///Update the page when we change mode
                    ChangePage(0, MenuMode);
                }
            }
            if(Reagent3.hovered){
                if(MenuMode != AbstractReagent.ReagentType.Concrete){
                    MenuMode = AbstractReagent.ReagentType.Concrete;
                    PlayPageSound();
                    ///Update the page when we change mode
                    ChangePage(0, MenuMode);
                }

            }
            if(Reagent4.hovered){
                if(MenuMode != AbstractReagent.ReagentType.Regulus){
                    MenuMode = AbstractReagent.ReagentType.Regulus;
                    PlayPageSound();
                    ///Update the page when we change mode
                    ChangePage(0, MenuMode);
                }
            }
            if(Reagent5.hovered){
                if(MenuMode != AbstractReagent.ReagentType.Humor){
                    MenuMode = AbstractReagent.ReagentType.Humor;
                    PlayPageSound();
                    ///Update the page when we change mode
                    ChangePage(0, MenuMode);
                }
            }

            if(Confirm.hovered){
               if(CanBrew()){
                   Brew();
               }else{
                   if(isInCombat() && isTurn()){
                       if(!AbstractDungeon.actionManager.isEmpty()){
                           for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                               if(action instanceof TalkAction){
                                   return;
                               }
                           }
                       }
                       AbstractDungeon.actionManager.addToTop(new TalkAction(true, uiStrings.EXTRA_TEXT[0], 1.2F, 1.0F));
                       PlayReagentSound();
                   }
               }
            }

        }
    }
    ///Advance to the next menu mode
    private void ModeAdvance(boolean clearing){
        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
            MenuMode = AbstractReagent.ReagentType.Blas;
            ///We only stop if the reagent we wheel to is empty
            if(MyBlas == null || clearing){
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Blas){
            MenuMode = AbstractReagent.ReagentType.Concrete;
            if(MyConcrete == null || clearing){
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Concrete){
            MenuMode = AbstractReagent.ReagentType.Humor;
            if(MyHumor == null || clearing){
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Humor){
            MenuMode = AbstractReagent.ReagentType.Regulus;
            if(MyRegulus == null || clearing || AllReagentsSelected()){
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Regulus){
            MenuMode = AbstractReagent.ReagentType.Alkahest;
            if(MyAlkahest == null || clearing){
                return;
            }
        }
        ///It loops back around to make sure it can loop from R to R if it has to, since
        ///all conditions will return to escape the loop
        ModeAdvance(clearing);
    }

    private boolean AllReagentsSelected() {
        return MyRegulus != null && MyAlkahest != null && MyBlas != null && MyConcrete != null && MyHumor != null;
    }

    private void PlayPageSound() {
        CardCrawlGame.sound.play("DECK_OPEN", 0.4f);
    }

    private void PlayReagentSound() {
        CardCrawlGame.sound.play("MAP_OPEN", 0.4f);
    }

    private void ChangePage(int i, AbstractReagent.ReagentType t) {
        int StartingPageNum = PageNum;
        int Min = 1;
        int Max = PageCap(t);

        ///Set our int that we'll use in the page select text
        MaxNum = String.valueOf(Max);


        ///Change the page number
        PageNum += i;
        ///And wrap it if it goes past our boundaries
        if(PageNum < Min){
            PageNum = Max;
        }
        if(PageNum > Max){
            PageNum = Min;
        }
        ///Then if it changed
        if(PageNum != StartingPageNum){
            ///Make a paper noise
            PlayPageSound();
        }
    }

    private String MaxNum = String.valueOf(1);
    private String PageCapString(){
        return PageNum + " / " + MaxNum;
    }
    private int PageCap(AbstractReagent.ReagentType type){
        ///Page Number
        int i = 1;
        ///Number of a given reagent
        int e = 0;
        ///For all reagents we have
        for(AbstractReagent r: AllReagents){
            ///If the type matches
            if(r.Type() == type){
                ///Add 1
                e += 1;
            }
        }
        ///If we found at least one match
        if(e > 0){
            ///Divide by 9 for the page number
            i = e/9;
            ///Then if there are any leftovers
            if(e%9 > 0){
                ///Add one more page
                i += 1;
            }
        }
        ///Always return at least one page minimum
        return Math.max(i, 1);
    }
    public void Toggley(){
        Toggley(!DisplayAlchemy);
    }
    public void Toggley(boolean b){
        DisplayAlchemy = b;
        if(DisplayAlchemy){
            Goaly = y;
            PlayReagentSound();
        }else{
            Goaly = hidey;
        }
        ///Update the page when we show or hide the menu
        ChangePage(0, MenuMode);
    }
    private void Cancel(){
        if(MenuMode == AbstractReagent.ReagentType.Alkahest){
            if(MyAlkahest != null){
                MyAlkahest = null;
                PlayReagentSound();
                ModeAdvance(true);
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Blas){
            if(MyBlas != null){
                MyBlas = null;
                PlayReagentSound();
                ModeAdvance(true);
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Concrete){
            if(MyConcrete != null){
                MyConcrete = null;
                PlayReagentSound();
                ModeAdvance(true);
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Regulus){
            if(MyRegulus != null){
                MyRegulus = null;
                PlayReagentSound();
                ModeAdvance(true);
                return;
            }
        }
        if(MenuMode == AbstractReagent.ReagentType.Humor){
            if(MyHumor != null){
                MyHumor = null;
                PlayReagentSound();
                ModeAdvance(true);
                return;
            }
        }
        Toggley(false);
    }

    ///Return if we have all 5 components AND an empty potion slot
    private boolean CanBrew(){
        ///Since we can only add things we have more than 0 of, we just need to make sure we have all 5 slots filled
        return ReagentsFull() && HasSlots() && CanAfford() && isTurn();
    }

    ///Return if we have all 5 components
    private boolean ReagentsFull() {
    return (MyAlkahest != null && MyBlas != null && MyConcrete != null && MyRegulus != null && MyHumor != null);
    }

    ///We can't brew if can't fit the potion
    private boolean HasSlots() {
        for(AbstractPotion po : AbstractDungeon.player.potions){
            ///If we have an empty slot, say yes
            if(po instanceof PotionSlot){
                ///UNLESS we have Sozu
                return true;
            }
        }
        return false;
    }

    private boolean CanAfford(){
        if(AbstractDungeon.player != null){
            if(AbstractDungeon.player.hasRelic(FieldAlchemyNotes.ID)){
                return true;
            }
            return EnergyPanel.totalCount >= 1;
        }
        return false;
    }

    private boolean isTurn(){
        return !AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.overlayMenu.endTurnButton.enabled;
    }

    private void Brew(){
        MenuMode = AbstractReagent.ReagentType.Regulus;
        if(AbstractDungeon.player != null){
            AbstractDungeon.effectsQueue.add(new BrewSmokeEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));

            if(!AbstractDungeon.player.hasRelic(FieldAlchemyNotes.ID)){
                AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
            }
        }



        ///Java doesn't like handling nulls, so create a list to pass
        ///This also makes it easier to bulk handle
        AbstractDungeon.effectsQueue.add(new BrewPotionEffect(SelectedReagents()));


        SpendReagents();
        ClearReagents();
        Toggley(false);
    }

    private BrewedPotion CurrentPotion(){
        ///Java doesn't like handling nulls, so create a list to pass
        ///This also makes it easier to bulk handle
        return GetBrew(SelectedReagents());
    }

    private ArrayList<AbstractReagent> SelectedReagents() {
        ArrayList<AbstractReagent> PassToPotion = new ArrayList<>();
        if(MyAlkahest != null){
            PassToPotion.add(MyAlkahest);
        }
        if(MyBlas != null){
            PassToPotion.add(MyBlas);
        }
        if(MyConcrete != null){
            PassToPotion.add(MyConcrete);
        }
        if(MyRegulus != null){
            PassToPotion.add(MyRegulus);
        }
        if(MyHumor != null){
            PassToPotion.add(MyHumor);
        }
        return PassToPotion;
    }

    private void UpdatePositions() {
        Buttonsy = Tempy+ToButtonsh;
        BackArrowx = x+ToButtonsw;
        Cancelx = BackArrowx+Arroww+BetweenButtonsw;
        RightArrowx = Cancelx+Cancelw+BetweenButtonsw;
        Confirmx = x + ToConfirmw;
        Confirmy = Tempy + ToConfirmh;


        SlotRow1 = Tempy+ToSlot1h;
        SlotRow2 = SlotRow1-Sloth-BetweenSlotsh;
        SlotRow3 = SlotRow2-Sloth-BetweenSlotsh;
        SlotColumn1 = x+ToSlot1w;
        SlotColumn2 = SlotColumn1 + Slotw + BetweenSlotsw;
        SlotColumn3 = SlotColumn2 + Slotw + BetweenSlotsw;


        ReagentColumn1 = x + ToReagent1Sizew;
        ReagentColumn2 = ReagentColumn1 + Slotw + BetweenSlotsw;
        ReagentColumn3 = ReagentColumn2 + Slotw + BetweenSlotsw;

        PageNumx = ReagentColumn2 + (Slotw * 0.1f);
        PageNumy = SlotRow3 + (Sloth * 0.1f);
    }
///Hitbox Magic
    private void AdjustAllHitboxes() {
        this.hitbox.resize(w, h);

        Slot1.resize(Slotw, Sloth);
        Slot2.resize(Slotw, Sloth);
        Slot3.resize(Slotw, Sloth);
        Slot4.resize(Slotw, Sloth);
        Slot5.resize(Slotw, Sloth);
        Slot6.resize(Slotw, Sloth);
        Slot7.resize(Slotw, Sloth);
        Slot8.resize(Slotw, Sloth);
        Slot9.resize(Slotw, Sloth);
        if(Clarity) {
            TipBox.resize(Tipw, Tiph);
        }
        Reagent1.resize(Slotw, Sloth);
        Reagent2.resize(Slotw, Sloth);
        Reagent3.resize(Slotw, Sloth);
        Reagent4.resize(Slotw, Sloth);
        Reagent5.resize(Slotw, Sloth);
        Result.resize(Slotw, Sloth);
        LeftArrow.resize(Arroww, Arrowh);
        RightArrow.resize(Arroww, Arrowh);
        Cancel.resize(Cancelw, Cancelh);
        Confirm.resize(Confirmw, Confirmh);

        this.hitbox.translate(x, Tempy);

        Slot1.translate(SlotColumn1, SlotRow1);
        Slot2.translate(SlotColumn2, SlotRow1);
        Slot3.translate(SlotColumn3, SlotRow1);
        Slot4.translate(SlotColumn1, SlotRow2);
        Slot5.translate(SlotColumn2, SlotRow2);
        Slot6.translate(SlotColumn3, SlotRow2);
        Slot7.translate(SlotColumn1, SlotRow3);
        Slot8.translate(SlotColumn2, SlotRow3);
        Slot9.translate(SlotColumn3, SlotRow3);
        if(Clarity) {
            TipBox.translate(ReagentColumn1, Buttonsy);
        }
        Reagent1.translate(ReagentColumn1, SlotRow1);
        Reagent2.translate(ReagentColumn2, SlotRow1);
        Reagent3.translate(ReagentColumn3, SlotRow1);
        Reagent4.translate(ReagentColumn1, SlotRow2);
        Result.translate(ReagentColumn2, SlotRow2);
        Reagent5.translate(ReagentColumn3, SlotRow2);
        LeftArrow.translate(BackArrowx, Buttonsy);
        RightArrow.translate(RightArrowx, Buttonsy);
        Cancel.translate(Cancelx, Buttonsy);
        Confirm.translate(Confirmx, Confirmy);
    }
    private void UpdateAllHitboxes() {
        Slot1.update();
        Slot2.update();
        Slot3.update();
        Slot4.update();
        Slot5.update();
        Slot6.update();
        Slot7.update();
        Slot8.update();
        Slot9.update();
        if (Clarity) {
        TipBox.update();
        }
        Reagent1.update();
        Reagent2.update();
        Reagent3.update();
        Reagent4.update();
        Result.update();
        Reagent5.update();
        LeftArrow.update();
        RightArrow.update();
        Cancel.update();
        Confirm.update();
    }
    private void RenderAllHitboxes(SpriteBatch sb){
        Slot1.render(sb);
        Slot2.render(sb);
        Slot3.render(sb);
        Slot4.render(sb);
        Slot5.render(sb);
        Slot6.render(sb);
        Slot7.render(sb);
        Slot8.render(sb);
        Slot9.render(sb);
        if(Clarity) {
            TipBox.render(sb);
        }
        Reagent1.render(sb);
        Reagent2.render(sb);
        Reagent3.render(sb);
        Reagent4.render(sb);
        Result.render(sb);
        Reagent5.render(sb);
        LeftArrow.render(sb);
        RightArrow.render(sb);
        Cancel.render(sb);
        Confirm.render(sb);
        this.hitbox.render(sb);
    }

    private AbstractReagent GetReagent(int e){
        AbstractReagent r = null;
        if((e-1 + ((PageNum-1) * 9)) > (List().size()-1)){
            return null;
        }else{
            return List().get((e-1) + ((PageNum-1) * 9));
        }
    }

    ///Empty our current reagents
    public void ClearReagents(){
        MyAlkahest = null;
        MyBlas = null;
        MyConcrete = null;
        MyRegulus = null;
        MyHumor = null;
    }


    ///-1 each reagent we spend
    private void SpendReagents(){
        if(MyAlkahest != null){
            MyAlkahest.Spend();
        }
        if(MyBlas != null){
            MyBlas.Spend();
        }
        if(MyConcrete != null){
            MyConcrete.Spend();
        }
        if(MyRegulus != null){
            MyRegulus.Spend();
        }
        if(MyHumor != null){
            MyHumor.Spend();
        }
    }



    private String TipTitle(){
        return tipStrings.TEXT[TipNum];
    }
    private String TipDesc(){
        return tipStrings.EXTRA_TEXT[TipNum];
    }






    private static int TipNum = 0;
    private static final int TipCap = 8;



    ///An easy way to reference the list for our current mode
    private ArrayList<AbstractReagent> List(){
        return ReagentList(MenuMode);
    }



    ///Declare these down here for ease of reading

    ///Declare our textures and widths/heights from them
    private static final float SizeCorrect = (1.4f*Settings.scale);
    private static final float w = AlchMenu.getWidth() * SizeCorrect;
    private static final float h = AlchMenu.getHeight() * SizeCorrect;
    private static final float Slotw = SlotSize.getWidth() * SizeCorrect;
    private static final float Sloth = SlotSize.getHeight() * SizeCorrect;
    private static final float Arroww = ArrowSize.getWidth() * SizeCorrect;
    private static final float Arrowh = ArrowSize.getHeight() * SizeCorrect;
    private static final float Cancelw = CancelSize.getWidth() * SizeCorrect;
    private static final float Cancelh = CancelSize.getHeight() * SizeCorrect;
    private static final float Confirmw = ConfirmSize.getWidth() * SizeCorrect;
    private static final float Confirmh = ConfirmSize.getHeight() * SizeCorrect;
    private static final float Tipw = Tips.getWidth() * SizeCorrect;
    private static final float Tiph = Tips.getHeight() * SizeCorrect;
    ///The size of gaps to reach targets
    private static final float ToButtonsw = ToButtonsSize.getWidth() * SizeCorrect;
    private static final float ToButtonsh = ToButtonsSize.getHeight() * SizeCorrect;
    private static final float BetweenButtonsw = BetweenButtonsSize.getWidth() * SizeCorrect;
    private static final float BetweenButtonsh = BetweenButtonsSize.getHeight() * SizeCorrect;
    private static final float ToConfirmw = ToConfirmSize.getWidth() * SizeCorrect;
    private static final float ToConfirmh = ToConfirmSize.getHeight() * SizeCorrect;
    private static final float ToSlot1w = ToSlot1Size.getWidth() * SizeCorrect;
    private static final float ToSlot1h = ToSlot1Size.getHeight() * SizeCorrect;
    private static final float BetweenSlotsw = BetweenSlotsSize.getWidth() * SizeCorrect;
    private static final float BetweenSlotsh = BetweenSlotsSize.getHeight() * SizeCorrect;
    private static final float ToReagent1Sizew = ToReagent1Size.getWidth() * SizeCorrect;
    private static final float ToReagent1Sizeh = ToReagent1Size.getHeight() * SizeCorrect;

    ///Get our sizes
    private static final float x = (Settings.WIDTH * 0.625f)-w;
    private static final float y = (Settings.HEIGHT * 0.8f)-h;
    private static final float hidey = Settings.HEIGHT + (h * 0.6f);
    private static float Tempy = hidey;
    private static float Goaly = hidey;

    ///Get our texture/hb positions
    ///Button positions
    private static float Buttonsy = Tempy+ToButtonsh;
    private static float BackArrowx = x+ToButtonsw;
    private static float Cancelx = BackArrowx+Arroww+BetweenButtonsw;
    private static float RightArrowx = Cancelx+Cancelw+BetweenButtonsw;
    private static float Confirmx = x + ToConfirmw;
    private static float Confirmy = Tempy + ToConfirmh;

    ///Slot Positions
    private static float SlotRow1 = Tempy+ToSlot1h;
    private static float SlotRow2 = SlotRow1-Sloth-BetweenSlotsh;
    private static float SlotRow3 = SlotRow2-Sloth-BetweenSlotsh;
    private static float SlotColumn1 = x+ToSlot1w;
    private static float SlotColumn2 = SlotColumn1 + Slotw + BetweenSlotsw;
    private static float SlotColumn3 = SlotColumn2 + Slotw + BetweenSlotsw;

    ///Reagent Positions
    private static float ReagentColumn1 = x + ToReagent1Sizew;
    private static float ReagentColumn2 = ReagentColumn1 + Slotw + BetweenSlotsw;
    private static float ReagentColumn3 = ReagentColumn2 + Slotw + BetweenSlotsw;

    ///Other alignment variable
    private static float PageNumx = ReagentColumn2 + (Slotw * 0.1f);
    private static float PageNumy = SlotRow3 + (Sloth * 0.1f);
    ///Page Number
    int PageNum = 1;

    ///Declare all our hitboxes
    private final Hitbox Slot1 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot2 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot3 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot4 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot5 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot6 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot7 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot8 = new Hitbox(Slotw, Sloth);
    private final Hitbox Slot9 = new Hitbox(Slotw, Sloth);

    private final Hitbox Reagent1 = new Hitbox(Slotw, Sloth);
    private final Hitbox Reagent2 = new Hitbox(Slotw, Sloth);
    private final Hitbox Reagent3 = new Hitbox(Slotw, Sloth);
    private final Hitbox Reagent4 = new Hitbox(Slotw, Sloth);
    private final Hitbox Reagent5 = new Hitbox(Slotw, Sloth);

    private final Hitbox Result = new Hitbox(Slotw, Sloth);

    private final Hitbox LeftArrow = new Hitbox(Arroww, Arrowh);
    private final Hitbox RightArrow = new Hitbox(Arroww, Arrowh);
    private final Hitbox Cancel = new Hitbox(Cancelw, Cancelh);
    private final Hitbox Confirm = new Hitbox(Confirmw, Confirmh);
    private final Hitbox TipBox = new Hitbox(Tipw, Tiph);
}