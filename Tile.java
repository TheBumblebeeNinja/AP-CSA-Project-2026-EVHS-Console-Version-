/**
 * Represents a location on the island or inside a building.
 * Stores its coordinates, description, available actions, and pathway.
 *
 * @author Joshua Wellabaum
 */
public class Tile {
    private int x;
    private int y;
    private String name;
    private String description;
    private boolean impassable;
    private boolean interior;

    // A location can offer actions and a connection to another location.
    private ItemAction firstAction;
    private ItemAction secondAction;
    private Pathway pathway;

    /**
     * Creates an outdoor location that the player can enter.
     *
     * @param x the location's horizontal coordinate
     * @param y the location's vertical coordinate
     * @param name the location's name
     * @param description the description of the location
     */
    public Tile(int x, int y, String name, String description) {
        // Reuse the full constructor with the default outdoor settings.
        this(x, y, name, description, false, false);
    }

    /**
     * Creates a location with specified movement and interior settings.
     *
     * @param x the location's horizontal coordinate
     * @param y the location's vertical coordinate
     * @param name the location's name
     * @param description the description of the location
     * @param impassable true if the player cannot enter this location
     * @param interior true if this location is inside a building
     */
    public Tile(int x, int y, String name, String description,
            boolean impassable, boolean interior) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.description = description;
        this.impassable = impassable;
        this.interior = interior;
    }

    /**
     * Checks whether this location matches the given coordinates.
     *
     * @param x the horizontal coordinate to check
     * @param y the vertical coordinate to check
     * @return true if both coordinates match this location
     */
    public boolean isAt(int x, int y) {
        // Both coordinates must match; sharing only a row or column is not enough.
        return this.x == x && this.y == y;
    }

    /**
     * Returns this location's horizontal coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns this location's vertical coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the name of this location.
     *
     * @return the location's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this location.
     *
     * @return the location's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Indicates whether this location blocks entry.
     *
     * @return true if the player cannot enter this location
     */
    public boolean isImpassable() {
        return impassable;
    }

    /**
     * Indicates whether this location is inside a building.
     *
     * @return true if this is an interior location
     */
    public boolean isInterior() {
        return interior;
    }

    /**
     * Returns the first action assigned to this location.
     *
     * @return the first action, or null if none is assigned
     */
    public ItemAction getFirstAction() {
        return firstAction;
    }

    /**
     * Returns the second action assigned to this location.
     *
     * @return the second action, or null if none is assigned
     */
    public ItemAction getSecondAction() {
        return secondAction;
    }

    /**
     * Assigns the actions available at this location.
     *
     * @param firstAction the first action, or null for no first action
     * @param secondAction the second action, or null for no second action
     */
    public void setActions(ItemAction firstAction, ItemAction secondAction) {
        this.firstAction = firstAction;
        this.secondAction = secondAction;
    }

    /**
     * Returns the pathway connected to this location.
     *
     * @return the pathway, or null if no pathway is assigned
     */
    public Pathway getPathway() {
        return pathway;
    }

    /**
     * Assigns a pathway to this location.
     *
     * @param pathway the connecting pathway, or null to remove the connection
     */
    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
    }
}
