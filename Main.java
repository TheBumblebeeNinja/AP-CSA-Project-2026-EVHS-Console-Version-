/**
 * Starts the console version of Island Escape.
 *
 * @author Tarun Achyuta
 */
public class Main {
    /**
     * Prevents creating Main objects because this class only provides startup.
     */
    private Main() {
    }

    /**
     * Starts the console application.
     *
     * @param args unused launch arguments
     */
    public static void main(String[] args) {
        new ConsoleApp().runConsole();
    }
}
