import java.util.ArrayList;
import java.util.Random;

public class Simon {
    boolean defused;
    Random colour=new Random()
    ArrayList<int>pattern=new ArrayList<int>();
    int complexity
    int clicks;
    public Simon(int complexity){
        this.complexity=complexity;
    }
    /*---------------------------------
    *This is the interact function that will be used for each of the modules (Specific to each module)
    *Might need a parameter of the mouse's position or something along those lines
    *Returns a boolean so that if they make a mistake, we can return false in the main and display the mistake.
    -----------------------------------*/
    public boolean interact(){
        if(mouseclicked){//Whenever you pick one of the 4 colours
            clicks+=1;//The user has gone one further into their pattern
            if(pattern.get(clicks)==clicked){//If they click the right one
                if(pattern.size()==clicks){ //We check if they've finished the pattern
                    if(clicks==complexity){ //And check if they've gotten to the largest complexity
                        defused=true;
                        return true;
                    }
                    clicks=0;
                    pattern.add(colour.nextInt(4));
                }
                return true;
            }
            else{
                clicks=0;
                return false;
            }
        }
        if(pattern.contains(input)){
            if(clicks==flashes){

            }
            correct+=1;
            //Display the button as green/clicked
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isdefused(){
        return defused;
    }
}
