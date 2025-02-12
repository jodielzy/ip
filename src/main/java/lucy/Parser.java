package lucy;

/**
 * Parses user commands.
 */
public class Parser {
    /**
     * Parses the given command string.
     * @param input The command input.
     * @return The parsed command as an array.
     */
    public static String[] parseCommand(String input) {
        assert input != null : "Command input should not be null";
        return input.split(" ", 2);
    }
}
