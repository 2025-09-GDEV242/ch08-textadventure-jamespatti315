
/**
 * Write a description of class Item here.
 *
 * @author James Patti
 * @version  11/7/25
 * 
 * USING BOOK: were gonna make a fresh new class, the "item" class 
 * this pairs with rooms, giving rooms at least one item,
 * items have a weight, and a description (string) rooms will just 
 * hold referenceto items.
 * 
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription = "this a test, if reading you messed up!"; //a description of a item
    private int weight; //how much a item weights

    /**
     * Constructor for objects of class Item, a basic description and a weight of 1
     */
    public Item(String itemDescription,int weight)
    {
        this.itemDescription = itemDescription;
        weight = 1;
        
    }

    /**
     * @return a short description of a item.
     * this was define above and can work on making items what we need.
     */
    public String shortItemDescription()
    {
        
        return itemDescription;
    }
}