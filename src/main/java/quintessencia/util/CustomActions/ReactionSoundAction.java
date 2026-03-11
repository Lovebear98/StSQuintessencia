package quintessencia.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static quintessencia.util.moreutil.AlchemyHandler.PlaySound;
import static quintessencia.util.moreutil.Vars.CLINKSOUNDKEY;

public class ReactionSoundAction extends AbstractGameAction {
    @Override
    public void update() {
        if(PlaySound){
            PlaySound = false;
            CardCrawlGame.sound.play(CLINKSOUNDKEY, 0.4f);
        }
        isDone = true;
    }
}
