/*
 * MainGame.java
 * Upon running, a 3 x 2 grid is displayed that represents the bomb. There are wires and a countdown.
 * How the program works: main method in MainGame class creates MainGame Object. The constructor there makes
 * a Bomb Object. The Bomb's constructor makes wire and time module Objects,  which are also fields in Bomb.
 * Keith Wong
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.Timer;		//import Timer specifically to avoid conflict with util Timer
public class MainGame extends JFrame implements ActionListener {
    Timer myTimer;
    private int tickCount;		//counter that tracks how much time is left in milliseconds to defuse the bomb
    Bomb bomb;

    /*----------------------------------------------------------------
    Constructor which makes the window, the Bomb Object, and the Timer
     ----------------------------------------------------------------*/
    public MainGame() {
        super("Main Game");
        setSize(800,600);
        myTimer=new Timer(10,this);
        myTimer.start();
        tickCount=310000;						//counts down, 1000 greater than desired number on screen, in this case, 30 sec
        bomb=new Bomb(3);				//if numWires>5, add colours to allColours[][] line 188
        add(bomb);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /*-----------------------------------------------------------------
    Updates the game whenever Timer fires and ends game when time is up
     ----------------------------------------------------------------*/
    public void actionPerformed(ActionEvent e){
        Object source=e.getSource();
        if(bomb!=null && source==myTimer){			//updating the game
            tickCount-=10;
            bomb.updateState();
            bomb.repaint();
        }
        if(tickCount==0){							//stop game when time is up
            myTimer.stop();
            tickCount=0;
            System.out.println("done");
        }
    }

    public static void main(String[]args){
        new MainGame();
    }
}
/*--------------------------------------------------
 *This class makes a bomb with modules as attributes
 *--------------------------------------------------*/
class Bomb extends JPanel implements MouseListener{
    private int mouseX,mouseY;					//coordinates of mouse
    private TimeModule timer;
    private WireModule wires;

    /*--------------------------------------------------------
    Constructor which makes a timer and wire module.
    "numWires" specifies how many wires are in the wire module.
     ---------------------------------------------------------*/
    public Bomb(int numWires){
        addMouseListener(this);
        mouseX=mouseY=0;
        timer=new TimeModule(340,425,31000);		//creating a timer

        int[] wireYCoord=new int[numWires];
        int spaceBetweenWires=(200-10*numWires)/(numWires+1);		//adjust: 200, which is the height of the squares on the grid.
        for(int i=0;i<numWires;i++){
            int nextYCoord=100+spaceBetweenWires*(i+1)+10*i;		//adjust: 100, which is y-coord where the module box starts. 10, which is current width of wires
            wireYCoord[i]=nextYCoord;
        }
        wires=new WireModule(wireYCoord,numWires);					//creating a wire module
    }

    /*-----------------------------------------------------------------
    This method updates the game whenever the Timer fires in main class
     -----------------------------------------------------------------*/
    public void updateState(){
        timer.subtractTime();						//displaying a count down
        for(Rectangle rect:wires.getWires()){		//checks if user clicks on wire
            if(rect.contains(mouseX,mouseY)){
                System.out.println("true");
            }
        }
    }

