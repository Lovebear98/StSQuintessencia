package quintessencia.patches.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface BrewedPotionInterface {
    default void onStartCombat(){

    }
    default void onTurnStart(){

    }
    ///default void onGainEnergy(){
///
    ///}
    ///default void onLoseEnergy(){
///
    ///}
    default void onUseCard(AbstractCard card){

    }
    default void onTurnEnd(){

    }
    default void onAttack(int i){

    }
    default void onLoseHP(int i){

    }
    ///default void onKill(){
///
    ///}
    default void onPlayerBlock(int i){

    }
    default void onMonsterBlock(int i){

    }
    ///default void onLoseBlock(){
///
    ///}
    default void onUsePotion(){

    }
    ///default void onDiscardPotion(){
///
    ///}
    ///default void onObtainCard(){
///
    ///}
    ///default void onRemoveCard(){
///
    ///}
    ///default void onGainRelic(){
///
    ///}
    ///default void onGainGold(){
///
    ///}
    ///default void onRest(){
///
    ///}
///
    ///default void onDeath(){
///
    ///}
    default void onStanceChange(){

    }

    default void onDraw(){

    }

    default void onEndCombat(){

    }

    default void onApplyPower(){

    }

    default void onBossEliteKill(){

    }

    default void onEvoke(){

    }

    default void onHeal(int healAmount){

    }

    ///This is ONLY for potions, and resets their reactiveness between combats/allows us to call it whenever we want

    default void HookReact(){

    }
    default void ResetReact(){

    }
}
