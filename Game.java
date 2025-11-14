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
 * about done.!
 * 
 * 
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item defaultItem;
    private Stack<Room>lastRooms = new Stack<>();  //a stack of last rooms, with it we can travel back up any amount of rooms!
    private Room outside, theater, pub, lab, office, city, evilLab, drunkHell, play;  //making them a field so scope not ruining me
    private Person player;  //there now should work.
    
        
    /**
     * Create the game and initialise its internal map.
     * 
     * added  a few other things, namely a instiantior for items, and a person entity so everything called correctly.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
        player = new Person();
    }

    /**
     * Create all the rooms and link their exits together.
     * 
     * made all rooms, grammer wonky but tired.
     * 

     */
    
    
    private void createRooms()
    {
       
      
        // create the rooms
        outside = new Room ("outside the main entrance of the university");
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
     * 
     * created a set of objects, alas no real mechanicsm and weight doesn;t do anything but got another thing and studies.
     */
    
    private void createItems(){
        
    
        //create the items to add to rooms below, we need a string and its weight (a int)
       Item nothing = new Item("nothing","..its a whole lotta nothing! Despite being nothing it has a weight of 1",1);  //add to outside
       Item sobriety  = new Item ("sobriety","...Sobriety finally free from your own drunken escapades!: has weight of 30",30);  //add to drunk hell
       Item kingYellow = new Item ("kinginyellow","...The king in yellow...its a liteary reference! weight of 5",5);  //add to play
       Item herb = new Item ("herb","...a green health herb its a video game reference! its has weight of 2",2);  //add to evilLab
        
       // now lets add items to the rooms
       
       outside.addItem(nothing);
       drunkHell.addItem(sobriety);
       play.addItem(kingYellow);
       evilLab.addItem(herb);
       
       
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
                        
                        //here the pickuo
                        case PICKUP:
                             if (!command.hasSecondWord()) {
                    System.out.println("Pick up what?");
                } else {
                    pickupItem(command.getSecondWord());
                }
                break;
                
                case DROP:
                if (!command.hasSecondWord()) {
                    System.out.println("Drop what?");
                } else {
                    dropItem(command.getSecondWord());
                }
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
    
    private  void lookAround(Command command){
            System.out.println("You begin looking around...");
            System.out.print(currentRoom.getLongDescription());
            System.out.println(currentRoom.getItemsString());
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
    
    /**here lets work on the final command methods, first is pickup of a item, 
     * this method takes the
     * @parem string name of a  item "itemName" and removes the item from the room and shoves it into the players inventory.
     * if no item to pick up then returns nothing, basically no changes needed
     * 
     * @parem string ItemName
     * @return null or puts item in new inventory
     */
    
    private void pickupItem(String itemName){
        Item item = currentRoom.removeItem(itemName); 
        if (item == null){
            System.out.println("There is nothing to grab numbskull!");
            return;
        }
        player.addItem(item);                          // add to player's inventory
    System.out.println("You grab a... " + item.getName() + ".");
        
    }
    
    /**
     * this method works likve above but drops a item, 
     * @parem String itemname.
     * @returns nothing is no item to drop. 
     */
    private void dropItem(String itemName){
        Item item = player.removeItem(itemName);
        if (item == null){
            System.out.println("Nothing to drop");
            return;
        }
        currentRoom.addItem(item);
        System.out.println("You Drop" + item.getName() );
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
