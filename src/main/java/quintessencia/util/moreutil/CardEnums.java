package quintessencia.util.moreutil;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class CardEnums {

        @SpireEnum(name = "Reagent Green") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor REAGENTCOLOR;
        @SpireEnum(name = "Reagent Green") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType REAGENTLIBCOLOR;

        @SpireEnum(name = "Quest Tan") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor QUESTCOLOR;
        @SpireEnum(name = "Quest Tan") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType QUESTLIBCOLOR;

        @SpireEnum
        public static AbstractPotion.PotionRarity CONCOCTION;

    }