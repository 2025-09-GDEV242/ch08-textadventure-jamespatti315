import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * 
 * new @author James Patti
 * @new version 2025.07.11
 * 
 * ok lets go small and add a few new rooms
 * 
 * 1st a basic test room (1/8 to add)
 * 
 * 
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item defaultItem;
    private Stack<Room>lastRooms = new Stack<>();  //a stack of last rooms, with it we can travel back up any amount of rooms!
    
        
    /**
     * Create the game and initialise its internal map.
     * 
     * added the as of yet not done items, it easy and can work peice by peice
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     * 
     * lets make  roughly 8 new rooms then add items to actually win the game
     * (maybe also a "you win room?")
     * 
     * also need to make a cross-directional (north east or such)
     * 
     * 1st room test
     * 
     * 
     * 
     * //lets also see.. we should be able to make and call for items via other object!
     * 
     */
    
    
    private void createRooms()
    {
        Room outside, theater, pub, lab, office,city,evilLab,drunkHell,play;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        city = new Room("entering a large city, its looks pretty crazy!");  //from outside, north!
        evilLab = new Room("in a odd and mysterious labatory, odd machines and chemicals buzz next to computers."); //from admin by south
        drunkHell = new Room("in hell, your drunken escapades have sent you straight into that pink elephants sequence from dumbo!"); //access from  west of campus pub
        play = new Room("in the play! show the audience your acting skills and break a leg!");
        
        
        // initialise room exits
        outside.setExit("north",city);
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        
        
        city.setExit("south",outside);

        theater.setExit("west", outside);
        theater.setExit("east",play);

        pub.setExit("east", outside);
        pub.setExit("west",drunkHell);
        

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("south",evilLab);
        
        evilLab.setExit("north",office);
        
        drunkHell.setExit("east",pub);
        
        play.setExit("west",theater);

        currentRoom = outside;  // start game outside
    }
    
    /**
     * here I will create the make items method, this will be similer to above create room method, it will make a set of items
     * with some  weight and description.
     */
    
    private void createItems(){
        //create the items to add to rooms below, we need a string and its weight (a int)
       Item nothing = new Item("its a whole lotta nothing! Despite being nothing it has a weight of 1",1);
        
        
        
        
        
        
        
        
    
        
        
    }
    
    
    
    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
                //lets work on LOOk!
                case LOOK:
                    lookAround(command);
                    break;
                    
                    //here is the back command
                    case BACK:
                        goBack(command);
                        break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRooms.push(currentRoom); //ok must need this here as nothing pushed till goRoom invoked! my dyslexia kills me.
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    
    /**ok here lets implment and create a new method using the LOOK command word which will give a look around for a test room
     * It will pass to main and tkes information set up in the other enum command objects to function fully.
     */
    
    private void lookAround(Command command){
            System.out.println("You begin looking around...");
            System.out.println(currentRoom.getLongDescription());
        
    }
    
    
    /**
     * ok here were gonna create the function for the BACK command,  we need  to keep going through the last room , with added protection of a 
     * thing for if back hit but no previoius rooms have been entered.
     * this like above
     * @param Command command
     * 
     */
    
    private void goBack(Command command){
        
         if (lastRooms.isEmpty()) {
            System.out.println("There is nowhere to go back to!");
        }
        else{
            
            currentRoom = lastRooms.pop();   //populate the lastRooms list with all previoiusly visitied rooms~
            System.out.println ("You turn around and start heading back..." + currentRoom.getLongDescription());
        }
        
    }
    

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