    /*------------------------------------------------------------
    Draws the bomb on screen, called at same time as updateState()
     ------------------------------------------------------------*/
    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0,getWidth(),getHeight());		//clearing the screen in preparation for new drawings
        g.setColor(new Color(0,0,0));

        for(int i=100;i<800;i+=200){						//drawing a 3 x 2 grid to represent the bomb
            g.drawLine(i,100,i,500);
        }
        for(int i=100;i<600;i+=200){
            g.drawLine(100,i,700,i);
        }

        g.setFont(new Font("Arial",Font.PLAIN,50));			//displaying time
        g.drawString(timer.getTime(),timer.getX(),timer.getY());

        for(int i=0;i<wires.getNumWires();i++){							//drawing the wires on screen
            Rectangle rect=wires.getWires()[i];
            int[] newColour=wires.getColour(i);
            g.setColor(new Color(newColour[0],newColour[1],newColour[2]));
            g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
        }
    }
    /*--------------------------------------------------------
    This method updates mouse coordinates whenever user clicks
     ---------------------------------------------------------*/
    public void mousePressed(MouseEvent e){
        mouseX=e.getX();
        mouseY=e.getY();
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
/*---------------------------------------------
 This class makes a countdown for a Bomb Object
 --------------------------------------------*/
class TimeModule{
    private int x,y,time;		//time is in milliseconds, x and y are where the module is displayed

    /*----------------------------------------
    Constructor, module is made in Bomb class
     ----------------------------------------*/
    public TimeModule(int xCoord, int yCoord, int timeLeft){
        x=xCoord;
        y=yCoord;
        time=timeLeft;
    }

    /*------------------------------------------------------------------------
     Responsible for the countdown because it alters the time that's displayed
     -------------------------------------------------------------------------*/
    public void subtractTime(){
        time-=10;					//called every 10 milliseconds
    }
    /*---------------------------------------------------------
    Accessor methods used by paintComponent to display the time
     ----------------------------------------------------------*/
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    /*--------------------------------------------------------------------------------------
     This method returns the time remaining in the format of minutes:seconds, such as 01:30
     Returns the time as a String so paintComponent can display it
     *-----------------------------------------------------------------------------------*/
    public String getTime(){
        int min=time/60000;
        int seconds=(time-(min*60000))/1000;
        String output=String.format("%2d:%2d",(int)min,seconds).replace(" ","0");		//replacing blank spaces with 0's so it looks like a timer
        return output;
    }
}
/*--------------------------------------------------------------
 This class makes a wire module with a specified number of wires
 *-------------------------------------------------------------*/
class WireModule{
    private Rectangle[] wires;				//each wire is a rectangle so collisions with the mouse can be detected
    private int[][]colours;					//the colours of the wires
    private int[] codes;
    private int numWires;
    /*--------------------------------------------------------------------------------------------
    Constructor which creates a specified number of Rectangles and rgb values that represent wires
    "coord" is a list of y coordinates that paintComponent uses to draw the wires.
    "wireCount" is how many wires there will be
     -------------------------------------------------------------------------------------------*/
    public WireModule(int[] coord,int wireCount){
        numWires=wireCount;
        colours=new int[numWires][3];	//possible wire colours: red, blue, green, yellow, black
        codes=new int[numWires];
        int[][]allColours={{255,0,0},{0,0,255},{0,255,0},{255,255,0},{0,0,0}};
        Random rand=new Random();										//generating random colours for the wires

        for(int i=0;i<numWires;i++){
            int index=rand.nextInt(numWires);							//choosing a random colour out of all the possible colours and assigning it to a wire
            int[] rgbSet=allColours[index];
            colours[i]=rgbSet;

            int sum=0;													//assigning a code to the wire that determines when it should be cut
            sum+=rgbSet[0]+rgbSet[1]*2+rgbSet[2]*3;						//each colour value has a certain weighting that contributes to the code
            codes[i]=sum;
        }
        Arrays.sort(codes);					//wires must be cut in ascending order
        wires=new Rectangle[numWires];		//creating the Rectangles that represent the wire hitboxes
        for(int i=0;i<numWires;i++){
            int YCoord=coord[i];
            wires[i]=new Rectangle(100,YCoord,200,10);
        }
    }

	/*
	Work in progress, ignore this method for now.
	This method is called whenever a wire is clicked to see if wires are cut in the right order.

	public boolean checkOrder(int[] cutSoFar){
		int[] correctOrder=codes[0:cutSoFar.length];
		return correctOrder.equals(cutSoFar);
	}*/

    /*----------------------------------------------------
     Used by paintComponent to draw the wires.
     Also used by updateState to see if user clicked a wire.
     ------------------------------------------------------*/
    public Rectangle[] getWires(){
        return wires;
    }
    /*----------------------------------------
     Used by paintComponent to draw the wires.
     ------------------------------------------*/
    public int[] getColour(int index){
        return colours[index];
    }

    /*--------------------------------------------------------------
    Used by paintComponent to display rectangles where the wires are
     --------------------------------------------------------------*/
    public int getNumWires(){
        return numWires;
    }
}