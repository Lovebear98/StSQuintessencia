package quintessencia.util.CustomActions.customeffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static quintessencia.util.moreutil.AlchemyHandler.GetRandomReagent;
import static quintessencia.util.moreutil.ReagentListLoader.*;
public class RandomizedPotionReagentsEffect extends AbstractGameEffect {
    private final int num;

    public RandomizedPotionReagentsEffect(){
        this(1);
    }
    public RandomizedPotionReagentsEffect(int i) {
        this.num = i;
    }

    public void update() {
        ///Once for each amount we're passed
        for(int i = num; i > 0; i -= 1){
            ///Gain a random one of each reagent
            GetRandomReagent(AllRegulus()).GainLoseReagent(1);
            GetRandomReagent(AllAlkahests()).GainLoseReagent(1);
            GetRandomReagent(AllBlas()).GainLoseReagent(1);
            GetRandomReagent(AllConcretes()).GainLoseReagent(1);
            GetRandomReagent(AllHumors()).GainLoseReagent(1);
        }
        isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

