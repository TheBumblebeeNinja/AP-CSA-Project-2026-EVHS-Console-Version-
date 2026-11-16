/**
 * Connects two locations and provides a travel label for each direction.
 *
 * @author Thomas Ta
 */
public class Pathway {
    private Tile firstTile;
    private Tile secondTile;
    private String firstLabel;
    private String secondLabel;

    /**
     * Creates a connection between two locations with direction-specific labels.
     *
     * @param firstTile the first endpoint of the pathway
     * @param secondTile the second endpoint of the pathway
     * @param firstLabel the travel label shown at the first endpoint
     * @param secondLabel the travel label shown at the second endpoint
     */
    public Pathway(Tile firstTile, Tile secondTile, String firstLabel, String secondLabel) {
        this.firstTile = firstTile;
        this.secondTile = secondTile;
        this.firstLabel = firstLabel;
        this.secondLabel = secondLabel;
    }

    /**
     * Finds the opposite endpoint when the given location belongs to this pathway.
     *
     * @param currentTile the location from which travel is being considered
     * @return the opposite endpoint, or null if the location is not an endpoint
     */
    public Tile getDestination(Tile currentTile) {
        // Compare the actual endpoint objects so travel follows this connection.
        if (currentTile == firstTile) {
            return secondTile;
        }
        if (currentTile == secondTile) {
            return firstTile;
        }
        return null;
    }

    /**
     * Returns the travel label for the given endpoint.
     *
     * @param currentTile the location from which travel is being considered
     * @return the travel label, or an empty string if the location is not an endpoint
     */
    public String getDescription(Tile currentTile) {
        if (currentTile == firstTile) {
            return firstLabel;
        }
        if (currentTile == secondTile) {
            return secondLabel;
        }
        return "";
    }
}

