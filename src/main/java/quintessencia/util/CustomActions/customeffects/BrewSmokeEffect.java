package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BrewSmokeEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public BrewSmokeEffect(float x, float y) {
        this.x = x;// 13
        this.y = y;// 14
        this.duration = 0.2F;// 15
    }// 16

    public void update() {
        if (this.duration == 0.2F) {// 19
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");// 20

            for(int i = 0; i < 30; ++i) {// 21
                AbstractDungeon.effectsQueue.add(new BrewBlurEffect(this.x, this.y));// 22
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 25
        if (this.duration < 0.0F) {// 26
            CardCrawlGame.sound.play("APPEAR");// 27
            this.isDone = true;// 28
        }

    }// 30

    public void render(SpriteBatch sb) {
    }// 34

    public void dispose() {
    }// 39
}
