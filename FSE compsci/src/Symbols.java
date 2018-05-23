public class Symbols {
    int[]order;
    int correct;
    boolean defused;
    public Symbols(int[]order){
        this.order=order;
    }
    /*---------------------------------
    *This is the interact function that will be used for each of the modules
    *Might need a parameter of the mouse's position or something along those lines
    *Returns a boolean so that if they make a mistake, we can return false in the main and display the mistake.
    -----------------------------------*/
    public boolean interact(){
        if(!defused) { //If the bomb is defused then they cant interact with it. Might just handle this in main/modules/bomb class though
            if (clicked == order[correct]) {
                correct += 1;
                //Display the button as green/clicked
                if (correct == 4) {
                    defused = true;
                    return true;
                }
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean isdefused(){
        return defused;
    }
}
