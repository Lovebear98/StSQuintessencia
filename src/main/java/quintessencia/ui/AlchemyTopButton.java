package quintessencia.ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import quintessencia.relics.FieldAlchemyNotes;
import quintessencia.util.TextureLoader;

import static quintessencia.QuintessenciaMod.makeID;
import static quintessencia.util.moreutil.AlchemyHandler.ToggleAlchemy;
import static quintessencia.util.moreutil.Vars.isInCombat;

public class AlchemyTopButton extends TopPanelItem {
    private boolean JustHovered = true;
    private static final Texture IMG = TextureLoader.getTexture("quintessencia/ui/AlchButton.png");
    public static final String ID = makeID("PotionPatch");
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);


    public AlchemyTopButton() {
        this(IMG, ID);
    }
    public AlchemyTopButton(Texture image, String ID) {
        super(image, ID);
    }

    @Override
    protected void onClick() {
        if(AbstractDungeon.player != null){
            if(!AbstractDungeon.player.isDead){
                if(isInCombat()){
                    ToggleAlchemy();
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
            super.render(sb);
    }

    @Override
    protected void onHover() {
        super.onHover();
        if(JustHovered){
            int Sound = MathUtils.random(1, 3);
            switch(Sound){
                case 1:
                    CardCrawlGame.sound.play("POTION_1", 0.2f);
                    break;
                case 2:
                    CardCrawlGame.sound.play("POTION_2", 0.2f);
                    break;
                case 3:
                    CardCrawlGame.sound.play("POTION_3", 0.2f);
                    break;
            }
            JustHovered = false;
        }
        TipHelper.renderGenericTip((this.x - (IMG.getWidth())), (float) (this.y-(IMG.getHeight())), uiStrings.TEXT[2], Desc());
    }

    private String Desc() {
        if(AbstractDungeon.player != null){
            if(!isInCombat()){
                return uiStrings.TEXT[5];
            }
            if(AbstractDungeon.player.hasRelic(FieldAlchemyNotes.ID)){
                return uiStrings.TEXT[4];
            }
        }
        return uiStrings.TEXT[3];
    }

    @Override
    protected void onUnhover() {
        super.onUnhover();
        JustHovered = true;
    }
}
