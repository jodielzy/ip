package lucy;

public class Parser {
    public static String[] parseCommand(String input) {
        return input.split(" ", 2);
    }
}
