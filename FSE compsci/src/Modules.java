public class Modules {
    public final int BUTTON=1; //Use the names instead of numbers since its more conventional for making bombs
    public final int WIRES=2;
    public final int SYMBOLS=3;
    public final int SIMON=4;
    //Not gonna make the timer a module since it should always be on the bomb no matter what
    int type;
    boolean defused=false;
    /*---------------------------------
    *Constructor for making the module
    *We need a "type" to distinguish what kind of module we're using
    *No return
    -----------------------------------*/
    public Modules(int type){
        this.type=type;
    }
    /*---------------------------------
    *When you click or select the module to "play" it. Can't be played if it's defused
    *No parameters (might not a mouseclick or something)
    *No return but it will help determine when the module is defused or not.
    -----------------------------------*/
    public void play(){
        if(!defused){
            if(type==1){
                Button click=new Button();
                if(click.isdefused()){
                    defused=true;
                }
            }
            if(type==2){
                Wires cut=new Wires();
                if(cut.isdefused()){
                    defused=true
                }
            }
            if(type==3){
                Symbols press=new Symbols();
                if(press.isdefused()){
                    defused=true
                }
            }
            if(type==4){
                Simon pattern=new Simon();
                if(pattern.isdefused()){
                    defused=true
                }
            }
        }
    }
    /*---------------------------------
    *Checks if the module has been "solved" or defused
    *No parameters
    *Returns defused which will always be true or false
    -----------------------------------*/
    public boolean isDefused(){
        return defused;
    }

}
