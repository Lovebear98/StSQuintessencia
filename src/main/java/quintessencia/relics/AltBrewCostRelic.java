package quintessencia.relics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static quintessencia.util.moreutil.Textures.AltCostFirst;


///These relics can allow us to pay different resources to brew
public abstract class AltBrewCostRelic extends BaseRelic implements ClickableRelic {
    ///UseFirst will cause the relic to be PRIORITIZED over energy, and other cost relics.
    public boolean UseFirst = false;

    ///Build it like a normal relic!
    public AltBrewCostRelic(String ID, String NAME, RelicTier RARITY, LandingSound SOUND) {
        super(ID, NAME, RARITY, SOUND);
    }

    ///When we're triggered to become first
    public void MakeMeFirst(){
        if(!this.UseFirst){
            ///Flash us
            this.flash();
            ///Make us first
            this.UseFirst = true;
            ///Fix our description
            FixDescription();
            ///Then if the player exists
            if(AbstractDungeon.player != null){
                ///Make sure nobody else is first, and fix their descriptions.
                for(AbstractRelic r: AbstractDungeon.player.relics){
                    if(r instanceof AltBrewCostRelic  && r != this){
                        ((AltBrewCostRelic) r).UnMakeMeFirst();
                    }
                }
            }
        }
    }

    public void UnMakeMeFirst(){
        ///Make us not first
        this.UseFirst = false;
        ///Fix our description
        FixDescription();
    }

    ///Whether we can afford or not. If we can, stop her. If not, check the next one.
    public boolean CanAfford(){
        return false;
    }
    ///What to do to pay the cost.
    public void PayCost(){

    }

    ///Fix the description!
    protected void FixDescription() {
        this.description = getUpdatedDescription();
        tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void onRightClick() {
        if(!UseFirst){
            MakeMeFirst();
        }else{
            UnMakeMeFirst();
        }
    }

    @Override
    public boolean hovered() {
        return ClickableRelic.super.hovered();
    }


    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        if(UseFirst){
            Color StoreColor = sb.getColor();
            sb.setColor(Color.YELLOW.cpy());
            sb.draw(AltCostFirst, this.currentX-(this.hb.width*0.75f), this.currentY-(this.hb.height*0.75f), this.hb.width*1.5f, this.hb.height*1.5f);
            sb.setColor(StoreColor);
        }
    }


}