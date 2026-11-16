/**
* Manages the island, inventory, movement rules, and escape state independently of the interface.
*
* @author Tarun Achyuta
*/
public class Game {
    private Tile lookout;
    private Tile spring;
    private Tile westernBeach;
    private Tile pineForest;
    private Tile clearing;
    private Tile cliffs;
    private Tile shipwreck;
    private Tile fallenTree;
    private Tile cabinOutside;
    private Tile boat;
    private Tile tidePools;
    private Tile startingBeach;
    private Tile southernBeach;

    private Item axe;
    private Item planks;
    private Item rope;
    private Item pitch;
    private Tile currentTile;
    private boolean escaped;

    /**
     * Creates a fresh island and places the player on the starting beach.
     */
    public Game() {
        // Fresh objects reset item ownership and one-time action completion.
        createIsland();
        currentTile = startingBeach;
    }

    /**
     * Returns the location currently occupied by the player.
     *
     * @return the player's current location
     */
    public Tile getCurrentTile() { return currentTile; }
    /**
     * Reports whether the player has completed the escape action.
     *
     * @return true after the escape action succeeds
     */
    public boolean hasEscaped() { return escaped; }

    /**
     * Resets the game by recreating the island and returning to the starting beach.
     */
    public void startNewGame() {
        escaped = false;
        createIsland();
        currentTile = startingBeach;
    }

    /**
     * Builds a description of carried items or an empty-inventory message.
     *
     * @return the inventory description
     */
    public String getInventoryText() {
        String items = inventoryLine(axe)
                + inventoryLine(planks)
                + inventoryLine(rope)
                + inventoryLine(pitch);

        if (items.equals("")) {
            return "Your inventory is empty.";
        }

        return items;
    }

    /**
     * Formats one carried item for the inventory display.
     *
     * @param item the item to examine
     * @return the item description followed by a newline, or an empty string if it is not carried
     */
    private String inventoryLine(Item item) {
        if (item.isInInventory()) {
            return item.getName() + " - "
                    + item.getDescription() + "\n";
        }
        return "";
    }

    /**
     * Checks whether a directional move is currently permitted without moving the player.
     *
     * @param dx the horizontal change in position
     * @param dy the vertical change in position
     * @return true if the requested move is permitted
     */
    public boolean canMove(int dx, int dy) {
        Tile destination = findTile(
                currentTile.getX() + dx,
                currentTile.getY() + dy);

        // A move requires an active game, an outdoor position, and an enterable tile.
        return !escaped
                && !currentTile.isInterior()
                && destination != null
                && !destination.isImpassable();
    }

    /**
     * Looks up an outdoor location by its coordinates.
     *
     * @param x the horizontal coordinate to find
     * @param y the vertical coordinate to find
     * @return the outdoor tile at those coordinates, or null for ocean
     */
    public Tile findTile(int x, int y) {
        if (lookout.isAt(x, y)) {
            return lookout;
        }
        if (spring.isAt(x, y)) {
            return spring;
        }
        if (westernBeach.isAt(x, y)) {
            return westernBeach;
        }
        if (pineForest.isAt(x, y)) {
            return pineForest;
        }
        if (clearing.isAt(x, y)) {
            return clearing;
        }
        if (cliffs.isAt(x, y)) {
            return cliffs;
        }
        if (shipwreck.isAt(x, y)) {
            return shipwreck;
        }
        if (fallenTree.isAt(x, y)) {
            return fallenTree;
        }
        if (cabinOutside.isAt(x, y)) {
            return cabinOutside;
        }
        if (boat.isAt(x, y)) {
            return boat;
        }
        if (tidePools.isAt(x, y)) {
            return tidePools;
        }
        if (startingBeach.isAt(x, y)) {
            return startingBeach;
        }
        if (southernBeach.isAt(x, y)) {
            return southernBeach;
        }

        return null; // No outdoor tile here, so this is ocean.
    }

