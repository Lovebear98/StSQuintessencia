package quintessencia.brewingmode;

import java.util.ArrayList;
import java.util.Objects;

public class ModeManager {

    ///Our list of catalyzer relics to enable alternate brewing costs
    public static ArrayList<BrewingMode> BrewModes = new ArrayList<>();

    ///Which Brew Mode we're on in our list
    public static int ModeNum = 0;
    ///The current active Brew Mode
    public static BrewingMode CurrentMode;

    ///When we scroll left (up) the list
    public static void ScrollLeft(){
        ///Only do thigns if the list isn't empty
        if(!BrewModes.isEmpty()){
            ///If we're at the beginning
            if(ModeNum == 0){
                ///Jump to the end
                ModeNum = BrewModes.size()-1;
                ///otherwise
            }else{
                ///If we're within range
                if(BrewModes.size()-1 >= ModeNum){
                    ///Reduce by 1
                    ModeNum -= 1;
                    ///otherwise
                }else{
                    ///Jump into range
                    ModeNum = BrewModes.size()-1;
                }
            }
            ///Then if we have unlocked the mode we landed on or it's an always shown mode
            if((BrewModes.get(ModeNum).ModeCondition()) || BrewModes.get(ModeNum).AlwaysShow()){
                ///That's our current mode
                CurrentMode = BrewModes.get(ModeNum);
            }
        }
    }

    public static void ScrollRight(){
        ///Only do things if the list isn't empty
        if(!BrewModes.isEmpty()){
            ///If we're at the end
            if(ModeNum == BrewModes.size()-1){
                ///Jump to the start
                ModeNum = 0;
                ///otherwise
            }else{
                ///If we're within range
                if(BrewModes.size()-1 > (ModeNum)){
                    ///Increase by 1
                    ModeNum += 1;
                    ///otherwise
                }else{
                    ///Jump into range
                    ModeNum = 0;
                }
            }
            ///Then if we have unlocked the mode we landed on or it's an always shown mode
            if((BrewModes.get(ModeNum).ModeCondition()) || BrewModes.get(ModeNum).AlwaysShow()){
                ///That's our current mode
                CurrentMode = BrewModes.get(ModeNum);
            }
        }
    }


    ///Manually enable a mode
    public static void EnableMode(BrewingMode mo){
        BrewModes.add(mo);
    }

    ///Manually disable a mode
    public static void DisableMode(String ID){
        for(BrewingMode mo: BrewModes){
            ///We CANNOT disable default modes, for safety
            if(!mo.Default()){
                if(Objects.equals(mo.ID, ID)){
                    BrewModes.remove(mo);
                }
            }
        }
    }
    ///Clear our list altogether
    public static void ClearModes(){
        BrewModes.clear();
        ///And put back the default
        BrewModes.add(new DefaultMode());
    }
    public static void VerifyModes(){
        if(!BrewModes.isEmpty()){
            ScrollLeft();
            ScrollRight();
        }else{
            ClearModes();
        }
    }
}
