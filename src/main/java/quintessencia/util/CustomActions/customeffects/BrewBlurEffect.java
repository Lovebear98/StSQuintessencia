package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BrewBlurEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float aV;
    private float startDur;
    private float targetScale;
    private TextureAtlas.AtlasRegion img;

    public BrewBlurEffect(float x, float y) {
        this.color = new Color(0.0F, 0.0F, 0.0F, 1.0F);// 19
        this.color.r = MathUtils.random(0.4F, 0.7F);// 20
        this.color.g = MathUtils.random(0.4F, 0.7F);// 20
        this.color.b = MathUtils.random(0.4F, 0.7F);// 20
        if (MathUtils.randomBoolean()) {// 24
            this.img = ImageMaster.EXHAUST_L;// 25
            this.duration = MathUtils.random(0.4F, 1.2F);// 26
            this.targetScale = MathUtils.random(0.4F, 0.8F);// 27
        } else {
            this.img = ImageMaster.EXHAUST_S;// 29
            this.duration = MathUtils.random(0.2F, 0.4F);// 30
            this.targetScale = MathUtils.random(2.4F, 2.8F);// 31
        }

        this.startDur = this.duration;// 34
        this.x = x + MathUtils.random(-180.0F * Settings.scale, 150.0F * Settings.scale) - (float)this.img.packedWidth / 2.0F;// 36
        this.y = y + MathUtils.random(-240.0F * Settings.scale, 150.0F * Settings.scale) - (float)this.img.packedHeight / 2.0F;// 37
        this.scale = 0.01F;// 38
        this.rotation = MathUtils.random(360.0F);// 39
        this.aV = MathUtils.random(-250.0F, 250.0F);// 40
        this.vY = MathUtils.random(1.0F * Settings.scale, 5.0F * Settings.scale);// 41
    }// 42

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();// 45
        if (this.duration < 0.0F) {// 46
            this.isDone = true;// 47
        }

        this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);// 49
        this.x += this.vY;// 50
        this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);// 51
        this.y += this.vY;// 52
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();// 53
        this.scale = Interpolation.exp10Out.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);// 54
        if (this.duration < 0.33F) {// 56
            this.color.a = this.duration * 3.0F;// 57
        }

    }// 59

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);// 63
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 64
    }// 75

    public void dispose() {
    }// 80
}