/**
 * Stores an item's name, description, and inventory status.
 *
 * @author Antony Ton
 */

public class Item {
    //Instance variables
    
    private String name;
    private String description;
    private boolean inInventory;

    //Constructor

   /** 
    * Item constructor
    * @param name (String) - name of item
    * @param description (String) - item description
    */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        inInventory = false;
    }

    //Accessors

    /** 
    * Accessor method for item's name
    * @return returns name (String)
    */
    public String getName() {
        return name;
    }

    /** 
    * Accessor method for item's description
    * @return returns description (String)
    */
    public String getDescription() {
        return description;
    }

    /** 
    * Accessor method for item's inventory status
    * @return returns inInventory (boolean)
    */
    public boolean isInInventory() {
        return inInventory;
    }

    //Mutators

    /** 
    * Mutator method for item's inventory status
    * @param inInventory (boolean) - new value for inInventory instance variable
    */
    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }
}
