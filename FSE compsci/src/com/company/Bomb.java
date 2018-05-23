import java.util.ArrayList;

class Bomb{
    String serial;
    int bat;
    int mod;
    Modules[][]minigames;
    int face;
    public Bomb(String serial,int bat,int mod, String components){
        int count;
        this.serial=serial;
        this.bat=bat;
        this.mod=mod;
        face=1;
        if(components.split(",")>5){
            minigames=new Modules[6][2];
        }
        else {
            minigames = new Modules[6][1];
        }
        for(int i=0;i<components.split(",");i++) {
            if (count < 5) {
                Modules[i][1] = new Modules(Modules. (components.split(",")[i].toUpperCase()));
            }
            else {
                Modules[i - 5][2] = new Modules(Modules. (components.split(",")[i].toUpperCase()));
            }
        }
    }
}
