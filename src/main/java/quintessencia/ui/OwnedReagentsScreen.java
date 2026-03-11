package quintessencia.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.*;
import static quintessencia.util.moreutil.Textures.*;


@SuppressWarnings("unused")
public class OwnedReagentsScreen extends ClickableUIElement{
    ///Set our UI strings
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(OwnedReagentsScreen.class.getSimpleName()));




    ///Height and Width are based on the image's size
    private static boolean Collapse = true;
    private static final float SizeCorrect = (1.4f*Settings.scale);
    private static final float w = OwnedMenu.getWidth() * SizeCorrect;
    private static final float h = OwnedMenu.getHeight() * SizeCorrect;

    private static final float Toboxesh = ToRewardsSlots.getWidth() * SizeCorrect;
    private static final float BetweenSlotsw = BetweenSlotsSize.getWidth() * SizeCorrect;
    private static final float Sloth = SlotSize.getHeight() * SizeCorrect;

    private static final float x = 400 * Settings.scale;

    private static final float y = 300f * Settings.scale;

    public OwnedReagentsScreen(){
        super(OwnedMenu,x,y,w,h);
        setClickable(false);

        this.hitbox.resize(w, h);
        this.hitbox.translate(x, y);
    }

    @Override
    public void render(SpriteBatch sb,Color color){

        ///Start rendering
        sb.end();
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();


        ///Draw the main UI
        sb.draw(OwnedMenu, x, y, w, h);


        ///Display the current Mysteria
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, String.valueOf(OwnedRegulusNum()), TextX(), TextY(5), Color.WHITE.cpy());
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, String.valueOf(OwnedAlkahestsNum()), TextX(), TextY(4), Color.WHITE.cpy());
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, String.valueOf(OwnedBlasNum()), TextX(), TextY(3), Color.WHITE.cpy());
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, String.valueOf(OwnedConcretesNum()), TextX(), TextY(2), Color.WHITE.cpy());
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, String.valueOf(OwnedHumorsNum()), TextX(), TextY(1), Color.WHITE.cpy());





        ///Draw the hitbox
        this.hitbox.translate(x, y);
        this.hitbox.render(sb);
    }

    private void RepositionHitboxes() {

    }

    private float TextX(){
        return x + (w * 0.75f);
    }
    private float TextY(int i){
        return y+Toboxesh+(Sloth/2) + ((Sloth+BetweenSlotsw)*(i - 1));
    }


    @Override
    protected void onHover(){
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD ){
            RenderTooltip(uiStrings.TEXT[0], uiStrings.TEXT[1]);
        }
    }

    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(x - (x*0.25f), y-(y * 0.3f), Name, Desc);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void onUnhover(){

    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
        if(!AbstractDungeon.isScreenUp){

        }
    }
}