package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;

public class RemoveCardEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private final AbstractCard card;

    public RemoveCardEffect(AbstractCard c) {
        this.card = c;
    }

    @Override
    public void update() {
        AbstractCard x = getMasterDeckEquivalent(card);
        AbstractDungeon.player.masterDeck.removeCard(x);
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
