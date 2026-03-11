//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;

import java.util.Iterator;

public class FastPurgeEffect extends AbstractGameEffect {
    private AbstractCard card;
    private static final float PADDING;
    private float scaleY;
    private Color rarityColor;

    public FastPurgeEffect(AbstractCard card) {
        this(card, (float)Settings.WIDTH - 96.0F * Settings.scale, (float)Settings.HEIGHT - 32.0F * Settings.scale);// 30
    }// 31

    public FastPurgeEffect(AbstractCard card, float x, float y) {
        this.card = card;// 34
        this.startingDuration = 1.2F;// 35
        this.duration = this.startingDuration;// 36
        this.identifySpawnLocation(x, y);// 37
        card.drawScale = 0.01F;// 38
        card.targetDrawScale = 1.0F;// 39
        CardCrawlGame.sound.play("CARD_BURN");// 40
        this.initializeVfx();// 42
    }// 43

    private void initializeVfx() {
        switch (this.card.rarity) {// 46
            case UNCOMMON:
                this.rarityColor = new Color(0.2F, 0.8F, 0.8F, 0.01F);// 48
                break;// 49
            case RARE:
                this.rarityColor = new Color(0.8F, 0.7F, 0.2F, 0.01F);// 51
                break;// 52
            case BASIC:
            case COMMON:
            case CURSE:
            case SPECIAL:
            default:
                this.rarityColor = new Color(0.6F, 0.6F, 0.6F, 0.01F);// 62
        }

        switch (this.card.color) {// 66
            case BLUE:
                this.color = new Color(0.1F, 0.4F, 0.7F, 0.01F);// 68
                break;// 69
            case COLORLESS:
                this.color = new Color(0.4F, 0.4F, 0.4F, 0.01F);// 71
                break;// 72
            case GREEN:
                this.color = new Color(0.2F, 0.7F, 0.2F, 0.01F);// 74
                break;// 75
            case RED:
                this.color = new Color(0.9F, 0.3F, 0.2F, 0.01F);// 77
                break;// 78
            case CURSE:
            default:
                this.color = new Color(0.2F, 0.15F, 0.2F, 0.01F);// 82
        }

        this.scale = Settings.scale;// 85
        this.scaleY = Settings.scale;// 86
    }// 87

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;// 90
        Iterator var4 = AbstractDungeon.effectList.iterator();// 91

        AbstractGameEffect e;
        while(var4.hasNext()) {
            e = (AbstractGameEffect)var4.next();
            if (e instanceof FastPurgeEffect) {// 92
                ++effectCount;// 93
            }
        }

        var4 = AbstractDungeon.topLevelEffects.iterator();// 96

        while(var4.hasNext()) {
            e = (AbstractGameEffect)var4.next();
            if (e instanceof FastPurgeEffect) {// 97
                ++effectCount;// 98
            }
        }

        this.card.current_x = x;// 102
        this.card.current_y = y;// 103
        this.card.target_y = (float)Settings.HEIGHT * 0.5F;// 104
        switch (effectCount) {// 106
            case 0:
                this.card.target_x = (float)Settings.WIDTH * 0.5F;// 108
                break;// 109
            case 1:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;// 111
                break;// 112
            case 2:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;// 114
                break;// 115
            case 3:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;// 117
                break;// 118
            case 4:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;// 120
                break;// 121
            default:
                this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);// 123
                this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);// 124
        }

    }// 127

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();// 130
        if (this.duration < 0.5F) {// 131
            if (!this.card.fadingOut) {// 132
                this.card.fadingOut = true;// 133
                if (!Settings.DISABLE_EFFECTS) {// 134
                    int i;
                    for(i = 0; i < 16; ++i) {// 135
                        AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x, this.card.current_y, this.color, false));// 136
                    }

                    for(i = 0; i < 8; ++i) {// 139
                        AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x, this.card.current_y, this.rarityColor, false));// 140
                    }
                }
            }

            this.updateVfx();// 145
        }

        this.card.update();// 148
        if (this.duration < 0.0F) {// 150
            this.isDone = true;// 151
        }

    }// 153

    private void updateVfx() {
        this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.5F);// 156
        this.rarityColor.a = this.color.a;// 157
        this.scale = Interpolation.swingOut.apply(1.6F, 1.0F, this.duration * 2.0F) * Settings.scale;// 158
        this.scaleY = Interpolation.fade.apply(0.005F, 1.0F, this.duration * 2.0F) * Settings.scale;// 159
    }// 160

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);// 164
        this.card.render(sb);// 165
        this.renderVfx(sb);// 166
    }// 167

    private void renderVfx(SpriteBatch sb) {
        sb.setColor(this.color);// 170
        TextureAtlas.AtlasRegion img = ImageMaster.CARD_POWER_BG_SILHOUETTE;// 171
        sb.draw(img, this.card.current_x + img.offsetX - (float)img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.scale * MathUtils.random(0.95F, 1.05F), this.scaleY * MathUtils.random(0.95F, 1.05F), this.rotation);// 172 180 181
        sb.setBlendFunction(770, 1);// 184
        sb.setColor(this.rarityColor);// 185
        img = ImageMaster.CARD_SUPER_SHADOW;// 186
        sb.draw(img, this.card.current_x + img.offsetX - (float)img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.scale * 0.75F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.75F * MathUtils.random(0.95F, 1.05F), this.rotation);// 187 195 196
        sb.draw(img, this.card.current_x + img.offsetX - (float)img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.scale * 0.5F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.5F * MathUtils.random(0.95F, 1.05F), this.rotation);// 199 207 208
        sb.setBlendFunction(770, 771);// 210
    }// 211

    public void dispose() {
    }// 216

    static {
        PADDING = 30.0F * Settings.scale;// 25
    }
}
