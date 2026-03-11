package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RemoveRewardEffect extends AbstractGameEffect {
    public RewardItem reward;
    public static boolean ShowOwnedMenu = false;

    public RemoveRewardEffect(RewardItem reward) {
        this.reward = reward;
    }

    @Override
    public void update() {
        AbstractDungeon.combatRewardScreen.rewards.remove(reward);
        this.isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    @Override
    public void dispose() {
    }
}