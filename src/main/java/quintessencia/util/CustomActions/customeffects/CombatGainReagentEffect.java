package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import quintessencia.reagents.AbstractReagent;

import static quintessencia.util.moreutil.AlchemyHandler.GetRandomReagent;
import static quintessencia.util.moreutil.ReagentListLoader.*;

///An animation effect that shows us picking up reagents near us


///NOTE TO SELF - Keep a close eye on this package in case it adds a lot of textures it never gets rid of
public class CombatGainReagentEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 2.5F;
    private float current_x;
    private float current_y;
    private float target_x;
    private float target_y;
    private static final float PADDING;

    private final Texture MyTexture;

    public CombatGainReagentEffect(){
        this(GetRandomReagent(AllReagents), 1);
    }
    public CombatGainReagentEffect(int num){
        this(GetRandomReagent(AllReagents), num);
    }
    public CombatGainReagentEffect(AbstractReagent r, int num) {
        ///Adjust duration
        this.duration = this.startingDuration = 1.0F;

        ///Set our texture to a copy of the reagent's own
        MyTexture = r.Texture();

        ///Adjust x/y
        ///Start at the player's center
        ///Wiggle x between -3.5 and 3.5 texture widths randomly
        this.current_x = AbstractDungeon.player.hb.cX+MathUtils.random((-MyTexture.getWidth()) * 3.5f, (MyTexture.getWidth() * 3.5f));
        ///y starts near our feet, ala on the ground
        this.current_y = AbstractDungeon.player.hb.y-(MyTexture.getHeight() * MathUtils.random(-0.5f, 0.5f));

        ///Target the center of the player as our goal point
        this.target_x = AbstractDungeon.player.hb.cX;
        this.target_y = AbstractDungeon.player.hb.cY;

        ///Set the color to render with
        this.color = Color.WHITE.cpy();
        if(num > 0){
            IncreaseReagent(r, num);
            CardCrawlGame.sound.play("TINGHSA");
        }else if(num < 0){
            DecreaseReagent(r, num);
            CardCrawlGame.sound.play("TINGHSA");
        }
    }

    public void update() {

        ///Tick down the duration of the animation by a set time
        this.duration -= Gdx.graphics.getDeltaTime();

        ///And if we're halfway through the animation or more
        if(this.duration <= this.startingDuration * 0.5f){
            ///Then move into our target location over time
            this.current_x = MathHelper.cardLerpSnap(current_x, target_x);
            this.current_y = MathHelper.cardLerpSnap(current_y, target_y);
        }

        ///If we're 7/8 done or more, start fading out
        if(this.duration <= this.startingDuration/8){
            this.color.a = this.duration / (this.startingDuration / 2.0F);
        }
        ///And once we're done, hide it
        if (this.duration < 0.0F) {
            this.color.a = 0.0F;
            ///Play the card select sound as we put it in our bag
            CardCrawlGame.sound.play("DECK_OPEN");
            this.isDone = true;

        }
    }

    public void render(SpriteBatch sb) {
        ///If we're not done
        if (!this.isDone) {
            ///Set our color
            sb.setColor(color);
            ///Render the image
            sb.draw(MyTexture, current_x, current_y);
            ///And set the color back to regular white
            sb.setColor(Color.WHITE);
        }
    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}

