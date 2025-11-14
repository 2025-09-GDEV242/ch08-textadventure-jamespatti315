import java.util.ArrayList;
/**
 * Write a description of class Person here.
 *
 * @author James Patti
 * @version 11/13/25
 * 
 * the person class really just basic, has a stack for the inventory and 
 * a weight limit, 
 */
public class Person
{
    // instance variables - replace the example below with your own
    private int weightLimit;
    private ArrayList<Item> inventory = new ArrayList<>();  //essentially a copy of rooms items, but now a inventory the player"carries"

    /**
     * Constructor for objects of class Person
     * 
     * we basically will set the weight limit , and start something on inventory.
     */
    public Person()
    {
        weightLimit = 100;
        
        
    }
    
  
    
    
    /**
     * here work on accessor, allow players to see the inventory (and helps me test cause this complicateD)
     * 
     * @returns string type item
     */
    
    public String getInventoryString(){
        if(inventory.isEmpty()){
            return "There is nothing in your inventory or your soul";
        }
        StringBuilder list = new StringBuilder("you are carrying:");
         for (int i = 0;i < inventory.size(); i++) {
        list.append(" ").append(inventory.get(i).getName());
        
    }
    
    return list.toString();
    
}

    
     /**
     *  this method similer to rooms, just for player and there inventory.
     * @param items items so needs something of the items class
     * 
     */
    
    public void addItem(Item item){
        inventory.add(item);
        }

    /**
     * A method to remove item from a inventory, this will be used more when DROP command is done!
     *
     * @param  the string itemname
     * @return    item
     */
   public Item removeItem(String itemName){ 
         for (int i = 0; i < inventory.size(); i++) {
        Item item = inventory.get(i);
        if (item.getName().equalsIgnoreCase(itemName)) {
            inventory.remove(i); // safe removal by index
            return item;
        }
    }
    return null; // if no items,
}
}
