//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ColoredPlasmaOrbPassiveEffect extends AbstractGameEffect {
    private float effectDuration;
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private TextureAtlas.AtlasRegion img;

    public ColoredPlasmaOrbPassiveEffect(float x, float y, Color color1, Color color2, Color color3) {
        this.img = ImageMaster.GLOW_SPARK_2;// 16
        this.effectDuration = 1.0F;// 19
        this.duration = this.effectDuration;// 20
        this.startingDuration = this.effectDuration;// 21
        this.x = x + MathUtils.random(-30.0F, 30.0F) * Settings.scale;// 23
        this.y = y + MathUtils.random(-30.0F, 30.0F) * Settings.scale;// 24
        this.sX = this.x;// 25
        this.sY = this.y;// 26
        this.tX = x;// 27
        this.tY = y;// 28
        int tmp = MathUtils.random(2);// 30
        if (tmp == 0) {// 31
            this.color = color1.cpy();// 32
        } else if (tmp == 1) {// 33
            this.color = color2.cpy();// 34
        } else {
            this.color = color3.cpy();// 36
        }

        this.scale = MathUtils.random(0.3F, 1.2F) * Settings.scale;// 39
        this.renderBehind = true;// 40
    }// 41

    public void update() {
        this.x = Interpolation.circleOut.apply(this.sX, this.tX, this.duration);// 44
        this.y = Interpolation.circleOut.apply(this.sY, this.tY, this.duration);// 45
        super.update();// 47
    }// 48

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);// 52
        sb.setBlendFunction(770, 1);// 53
        sb.draw(this.img, this.x - (float)this.img.packedWidth / 2.0F, this.y - (float)this.img.packedWidth / 2.0F, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(0.7F, 1.4F), this.scale * MathUtils.random(0.7F, 1.4F), this.rotation);// 54 62 63
        sb.setBlendFunction(770, 771);// 65
    }// 66

    public void dispose() {
    }// 71
}
