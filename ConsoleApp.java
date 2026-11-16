import java.util.Scanner;

/**
* Reads console commands and displays game results.
*
* @author Tarun Achyuta
*/
public class ConsoleApp {
    private Game game;

    /**
     * Creates the game used by the console interface.
     */
    public ConsoleApp() {
        game = new Game();
    }

    /**
     * Repeats the console menu until the player quits or the input stream closes.
     */
    public void runConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ISLAND ESCAPE");
        System.out.println(
                "Find planks, rope, and waterproof pitch to repair the boat.");
        System.out.println("Start by looking near the cabin for an axe.");

        boolean playing = true;

        while (playing) {
            showConsoleTile();
            System.out.print("Your choice: ");

            // End the loop cleanly if the input stream closes.
            if (!scanner.hasNextLine()) {
                playing = false;
            } else {
                // Ignore surrounding spaces so otherwise valid commands still work.
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("q")) {
                    playing = false;
                } else {
                    handleConsoleChoice(choice);
                }
            }
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }

    /**
     * Displays the current location and the choices available to the player.
     */
    private void showConsoleTile() {
        System.out.println();
        System.out.println(
                game.getCurrentTile().getName() + " ("
                        + game.getCurrentTile().getX() + ", "
                        + game.getCurrentTile().getY() + ")");
        System.out.println(game.getCurrentTile().getDescription());

        if (game.hasEscaped()) {
            System.out.println("You escaped! Enter r to play again.");
        } else {
            if (!game.getCurrentTile().isInterior()) {
                showConsoleDirection("n", "North", 0, -1);
                showConsoleDirection("e", "East", 1, 0);
                showConsoleDirection("s", "South", 0, 1);
                showConsoleDirection("w", "West", -1, 0);
            }

            showConsoleAction("1", game.getCurrentTile().getFirstAction());
            showConsoleAction("2", game.getCurrentTile().getSecondAction());

            Pathway pathway = game.getCurrentTile().getPathway();

            if (pathway != null) {
                System.out.println(
                        "f - " + pathway.getDescription(game.getCurrentTile()));
            }
        }

        System.out.println("i - Inventory | r - Restart | q - Quit");
    }

    /**
     * Displays a direction and whether its neighboring location is blocked.
     *
     * @param key the command displayed beside the choice
     * @param name the direction name displayed to the player
     * @param dx the horizontal change in position
     * @param dy the vertical change in position
     */
    private void showConsoleDirection(
            String key, String name, int dx, int dy) {

        Tile destination = game.findTile(
                game.getCurrentTile().getX() + dx,
                game.getCurrentTile().getY() + dy);

        if (destination == null) {
            System.out.println(
                    key + " - " + name + ": ocean (blocked)");
        } else if (destination.isImpassable()) {
            System.out.println(
                    key + " - " + name + ": "
                            + destination.getName() + " (blocked)");
        } else {
            System.out.println(
                    key + " - " + name + ": "
                            + destination.getName());
        }
    }

    /**
     * Displays a numbered action only when it exists and is unfinished.
     *
     * @param key the command displayed beside the choice
     * @param action the action to display or attempt, or null if none is available
     */
    private void showConsoleAction(String key, ItemAction action) {
        if (action != null && !action.isCompleted()) {
            System.out.println(key + " - " + action.getDescription());
        }
    }

    /**
     * Interprets a command and displays an error for unsupported choices.
     *
     * @param choice the command entered by the player
     */
    private void handleConsoleChoice(String choice) {
        if (choice.equalsIgnoreCase("i")) {
            System.out.println(game.getInventoryText());
        } else if (choice.equalsIgnoreCase("r")) {
            game.startNewGame();
            System.out.println("You started a new game.");
        } else if (game.hasEscaped()) {
            System.out.println(
                    "Enter r to play again, i for inventory, or q to quit.");
        } else if (choice.equalsIgnoreCase("n")) {
            move(0, -1);
        } else if (choice.equalsIgnoreCase("e")) {
            move(1, 0);
        } else if (choice.equalsIgnoreCase("s")) {
            move(0, 1);
        } else if (choice.equalsIgnoreCase("w")) {
            move(-1, 0);
        } else if (choice.equals("1")) {
            performAction(game.getCurrentTile().getFirstAction());
        } else if (choice.equals("2")) {
            performAction(game.getCurrentTile().getSecondAction());
        } else if (choice.equalsIgnoreCase("f")) {
            Pathway pathway = game.getCurrentTile().getPathway();

            if (pathway == null) {
                System.out.println(
                        "There is no pathway at this location.");
            } else {
                moveTo(pathway.getDestination(game.getCurrentTile()));
            }
        } else {
            System.out.println(
                    "Choose one of the letters or numbers shown.");
        }
    }

    /**
     * Forwards a directional move to Game and displays the result.
     *
     * @param dx the horizontal change in position
     * @param dy the vertical change in position
     */
    private void move(int dx, int dy) {
        System.out.println(game.move(dx, dy));
    }
    /**
     * Forwards a pathway move to Game and displays the result.
     *
     * @param destination the requested destination, or null when there is no tile
     */
    private void moveTo(Tile destination) {
        System.out.println(game.moveTo(destination));
    }
    /**
     * Forwards an item action to Game and displays the result.
     *
     * @param action the action to display or attempt, or null if none is available
     */
    private void performAction(ItemAction action) {
        System.out.println(game.performAction(action));
    }
}

