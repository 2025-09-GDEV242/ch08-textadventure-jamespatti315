
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
    private String itemName = "i'm a item! describe meeeee!"; //a name  of a item, changing and makinf descriptions seperate!
    private String itemDescription = "this a test description! detail me!"; 
    private int weight; //how much a item weights

    /**
     * Constructor for objects of class Item, a basic description and a weight of 1
     * lets change some thing! alt to itemName
     */  
    public Item(String itemName,int weight)
    {
        this.itemName = itemName;
        weight = 1;
        
    }
    
    /**looks like we need a getter for the item names
     * a basic  getter
     * @returns itemName
     */
    
    public String  getName(){
        return itemName;
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