/**
 * Represents an action with optional item requirements, a reward, and completion state.
 *
 * @author Lauren Lee
 */
public class ItemAction {
    //Instance variables
    
    private String description;
    private String successMessage;
    private Item firstRequired;
    private Item secondRequired;
    private Item thirdRequired;
    private Item reward;
    private boolean usesRequiredItems;
    private boolean endsGame;
    private boolean completed;

    //Constructor
    
    /**
    * Creates a new ItemAction object
    * @param description (String) - description of the item action
    * @param successMessage (String) - the success message of the item action
    */

    public ItemAction(String description, String successMessage) {
        this.description = description;
        this.successMessage = successMessage;
    }

    //Mutators

    /**
    * Sets the values of the requirements
    * @param first (Item) - first item required
    * @param second (Item) - second item required
    * @param third (Item) - third item required
    */
    public void setRequirements(Item first, Item second, Item third) {
        firstRequired = first;
        secondRequired = second;
        thirdRequired = third;
    }

    /** 
    * Sets the value of the reward
    * @param reward (Item) - the reward
    */
    public void setReward(Item reward) {
        this.reward = reward;
    }
    
    /**
     * Sets whether successful completion removes the required items from inventory.
     *
     * @param usesRequiredItems true to consume required items on success
     */
    public void setUsesRequiredItems(boolean usesRequiredItems) {
        this.usesRequiredItems = usesRequiredItems;
    }

    /**
     * Sets whether completing this action should end the game.
     *
     * @param endsGame true if successful completion should end the game
     */
    public void setEndsGame(boolean endsGame) {
        this.endsGame = endsGame;
    }

    //Accessors
    
    /**
     * Returns the description shown to the player.
     *
     * @return the action description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Reports whether this action has already succeeded.
     *
     * @return true if this action has succeeded
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Reports whether this action is marked as an escape action.
     *
     * @return true if this action is marked as ending the game
     */
    public boolean endsGame() {
        return endsGame;
    }

    /**
     * Checks requirements, consumes items when configured, and grants a reward once.
     *
     * @return a success message or the reason the action cannot be completed
     */
    public String perform() {
        // Prevent repeated rewards or repeated consumption after success.
        if (completed) {
            return "You have already completed that action.";
        }

        String missing = addMissing("", firstRequired);
        missing = addMissing(missing, secondRequired);
        missing = addMissing(missing, thirdRequired);
        if (!missing.equals("")) {
            return "You still need: " + missing + ".";
        }

        // Check all requirements before consuming any supplies.
        if (usesRequiredItems) {
            removeItem(firstRequired);
            removeItem(secondRequired);
            removeItem(thirdRequired);
        }

        completed = true;
        if (reward != null) {
            reward.setInInventory(true);
            return successMessage + "\nYou received: " + reward.getName() + ".";
        }
        return successMessage;
    }

    /**
     * Adds an unowned required item to the missing-items message.
     *
     * @param missing the missing-item names accumulated so far
     * @param item the item to examine
     * @return the updated list of missing item names
     */
    private String addMissing(String missing, Item item) {
        // Unused requirement slots and already-carried items do not block the action.
        if (item == null || item.isInInventory()) {
            return missing;
        }
        if (missing.equals("")) {
            return item.getName();
        }
        return missing + ", " + item.getName();
    }

    /**
     * Removes an assigned item from inventory; ignores an unassigned item.
     *
     * @param item the item to examine
     */
    private void removeItem(Item item) {
        if (item != null) {
            item.setInInventory(false);
        }
    }
}

