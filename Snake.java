import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snake{
    /** The variables and stuff are defined here. */
    public static int xCoord, yCoord, dotX, dotY, score, growth;
    public static int frameNumber = 1;
    public static int growthSize = 5;
    public static int waitTime = 50; /** This changes the refresh time*/
    public static boolean up, down, left, right, theBoolean, runsIntoDot, isPlaying;
    public ArrayList<Integer> x = new ArrayList<Integer>();
    public ArrayList<Integer> y = new ArrayList<Integer>();
    public String theString, message, userInput;
    public Random rand = new Random();
    public Scanner sc = new Scanner(System.in);
    /** The driver. */
    public static void main(String [] args){
        Snake game = new Snake();
        isPlaying = true;
        game.start();
        score = 0;
        game.generateDot();
        //dotX = 49;
        //dotY = 5;
        while(isPlaying){
            game.printGameScreen();
            game.updateScreen();
            game.doAI();
            game.wait(waitTime);
        }
    }
    
    /** Methods used within the driver. Sorted by order of appearance in driver. */
    public void start(){
        clear();
        x.add(0, 49);   //Starting point at the center
        y.add(0, 12);
        allDirectionFalse();    //Clears previous data
        up = true;              //Snake starts going up
        growth = 0;
        printScore();
        printFrame();
        printEmptyLines(11);
        printMessage("Welcome to the game of Snake!");
        printMessage("Hit the enter key to begin!");
        printEmptyLines(12);
        printFrame();
        userInput = sc.nextLine();
    }
    public void printGameScreen(){
        clear();
        printScore();
        printFrame();
        xCoord = 0;
        yCoord = 25;
        while(yCoord > 0){
            print("@");
            while (xCoord < 100){
                if (!testForItem(xCoord, yCoord)){
                    print(" ");
                    xCoord++;
                }
                else{
                    String toPrint = getItem(xCoord, yCoord);
                    for (int i = 0; i < x.size(); i++){
                        if (xCoord == x.get(i)){
                            if (yCoord == y.get(i)){
                                toPrint = "X";
                                break;
                            }
                        }
                    }
                    print(toPrint);
                    xCoord++;
                }
            }
            println("@");
            yCoord--;
            xCoord = 0;
        }
        printFrame();
        print("Frame Number: "); //Just to see if the game is updating or not
        println(frameNumber);
        frameNumber++;
        System.out.println("Dot: (" + dotX + ", " + dotY + ")");
        System.out.println("Snake head: (" + x.get(0) + ", " + y.get(0) + ")");
    }
    public void updateScreen(){
        runsIntoDot = getItem(x.get(0), y.get(0)).equals("o");
        
        if (runsIntoDot){
            generateDot();
            score++;
            growth += growthSize;
            //runsIntoDot = true;
        }
        /**This will be commented out until a better AI is implemented. */
        //else if (runsIntoSelf()){//If it runs into itself
        //    endGame();
        //}
        else if (y.get(0) > 25 || y.get(0) < 0 || x.get(0) > 100 || x.get(0) < 0){//If it runs into the border
            isPlaying = false;
        }
        
        /**This is how the following if/if else statements work */
        //Adds a new variable up front that is previous variable, but translated in the right direction
        //Adds a new variable to the other axis as the same as the previous
        //If it doesn't run into the dot, the end piece is removed as it doesn't grow
        //If it does run into a dot, nothing happens as the end piece gets to stay
        //if(getItem(x.get(0), y.get(0)).equals("o")){//If it lands on a dot
        if (up){
            y.add(0, y.get(0) + 1);
            x.add(0, x.get(0));
            if (!runsIntoDot && growth == 0){
                x.remove(x.size() - 1);
                y.remove(y.size() - 1);
            }
            else{
                growth--;
            }
        }
        else if (down){
            y.add(0, y.get(0) - 1);
            x.add(0, x.get(0));
            if (!runsIntoDot && growth == 0){
                x.remove(x.size() - 1);
                y.remove(y.size() - 1);
            }
            else{
                growth--;
            }
        }
        else if (left){
            x.add(0, x.get(0) - 1);
            y.add(0, y.get(0));
            if (!runsIntoDot && growth == 0){
                x.remove(x.size() - 1);
                y.remove(y.size() - 1);
            }
            else{
                growth--;
            }
        }
        else if (right){
            x.add(0, x.get(0) + 1);
            y.add(0, y.get(0));
            if (!runsIntoDot && growth == 0){
                x.remove(x.size() - 1);
                y.remove(y.size() - 1);
            }
            else{
                growth--;
            }
        }
        else{
            printError("updateScreen");
            System.out.println(up);
            System.out.println(down);
            System.out.println(left);
            System.out.println(right);
        }
        //Take an input of the arrow keys or WASD to change direction 
        //Moves the snake along by using the proper position increment based upon the direction (increment/decrement on x/y axis)
        //Body follows behind by inheriting the body part in front of it's position
        
        //Also tests if the snake collides with a wall then sets isPlaying to false;
        //Also tests if the snake runs into a dot
        //If the snake runs into a dot, then it increases the snake length
        //If it does, then a new dot location is generated using generateDot();
        if (!isPlaying){
            endGame();
        }
    }
    public void doAI(){
        if (y.get(0) > dotY){
            allDirectionFalse();
            down = true;
        }
        else if (y.get(0) < dotY){
            allDirectionFalse();
            up = true;
        }
        else if (x.get(0) > dotX){
            allDirectionFalse();
            left = true;
        }
        else{
            allDirectionFalse();
            right = true;
        }
    }
    
    /** AI Methods */
    //
    
    /** Methods used within main methods to do other tasks. Sorted alphabetically. */
    public void allDirectionFalse(){
        up = down = left = right = false;
    }
    public void clear(){
        System.out.print("\f");
    }
    public void endGame(){
        clear();
        isPlaying = false;
        printScore();
        printFrame();
        printEmptyLines(11);
        printMessage("You have lost the game");
        printMessage("Your final score was:");
        printMessage(Integer.toString(score*10));
        printEmptyLines(11);
        printFrame();
        
        print("Frame Number: ");
        println(frameNumber);
        System.out.println("Dot: (" + dotX + ", " + dotY + ")");
        System.out.println("Snake head: (" + x.get(0) + ", " + y.get(0) + ")");
    }
    public void generateDot(){
        //Generates random numbers to set location of the dot using variables dotX and dotY
        do{
            dotX = rand.nextInt(100);
            dotY = rand.nextInt(25);
        }while(getItem(dotX, dotY).equals("X")); //If the snake is over it, try another spot
    }
    public String getItem(int xCoordinate, int yCoordinate){
        //Returns the item at the given coordinate, whether it be a dot or snake part
        theString = "";
        if (dotX == xCoordinate && dotY == yCoordinate){
            theString = "o";
        }
        else{
            for (int i = 0; i < x.size(); i++){
                if (xCoordinate == x.get(i)){
                    if (yCoordinate == y.get(i)){
                        theString = "X";
                        break;
                    }
                }
            }
        }
        return theString;
    }
    public void keyInput(){
        //Takes in the key input
    }
    public boolean runsIntoSelf(){
        for (int i = 1; i < x.size(); i++){ //Starts at i = 1 as to not count the head itself
            if (x.get(i) == x.get(0) && y.get(i) == y.get(0)){
                return true;
            }
        }
        return false;
    }
    public boolean testForItem(int xCoordinate, int yCoordinate){
        // Takes the input coordinates to see if an item exists in the given position
        theBoolean = false;
        if (dotX == xCoordinate && dotY == yCoordinate){//If there is a dot
            theBoolean = true;
        }
        else{//If there is a snake body part
            for (int i = 0; i < x.size(); i++){
                if (xCoordinate == x.get(i)){
                    if (yCoordinate == y.get(i)){
                        theBoolean = true;
                        break;
                    }
                }
            }
        }
        return theBoolean;
    }
    public void wait(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    
    /** Print methods. */
    public void print(String s){
        System.out.print(s);
    }
    public void println(String s){
        System.out.println(s);
    }
    public void println(int s){
        System.out.println(s);
    }
    public void print(int s){
        System.out.print(s);
    }
    public void printEmptyLines(int x){ //Prints the specified amount of empty lines
        for (int i = 0; i < x; i++){
            xCoord = 0;
            print("@");
            while (xCoord < 100){
                print(" ");
                xCoord++;
            }
            println("@");
        }
    }
    public void printError(String method){ //Prints an error with the given location
        System.out.println("An error occured at the following location: " + method);
    }
    public void printFrame(){ //Prints 100 @s
        print("@");
        for (int i = 0; i < 100; i++){
            print("@");
        }
        println("@");
    }
    public void printMessage(String message){ //Prints the given message in the center of the line
        print("@");
        for (int i = 0; i < (100 - message.length())/2; i++){
            print(" ");
        }
        print(message);
        for (int i = 0; i < (100 - message.length())/2; i++){
            print(" ");
        }
        if (message.length()%2 != 0){
            print(" ");
        }
        println("@");
    }
    public void printScore(){
        print("Score: ");
        println(score*10);
    }
}