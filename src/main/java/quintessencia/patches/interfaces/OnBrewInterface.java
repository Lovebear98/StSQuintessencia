package quintessencia.patches.interfaces;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public interface OnBrewInterface {
    default void onBrew(AbstractPotion p){

    }
}
