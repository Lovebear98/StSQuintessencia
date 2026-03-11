package quintessencia.orbs;


import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import quintessencia.powers.custompowers.BarklightPower;
import quintessencia.util.CustomActions.customeffects.ColoredPlasmaOrbPassiveEffect;
import quintessencia.util.CustomActions.customeffects.CombatGainReagentEffect;

import static quintessencia.QuintessenciaMod.makeID;

public class Elemental extends CustomOrb {

    // Standard ID/Description
    public static final String ORB_ID = makeID(Elemental.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int PASSIVE_AMOUNT = 1;
    private static final int EVOKE_AMOUNT = 1;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.05f;
    private float vfxIntervalMax = 0.25f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    private final Color c1;
    private final Color c2;
    private final Color c3;

    public Elemental() {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[1], DESCRIPTIONS[1], "quintessencia/orbs/Elemental.png");
        c1 = new Color((MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), 1f);
        c2 = new Color((MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), 1f);
        c3 = new Color((MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), (MathUtils.random(0f, 255f)/255f), 1f);


        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1]+ passiveAmount + DESCRIPTIONS[2];
    }

    @Override
    public void applyFocus() {
        this.passiveAmount = basePassiveAmount;
        this.evokeAmount = baseEvokeAmount;
    }


    @Override
    public void onStartOfTurn() {// 1.At the start of your turn.
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarklightPower(AbstractDungeon.player, passiveAmount)));
    }

    @Override
    public void updateAnimation() {// You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's Water Orb. It includes a custom sound, too!
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new ColoredPlasmaOrbPassiveEffect(cX, cY, c1, c2, c3));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    // Render the orb.



    @Override
    public void triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Elemental();
    }
@Override
    public void onEvoke() {
        AbstractDungeon.effectsQueue.add(new CombatGainReagentEffect(evokeAmount));
    AbstractDungeon.actionManager.addToBottom(new SFXAction("CEILING_BOOM_1")); // 3.And play a Jingle Sound.
    }
}