    /**
     * Creates locations, items, actions, and the cabin pathway for a fresh game.
     */
    private void createIsland() {
        // X increases eastward. Y increases southward.
        lookout = new Tile(
                1, 0, "Lookout",
                "You stand on a hill overlooking the island.");

        spring = new Tile(
                2, 0, "Freshwater Spring",
                "A small spring bubbles between the rocks.");

        westernBeach = new Tile(
                0, 1, "Western Beach",
                "Waves wash against a narrow strip of sand.");

        pineForest = new Tile(
                1, 1, "Pine Forest",
                "Tall pine trees shade the sandy path.");

        clearing = new Tile(
                2, 1, "Forest Clearing",
                "Sunlight falls through a gap in the trees.");

        cliffs = new Tile(
                3, 1, "Steep Cliffs",
                "The cliffs are too steep to climb.",
                true, false);

        shipwreck = new Tile(
                0, 2, "Shipwreck",
                "An old shipwreck rests on the beach.");

        fallenTree = new Tile(
                1, 2, "Fallen Tree",
                "A fallen tree lies across the forest floor.");

        cabinOutside = new Tile(
                2, 2, "Cabin Exterior",
                "You are outside a wooden cabin.");

        boat = new Tile(
                3, 2, "Boat Cove",
                "A damaged rowboat sits in a sheltered cove.");

        tidePools = new Tile(
                0, 3, "Tide Pools",
                "Small tide pools glisten along the shore.");

        startingBeach = new Tile(
                1, 3, "Starting Beach",
                "You wake up stranded on an island.");

        southernBeach = new Tile(
                2, 3, "Southern Beach",
                "Footprints lead north toward a cabin.");

        Tile cabinInside = new Tile(
                99, 99, "Cabin Interior",
                "Inside the cabin, you see a dusty shelf and an old journal.",
                false, true);

        Pathway cabinDoor = new Pathway(
                cabinOutside,
                cabinInside,
                "Enter the cabin",
                "Leave the cabin");

        cabinOutside.setPathway(cabinDoor);
        cabinInside.setPathway(cabinDoor);

        axe = new Item(
                "axe",
                "A tool for cutting the fallen tree into planks.");

        planks = new Item(
                "planks",
                "Wooden boards for repairing the boat.");

        rope = new Item(
                "rope",
                "Strong rope salvaged from the shipwreck.");

        pitch = new Item(
                "waterproof pitch",
                "Sticky pitch for sealing the boat's cracks.");

        ItemAction takeAxe = new ItemAction(
                "Pick up the axe",
                "You take the axe.");
        takeAxe.setReward(axe);
        cabinOutside.setActions(takeAxe, null);

        ItemAction cutPlanks = new ItemAction(
                "Cut planks with the axe",
                "You cut several sturdy planks. You keep the axe.");
        cutPlanks.setRequirements(axe, null, null);
        cutPlanks.setReward(planks);
        fallenTree.setActions(cutPlanks, null);

        ItemAction takeRope = new ItemAction(
                "Take the rope",
                "You take the rope.");
        takeRope.setReward(rope);
        shipwreck.setActions(takeRope, null);

        ItemAction takePitch = new ItemAction(
                "Take the waterproof pitch",
                "You take the pitch.");
        takePitch.setReward(pitch);

        ItemAction readJournal = new ItemAction(
                "Read the old journal",
                "The journal says to patch the boat with planks, rope, and waterproof pitch."
                        + " There is rope at the western shipwreck.");

        cabinInside.setActions(takePitch, readJournal);

        ItemAction repairBoat = new ItemAction(
                "Repair the boat and sail away",
                "You repair the boat and sail toward the mainland. You escaped the island!");
        repairBoat.setRequirements(planks, rope, pitch);
        repairBoat.setUsesRequiredItems(true);
        repairBoat.setEndsGame(true);
        boat.setActions(repairBoat, null);
    }

    /**
     * Attempts an action and updates escape state after a successful escape action.
     *
     * @param action the action to display or attempt, or null if none is available
     * @return the action result or a message explaining that no action is available
     */
    public String performAction(ItemAction action) {
        if (action == null) {
            return "There is no action with that number here.";
        }
        // Let ItemAction manage its requirements and rewards before checking for escape.
        String message = action.perform();
        if (action.isCompleted() && action.endsGame()) {
            escaped = true;
        }
        return message;
    }

    /**
     * Attempts a relative move, requiring the pathway when the player is indoors.
     *
     * @param dx the horizontal change in position
     * @param dy the vertical change in position
     * @return a movement result or an explanation of why movement failed
     */
    public String move(int dx, int dy) {
        if (currentTile.isInterior()) {
            return "Use the cabin pathway to leave this interior.";
        }
        return moveTo(findTile(currentTile.getX() + dx, currentTile.getY() + dy));
    }

    /**
     * Checks a destination and changes the player location when travel is permitted.
     *
     * @param destination the requested destination, or null when there is no tile
     * @return a movement result or an explanation of why the destination is blocked
     */
    public String moveTo(Tile destination) {
        if (escaped) {
            return "You have already escaped.";
        } else if (destination == null) {
            return "You cannot walk into the ocean.";
        } else if (destination.isImpassable()) {
            return "You cannot enter " + destination.getName() + ".";
        }
        currentTile = destination;
        return "You reached " + destination.getName() + ".";
    }
}

